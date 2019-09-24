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
import com.fxbank.cap.ceba.model.CebaBillCheckModel;
import com.fxbank.cap.ceba.model.CebaCheckLogInitModel;
import com.fxbank.cap.ceba.service.ICebaBillCheckService;
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
import com.fxbank.cip.base.util.SFtpUtil;

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
	private ICebaBillCheckService cebaBillCheckService;
	
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
		CebaBillCheckModel model = new CebaBillCheckModel(myLog,0,0,0);
		model.setSignDate(Integer.parseInt(date));
		model.setFileName(fileName);
		model.setUploadDate(Long.parseLong(req.getTin().getDate()));
		model.setStatus("0");
		model.setRemark(req.getTin().getFiled1());
		cebaBillCheckService.billCheckInit(model);
		//登记光大银行对账信息日志
		initCebaCheckLog(localFile,myLog,date,req.getTin().getDate());
		REP_BJCEBBCNotify rep = new REP_BJCEBBCNotify();
		return rep;
	}
	private void initCebaCheckLog(String localFile, MyLog myLog,String platDate,String uploadDate) throws SysTradeExecuteException {
		BufferedReader br = null;
		myLog.info(logger, "光大银行对账文件入库开始");
		CebaBillCheckModel checkModel = new CebaBillCheckModel(myLog,0,0,0);
		checkModel.setSignDate(Integer.parseInt(platDate));
		checkModel.setUploadDate(Long.parseLong(uploadDate));
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
			checkModel.setStatus("1");
			cebaBillCheckService.billCheckUpdate(checkModel);
		} catch (Exception e) {
            myLog.error(logger, "文件【"+localFile+"】插入失败", e);
			checkModel.setStatus("2");
			cebaBillCheckService.billCheckUpdate(checkModel);
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
		//对账文件保存到本地路径
		String localPath = null;
		//光大银行上传文件FTP地址
		String ftpIP = null;
		//光大银行上传文件FTP端口号
		Integer ftpPort = null;
		//光大银行上传文件FTP用户名
		String ftpUser = null;
		//光大银行上传文件FTP密码
		String ftpPassword = null;
		//光大银行上传文件FTP路径
		String ftpPath = null;
		try (Jedis jedis = myJedis.connect()) {
			localPath = jedis.get(COMMON_PREFIX + "cebafile_path");
			ftpIP = jedis.get(COMMON_PREFIX + "ftp_ip");
			ftpPort = null == jedis.get(COMMON_PREFIX + "ftp_port") ? 22
					: Integer.parseInt(jedis.get(COMMON_PREFIX + "ftp_port"));
			ftpUser = jedis.get(COMMON_PREFIX + "ftp_user");
			ftpPassword = jedis.get(COMMON_PREFIX + "ftp_password");
			ftpPath = jedis.get(COMMON_PREFIX + "ftp_path");
		}
		SFtpUtil sftpUtil = null;
		try {
			sftpUtil = SFtpUtil.getConnect(ftpIP, ftpUser, ftpPort, ftpPassword);
			File localFilePath = new File(localPath + File.separator + date);
			if (!localFilePath.exists()) {
				localFilePath.mkdirs();
			}
			sftpUtil.download(ftpPath + File.separator + date, fileName,
					localPath + File.separator + date + File.separator + fileName);
			// 上传商户T日交易的对账文件时，会按照T日的日期为名在商户指定目录下创建一个目录，然后把对应T日的对账文件放到这个目录下。
		} catch (Exception e) {
			myLog.error(logger, "下载对账文件失败", e);
		}
		return localPath + File.separator + date + File.separator + fileName;
	}
}
