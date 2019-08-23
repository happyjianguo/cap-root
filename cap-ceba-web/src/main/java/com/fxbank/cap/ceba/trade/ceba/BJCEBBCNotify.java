package com.fxbank.cap.ceba.trade.ceba;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.ceba.REP_BJCEBBCNotify;
import com.fxbank.cap.ceba.dto.ceba.REQ_BJCEBBCNotify;
import com.fxbank.cap.ceba.model.CebaCheckLogInitModel;
import com.fxbank.cap.ceba.service.ICebaChargeLogService;
import com.fxbank.cap.ceba.service.ICebaCheckLogService;
import com.fxbank.cap.ceba.service.ICebaSettleLogService;
import com.fxbank.cap.ceba.service.ICheckErrorService;
import com.fxbank.cap.ceba.service.IHostCheckLogService;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

import redis.clients.jedis.Jedis;

/** 
* @ClassName: BJCEBBCNotify 
* @Description: 对账文件上传通知报文 
* @作者 杜振铎
* @date 2019年6月12日 下午2:20:17 
*  
*/
@Service("BJCEBBCNotify")
public class BJCEBBCNotify implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(BJCEBBCNotify.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private ICebaChargeLogService cebaChargeLogService;
	
	@Reference(version = "1.0.0")
	private IHostCheckLogService hostCheckLogService;
	
	@Reference(version = "1.0.0")
	private ICebaCheckLogService cebaCheckLogService;
	
	@Reference(version = "1.0.0")
	private ICheckErrorService checkErrorService;
	
	@Reference(version = "1.0.0")
	private ICebaSettleLogService cebaSettleLogService;

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_BJCEBBCNotify req = (REQ_BJCEBBCNotify) dto;
		//对账日期
		String date = req.getTin().getSignDate();
		//光大银行对账文件名称
		String fileName = req.getTin().getFileName();
		//获取光大银行对账文件
		String localFile = getCheckFile(myLog,date,fileName);
		//登记光大银行对账信息日志
		initCebaCheckLog(localFile,myLog,date);
		REP_BJCEBBCNotify rep = new REP_BJCEBBCNotify();
		return rep;
	}
	private void initCebaCheckLog(String localFile, MyLog myLog,String platDate) throws SysTradeExecuteException {
		BufferedReader br = null;
		myLog.info(logger, "光大银行对账文件入库开始");
		try {
			cebaCheckLogService.delete(platDate);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(localFile)),"GBK"));
			String lineTxt=null;
			//缴费成功金额
			BigDecimal totalSuccAmt = null;
			//缴费成功笔数
			int totalSuccNum = 0;
			//明细累加的缴费成功金额
			BigDecimal totalSuccAmt1 = new BigDecimal(0);
			//明细累加的缴费成功笔数
			int totalSuccNum1 = 0;
			while ((lineTxt = br.readLine()) != null) {
				lineTxt += "*|";
				String[] array = lineTxt.split("\\|");
				if(array.length==4) {
					totalSuccAmt = new BigDecimal(array[0]);
					totalSuccNum = Integer.parseInt(array[1]);
				}
                if(array.length==6){
                    CebaCheckLogInitModel model = new CebaCheckLogInitModel(myLog, Integer.parseInt(array[2].substring(0, 8)),Integer.parseInt(array[2].length()<14?"0":array[2].substring(8, 14)),Integer.parseInt(array[0]));
                    BigDecimal bg = new BigDecimal(array[1]==null?"0":array[1]);
                    model.setTxAmt(bg.movePointLeft(2).setScale(2, BigDecimal.ROUND_HALF_UP)); //交易金额
                    model.setBankBillNo(array[3]);
            		cebaCheckLogService.cebaCheckLogInit(model);
            		totalSuccAmt1 = totalSuccAmt1.add(bg);
            		totalSuccNum1++;
                }
			}
			//对账文件成功金额、笔数和明细累加的金额、笔数不同，删除对账明细数据
			if(totalSuccNum!=totalSuccNum1||totalSuccAmt.compareTo(totalSuccAmt1)!=0) {
				cebaCheckLogService.delete(platDate);
				myLog.error(logger, "光大银行对账文件成功明细累加总金额、总笔数和成功金额、成功笔数不同，对账日期"+platDate);
			}

		} catch (Exception e) {
            myLog.error(logger, "文件【"+localFile+"】插入失败", e);
            throw new RuntimeException("文件【"+localFile+"】插入失败");
		} finally {
			if(null != br) {
				try {
					br.close();
				} catch (IOException e) {
					myLog.error(logger, "文件流关闭失败", e);
				}
			}
			myLog.info(logger, "光大银行对账信息入库结束");
		}
	}
	
	private String getCheckFile(MyLog myLog,String date, String fileName) throws SysTradeExecuteException {
		
		String localPath="";
		try (Jedis jedis = myJedis.connect()) {
			localPath = jedis.get(COMMON_PREFIX+"txt_path");
		}
		//上传商户T日交易的对账文件时，会按照T日的日期为名在商户指定目录下创建一个目录，然后把对应T日的对账文件放到这个目录下。
		return localPath+File.separator+date+File.separator+fileName;
	}

}
