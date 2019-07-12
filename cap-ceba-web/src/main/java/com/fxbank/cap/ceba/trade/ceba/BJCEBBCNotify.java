package com.fxbank.cap.ceba.trade.ceba;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.fxbank.cap.ceba.dto.ceba.REP_BJCEBBCNotify;
import com.fxbank.cap.ceba.dto.ceba.REQ_BJCEBBCNotify;
import com.fxbank.cap.ceba.exception.CebaTradeExecuteException;
import com.fxbank.cap.ceba.model.CebaChargeLogModel;
import com.fxbank.cap.ceba.model.CheckErrorModel;
import com.fxbank.cap.ceba.model.DayCheckLogInitModel;
import com.fxbank.cap.ceba.service.ICebaChargeLogService;
import com.fxbank.cap.ceba.service.ICheckErrorService;
import com.fxbank.cap.ceba.service.IDayCheckLogService;
import com.fxbank.cap.esb.model.ses.ESB_REP_50015000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_50015000101;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
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
	private IDayCheckLogService dayCheckLogService;
	
	@Reference(version = "1.0.0")
	private ICheckErrorService checkErrorService;

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
		myLog.info(logger, "核心与外围对账开始");
		checkErrorService.delete(date);
		List<DayCheckLogInitModel> checkLogList = getCheckLogList(myLog,date,dto);
		checkTraceLog(myLog, date, checkLogList);
		myLog.info(logger, "核心与外围对账结束");
		myLog.info(logger, "外围与核心对账开始");
		//获取未对账的缴费记录
		List<CebaChargeLogModel> traceList = cebaChargeLogService.getCheckTrace(myLog,dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(), date);
		for(CebaChargeLogModel model:traceList) {
			CebaChargeLogModel record = new CebaChargeLogModel(myLog, model.getSysDate(), model.getSysTime(), model.getSysTraceno());
			record.setCheckState("4");
			cebaChargeLogService.updateCheck(record);
			if(model.getPayState().equals("1")) {
				myLog.error(logger,"光大云缴费【"+date+"】对账失败: 多出记录，渠道流水号【"+model.getSysTraceno()+"】，核心记账状态【"+model.getPayState()+"】");
				CebaTradeExecuteException e = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10008);
				throw e;
			}else {
				myLog.info(logger, "渠道多出往账数据，渠道日期【"+model.getSysDate()+"】，渠道流水【"+model.getSysTraceno()+"】，核心记账状态【"+model.getPayState()+"】");
			}
		}
		myLog.info(logger, "外围与核心对账结束");
		//对账文件名称
		String fileName = req.getTin().getFileName();
		myLog.info(logger, "光大银行与外围对账开始");
		checkErrorService.delete(date);
		List<DayCheckLogInitModel> checkLogList1 = getCheckLogList(myLog,fileName,date,dto);
		checkTraceLog(myLog, date, checkLogList1);
		myLog.info(logger, "光大银行与外围对账结束");
		myLog.info(logger, "外围与光大银行对账开始");
		//获取未对账的缴费记录
		List<CebaChargeLogModel> traceList1 = cebaChargeLogService.getCheckTrace(myLog,dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno(), date);
		for(CebaChargeLogModel model:traceList1) {
			CebaChargeLogModel record = new CebaChargeLogModel(myLog, model.getSysDate(), model.getSysTime(), model.getSysTraceno());
			record.setCheckState("4");
			cebaChargeLogService.updateCheck(record);
			if(model.getPayState().equals("1")) {
				myLog.error(logger,"光大云缴费【"+date+"】对账失败: 多出记录，渠道流水号【"+model.getSysTraceno()+"】，光大银行记账状态【"+model.getPayState()+"】");
				CebaTradeExecuteException e = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10008);
				throw e;
			}else {
				myLog.info(logger, "渠道多出往账数据，渠道日期【"+model.getSysDate()+"】，渠道流水【"+model.getSysTraceno()+"】，光大银行记账状态【"+model.getPayState()+"】");
			}
		}
		myLog.info(logger, "外围与光大银行对账结束");

		String num2 = cebaChargeLogService.getTraceNum(date, "2");
		String num3 = cebaChargeLogService.getTraceNum(date, "3");
		String num4 = cebaChargeLogService.getTraceNum(date, "4");
		int total = Integer.parseInt(num2)+Integer.parseInt(num3)+Integer.parseInt(num4);
		String s = "光大云缴费【"+date+"】对账统计：共【"+total+"】笔，其中已对账【"+num2+"】笔，光大银行多出【"+num3+"】笔，渠道多出【"+num4+"】笔";

		myLog.info(logger, s);
		REP_BJCEBBCNotify rep = new REP_BJCEBBCNotify();
		return rep;
	}
	
	private void checkTraceLog(MyLog myLog, String date, List<DayCheckLogInitModel> dayCheckLogList) throws SysTradeExecuteException {
		for(DayCheckLogInitModel model:dayCheckLogList) {
			//根据光大银行对账日期取渠道数据
			CebaChargeLogModel cebaChargeLogModel = cebaChargeLogService.queryLogByPk(myLog,model.getSysDate(),model.getSysTime(),model.getSysTraceno());
			//若渠道缺少数据则报错
			if(cebaChargeLogModel == null) {
				CheckErrorModel ceModel = new CheckErrorModel(myLog, model.getSysDate(), model.getSysTime(), model.getSysTraceno());
				ceModel.setBankBillNo(model.getBankBillNo());
				//对账标志，1-未对账，2-已对账，3-光大银行多，4-渠道多
				ceModel.setCheckFlag("3");
				ceModel.setPrePayState("");
				ceModel.setRePayState("2");
				ceModel.setTxAmt(model.getTxAmt());
				ceModel.setRemark("渠道补充数据，渠道日期【"+model.getSysDate()+"】，渠道流水【"+model.getSysTraceno()+"】");
				checkErrorService.insert(ceModel);
				
				myLog.error(logger, "光大云缴费【"+date+"】对账失败,渠道数据丢失: 渠道流水号【"+model.getSysTraceno()+"】渠道日期为【"+model.getSysDate()+"】");
				CebaTradeExecuteException e = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10011);
				throw e;
			}else {
				String payState = cebaChargeLogModel.getPayState(); //渠道记录的光大银行记账状态
					if(payState.equals("0")) {
						//光大银行与渠道状态一致
						CebaChargeLogModel record = new CebaChargeLogModel(myLog, model.getSysDate(), model.getSysTime(), model.getSysTraceno());
						record.setCheckState("2");
						cebaChargeLogService.updateCheck(record);
					}else if(payState.equals("2")||payState.equals("3")||payState.equals("4")) {
						//光大银行记账成功，渠道状态为超时、存款确认、冲正超时、冲正失败，修改渠道状态为成功
						CebaChargeLogModel record = new CebaChargeLogModel(myLog, model.getSysDate(), model.getSysTime(), model.getSysTraceno());
						record.setPayState("0");
						record.setCheckState("2");
						cebaChargeLogService.updateCheck(record);
						myLog.info(logger,"渠道调整光大银行记账状态，渠道日期【"+cebaChargeLogModel.getSysDate()+"】，渠道流水【"+model.getSysTraceno()+"】，调整前状态【"+payState+"】，调整后状态【1】");
					
						CheckErrorModel ceModel = new CheckErrorModel(myLog, model.getSysDate(), model.getSysTime(), model.getSysTraceno());
						ceModel.setPrePayState(payState);
						ceModel.setRePayState("0");
						ceModel.setCheckFlag("2");
						ceModel.setTxAmt(cebaChargeLogModel.getPayAmount());
						ceModel.setBankBillNo(cebaChargeLogModel.getBankBillNo());
						ceModel.setRemark("渠道调整光大银行记账状态，渠道日期【"+cebaChargeLogModel.getSysDate()+"】，渠道流水【"+cebaChargeLogModel.getSysTraceno()+"】，调整前状态【"+payState+"】，调整后状态【0】");
						checkErrorService.insert(ceModel);
					}else {
						myLog.error(logger, "光大云缴费【"+date+"】对账失败: 渠道流水号【"+cebaChargeLogModel.getSysTraceno()+"】记录光大记账状态为【"+cebaChargeLogModel.getPayState()+"】,与光大银行不符");
						CebaTradeExecuteException e = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10011);
						throw e;
					}
			}
		}
	}
	private List<DayCheckLogInitModel> getCheckLogList(MyLog myLog,String fileName,String date,DataTransObject dto) throws SysTradeExecuteException{
		String localFile = getCheckFile(myLog,date,fileName);
		//对账文件入库
		initCheckLog(localFile,myLog,date);
		//取对账文件数据
		List<DayCheckLogInitModel> dayCheckLogList = dayCheckLogService.getDayCheckLog(myLog, dto.getSysTime(), dto.getSysTraceno(), dto.getSysDate());
		return 	dayCheckLogList;
	}
	private String getEsbCheckFile(MyLog myLog, String date,DataTransObject dto) throws SysTradeExecuteException {
		ESB_REQ_50015000101 esbReq_50015000101 = new ESB_REQ_50015000101(myLog, dto.getSysDate(),dto.getSysTime(),dto.getSysTraceno());
		String txBrno = null,txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX+"txbrno");
			txTel = jedis.get(COMMON_PREFIX+"txtel");
		}
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_50015000101.getReqSysHead(),dto).setBranchId(txBrno).setUserId(txTel).setSourceType("MB").build();
		esbReq_50015000101.setReqSysHead(reqSysHead);
		ESB_REQ_50015000101.REQ_BODY esbReqBody_50015000101 = esbReq_50015000101.getReqBody();
		esbReqBody_50015000101.setChannelType("GD");
		esbReqBody_50015000101.setStartDate(date);
		esbReqBody_50015000101.setEndDate(date);
		esbReqBody_50015000101.setDirection("O");
		
		ESB_REP_50015000101 esbRep_50015000101 = forwardToESBService.sendToESB(esbReq_50015000101, esbReqBody_50015000101, ESB_REP_50015000101.class);
		String remoteFile = esbRep_50015000101.getRepSysHead().getFilePath();
		String fileName = esbRep_50015000101.getRepBody().getFileName();
		String localPath="";
		try (Jedis jedis = myJedis.connect()) {
			localPath = jedis.get(COMMON_PREFIX+"txt_path");
		}
		loadTraceLogFile(myLog, remoteFile, localPath+File.separator+"_"+fileName);
		return localPath+File.separator+"_"+fileName;
	}
	/**
	 * @Title: loadFile @Description: 从文件传输平台下载文件 @param @param
	 * myLog @param @param remoteFile 文件传输平台路径+文件名 @param @param localFile
	 * 文件本地路径+文件名 @param @throws PafTradeExecuteException 设定文件 @return void
	 * 返回类型 @throws
	 */
	private void loadTraceLogFile(MyLog myLog, String remoteFile, String localFile) throws SysTradeExecuteException {
		FtpClientConfigSet configSet = new FtpClientConfigSet();
		FtpGet ftpGet = null;
		try {
			ftpGet = new FtpGet(remoteFile, localFile, configSet);
			boolean result = ftpGet.doGetFile();
			if (!result) {
				myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！");
				CebaTradeExecuteException e = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10010);
				throw e;
			}
			myLog.info(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载成功！");
		} catch (Exception e) {
			myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！", e);
			CebaTradeExecuteException e1 = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10010,e.getMessage());
			throw e1;
		} finally {
			if (ftpGet != null) {
				try {
					ftpGet.close();
				} catch (FtpException e) {
					myLog.error(logger, "文件传输关闭连接失败！", e);
				}
			}
		}
	}
	private List<DayCheckLogInitModel> getCheckLogList(MyLog myLog,String date,DataTransObject dto) throws SysTradeExecuteException{
		String localFile = getEsbCheckFile(myLog,date,dto);
		//对账文件入库
		initCheckLog(localFile,myLog,date);
		//取对账文件数据
		List<DayCheckLogInitModel> dayCheckLogList = dayCheckLogService.getDayCheckLog(myLog, dto.getSysTime(), dto.getSysTraceno(), dto.getSysDate());
		return 	dayCheckLogList;
	}
	
	private void initCheckLog(String localFile, MyLog myLog,String platDate) throws SysTradeExecuteException {
		BufferedReader br = null;
		myLog.info(logger, "账户变动信息入库开始");
		try {
			dayCheckLogService.delete(platDate);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(localFile)),"GBK"));
			String lineTxt=null;
			while ((lineTxt = br.readLine()) != null) {
				lineTxt += "*|";
				String[] array = lineTxt.split("\\|");
                if(array.length==6){
                    DayCheckLogInitModel model = new DayCheckLogInitModel(myLog, Integer.parseInt(array[2].substring(0, 8)),Integer.parseInt(array[2].length()<14?"0":array[2].substring(8, 14)),Integer.parseInt(array[0]));
                    BigDecimal bg = new BigDecimal(array[1]==null?"0":array[1]);
                    model.setTxAmt(bg.movePointLeft(2).setScale(2, BigDecimal.ROUND_HALF_UP)); //交易金额
                    model.setBankBillNo(array[3]);
            		dayCheckLogService.dayCheckLogInit(model);
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
