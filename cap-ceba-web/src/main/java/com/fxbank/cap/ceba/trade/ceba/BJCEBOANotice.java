package com.fxbank.cap.ceba.trade.ceba;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.ceba.REP_BJCEBOANotice;
import com.fxbank.cap.ceba.dto.ceba.REQ_BJCEBOANotice;
import com.fxbank.cap.ceba.model.CebaOutageLogModel;
import com.fxbank.cap.ceba.service.ICebaOutageLogService;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import redis.clients.jedis.Jedis;

/** 
* @ClassName: BJCEBOANotice 
* @Description: 停运通知报文 
* @作者 杜振铎
* @date 2019年6月12日 下午2:20:17 
*  
*/
@Service("BJCEBOANotice")
public class BJCEBOANotice implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(BJCEBOANotice.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;
	
	@Reference(version = "1.0.0")
	private ICebaOutageLogService cebaOutageLogService;

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_BJCEBOANotice req = (REQ_BJCEBOANotice) dto;
		//文件上传日期和时间
		String noticeTime = req.getTin().getDate();
		//文件名称
		String fileName = req.getTin().getFileName();
		String localFile = getOutageFile(myLog,noticeTime,fileName);
		//通知类型 0 缴费停运通知(默认为0，其它扩展使用）
		String noticeType = req.getTin().getNoticetype();
		if("0".equals(noticeType)) {
			initOutageLog(localFile,myLog,noticeTime);
		}
		REP_BJCEBOANotice rep = new REP_BJCEBOANotice();
		return rep;
	}
	private void initOutageLog(String localFile, MyLog myLog,String noticeTime) throws SysTradeExecuteException {
		BufferedReader br = null;
		myLog.info(logger, "停运通知文件入库开始");
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(localFile)),"GBK"));
			String lineTxt=null;
			while ((lineTxt = br.readLine()) != null) {
                if(!"END".endsWith(lineTxt)){
    				lineTxt += "*|+|";
    				String[] array = lineTxt.split("\\|\\+\\|");
                    CebaOutageLogModel model = new CebaOutageLogModel(myLog, null,null,null);
                    model.setNoticeTime(Long.parseLong(noticeTime));
                    model.setOutageNoticeId(array[0]);
                    model.setCompanyId(array[1]);
                    model.setCompanyName(array[2]);
                    model.setOutageReason(array[3]);
                    model.setOutageDesc(array[4]);
                    model.setOutageBeginTime(Long.parseLong(array[5]));
                    model.setOutageEndTime(Long.parseLong(array[6]));
                    model.setFixDate(Integer.parseInt(array[7]));
                    model.setFixTime(Integer.parseInt(array[8]));
                    model.setRemark(array[9].substring(0,array[9].length()-1));
            		cebaOutageLogService.initOutageLog(myLog, model);
                }

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
			myLog.info(logger, "停运通知入库结束");
		}
	}
	private String getOutageFile(MyLog myLog,String date, String fileName) throws SysTradeExecuteException {
		
		String localPath="";
		try (Jedis jedis = myJedis.connect()) {
			localPath = jedis.get(COMMON_PREFIX+"txt_path");
		}
		return localPath+File.separator+fileName;
	}
	
}