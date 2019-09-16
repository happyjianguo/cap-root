package com.fxbank.cap.manager.quartz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.fxbank.cap.esb.model.ses.ESB_REP_30065001401;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30065001401;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.exception.YkwmTradeExecuteException;
import com.fxbank.cap.ykwm.model.CheckErrorModel;
import com.fxbank.cap.ykwm.model.DayCheckLogInitModel;
import com.fxbank.cap.ykwm.model.YkwmTraceLogModel;
import com.fxbank.cap.ykwm.service.ICheckErrorService;
import com.fxbank.cap.ykwm.service.IDayCheckLogService;
import com.fxbank.cap.ykwm.service.IPaymentService;
import com.fxbank.cap.ykwm.service.IYkwmSettleLogService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.util.FtpUtil;
import com.fxbank.cip.pub.service.IPublicService;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Configuration
@Component
@EnableScheduling
public class YkwmCheckTask {
	private static Logger logger = LoggerFactory.getLogger(YkwmCheckTask.class);

	private static final String JOBNAME = "YkwmCheck";

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IDayCheckLogService dayCheckLogService;
	
	@Reference(version = "1.0.0")
	private ICheckErrorService checkErrorService;
	
	@Reference(version = "1.0.0")
	private IYkwmSettleLogService ykwmSettleLogService;
	
	@Reference(version = "1.0.0")
	IPaymentService paymentService;	

	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "ykwm.";

	public void exec() throws Exception {
		MyLog myLog = new MyLog();
		Integer date = getPreDateByDate(getReqDto().getSysDate());
		myLog.info(logger, "核心与外围对账开始");
		checkErrorService.delete(date.toString());
		List<DayCheckLogInitModel> checkLogList = getCheckLogList(myLog,date);
		checkTraceLog(myLog,date, checkLogList);
		myLog.info(logger, "核心与外围对账结束");
		myLog.info(logger, "外围与核心对账开始");
		Integer sysTime = getReqDto().getSysTime();
		Integer sysTraceno = getReqDto().getSysTraceno();
		//获取未对账的缴费记录
		List<YkwmTraceLogModel> traceList = paymentService.getCheckTrace(myLog,date,
				sysTime,sysTraceno, date.toString());
		for(YkwmTraceLogModel model:traceList) {
			YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, model.getSysDate(), model.getSysTime(), model.getSysTraceno());
			record.setCapResult("4");
			paymentService.updateCheck(record);
			
			if(model.getCoResult().equals("0")||model.getCoResult().equals("2")) {
				CheckErrorModel aceModel = new CheckErrorModel(myLog, model.getSysDate(), 
						getReqDto().getSysTime(), model.getSysTraceno());
				aceModel.setPlatDate(model.getSysDate());
				aceModel.setPlatTrace(model.getSysTraceno());
				aceModel.setPreHostState(model.getCoResult());
				aceModel.setCheckFlag("4");
				aceModel.setRemark("渠道补充数据，渠道日期【"+model.getSysDate()+"】，渠道流水【"+model.getSysTraceno()+"】");
				checkErrorService.insert(aceModel);
				myLog.error(logger,"营口热电【"+date+"】对账失败: 多出记录，渠道流水号【"+model.getSysTraceno()+"】，核心状态【"+model.getCoResult()+"】");
				YkwmTradeExecuteException e = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10006);
				throw e;
			}else {
				myLog.info(logger, "渠道多出往账数据，渠道日期【"+model.getSysDate()+"】，渠道流水【"+model.getSysTraceno()+"】，核心状态【"+model.getCoResult()+"】");
			}
		}
		myLog.info(logger, "外围与核心对账结束");
		
		StringBuffer sb = new StringBuffer();
		//首行:总笔数|总金额|公司名称|批次号
		String totalNum = paymentService.getTotalNum(date.toString());
		String totalAmt = paymentService.getTotalAmt(date.toString());
		sb.append(totalNum).append("|");
		sb.append(totalAmt).append("|");
		String companyName = "";
		try (Jedis jedis = myJedis.connect()) {
			companyName = jedis.get(COMMON_PREFIX + "company_name");
		}
		sb.append(companyName).append("|");
		sb.append(date).append("|");
		sb.append("\n"); 
		List<YkwmTraceLogModel> upSndTraceList = paymentService.getUploadCheckSndTrace(myLog, 
				date,sysTime,sysTraceno, date.toString());
		for(YkwmTraceLogModel model : upSndTraceList) {
			sb.append(model.getUserCardNoT()).append("|"); //用户卡号
		    sb.append(model.getSysDate()).append("|");//批次号
		    sb.append(model.getTeCheckNum()).append("|");//流水号
		    sb.append(model.getPyFeeAmtT()).append("|");//交费金额
		    sb.append(model.getBillGetTpT()).append("|");//发票处理方式
		    sb.append(model.getCourierCmpnyIdT()).append("|");//快递公司ID
		    sb.append(model.getMailAddrT()).append("|");//邮寄地址
		    sb.append(model.getCnttPhnT()).append("|");//联系电话
		    sb.append(model.getLnmT3()).append("|");//联系人
		    sb.append(model.getPostNoT5()).append("|");//邮编
		    sb.append(model.getInvoiceList().get(0).getInvoiceTitle()).append("|");//发票抬头
			sb.append("\n"); 
		}
		
		//生成本地对账文件并上传至FTP服务器
		String fileName=companyName+date+".txt";
		ftpUpload(myLog, fileName, sb.toString());
		String num2 = paymentService.getTraceNum(date.toString(), "2");
		String num3 = paymentService.getTraceNum(date.toString(), "3");
		String num4 = paymentService.getTraceNum(date.toString(), "4");
		int total = Integer.parseInt(num2)+Integer.parseInt(num3)+Integer.parseInt(num4);
		String checkMsg = "营口热电【"+date+"】对账统计：共【"+total+"】笔，其中已对账【"+num2+"】笔，核心多出【"+num3+"】笔，渠道多出【"+num4+"】笔";
		myLog.info(logger, checkMsg);
		
		if(checkLogList.size()==Integer.parseInt(num2)) {
			ykwmSettleLogService.initSettleLog(myLog, date, new BigDecimal(totalAmt));
			checkMsg += "对账成功";
		}else {
			checkMsg += "对账失败";
		}

	    myLog.info(logger, "营口热力对账完成");
	}
	
	private void checkTraceLog(MyLog myLog,Integer date, List<DayCheckLogInitModel> dayCheckLogList) throws SysTradeExecuteException {
		for(DayCheckLogInitModel model:dayCheckLogList) {
			//根据核心对账数据取渠道往账数据
			YkwmTraceLogModel ykwmTracelogModel = paymentService.queryLogByPk(myLog,model.getSysDate(),model.getSysTime(),model.getSysTraceno());
			//若渠道缺少数据则报错
			if(ykwmTracelogModel == null) {
				CheckErrorModel aceModel = new CheckErrorModel(myLog, model.getSysDate(), 
						getReqDto().getSysTime(), model.getSysTraceno());
				aceModel.setPlatDate(model.getSysDate());
				aceModel.setPlatTrace(model.getSysTraceno());
				aceModel.setPreHostState("");
				aceModel.setReHostState("0");
				aceModel.setCheckFlag("3");
				aceModel.setRemark("渠道补充数据，渠道日期【"+model.getSysDate()+"】，渠道流水【"+model.getSysTraceno()+"】");
				checkErrorService.insert(aceModel);
				
				myLog.error(logger, "营口热电【"+date+"】对账失败,渠道数据丢失: 核心流水号【"+model.getHostTraceno()+"】核心日期为【"+model.getHostDate()+"】");
				YkwmTradeExecuteException e = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10006);
				throw e;
			}else {
				String hostState = ykwmTracelogModel.getCoResult(); //渠道记录的核心状态
				
					if(hostState.equals("0")) {
						//核心与渠道状态一致
						YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, model.getSysDate(), model.getSysTime(), model.getSysTraceno());
						record.setCapResult("2");
						paymentService.updateCheck(record);
					}else if(hostState.equals("2")||hostState.equals("3")||hostState.equals("4")) {
						//核心记账成功，渠道状态为超时、存款确认、冲正超时、冲正失败，修改渠道状态为成功
						YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, model.getSysDate(), model.getSysTime(), model.getSysTraceno());
						record.setCoResult("0");
						record.setCapResult("2");
						paymentService.updateCheck(record);
						myLog.info(logger,"渠道调整往账数据核心状态，渠道日期【"+ykwmTracelogModel.getSysDate()+"】，渠道流水【"+model.getSysTraceno()+"】，调整前状态【"+hostState+"】，调整后状态【1】");
					
						CheckErrorModel ceModel = new CheckErrorModel(myLog, model.getSysDate(), getReqDto().getSysTime(), model.getSysTraceno());
						ceModel.setPlatDate(model.getSysDate());
						ceModel.setPlatTrace(model.getSysTraceno());
						ceModel.setPreHostState(hostState);
						ceModel.setReHostState("0");
						ceModel.setCheckFlag("2");
						ceModel.setTxAmt(new BigDecimal(ykwmTracelogModel.getPyFeeAmtT()));
						ceModel.setAcctNo(ykwmTracelogModel.getAcctNoT());
						ceModel.setUserCard(ykwmTracelogModel.getUserCardNoT());
						ceModel.setUserName(ykwmTracelogModel.getLnmT3());
						ceModel.setRemark("渠道调整往账数据核心状态，渠道日期【"+ykwmTracelogModel.getSysDate()+"】，渠道流水【"+ykwmTracelogModel.getSysTraceno()+"】，调整前状态【"+hostState+"】，调整后状态【0】");
						checkErrorService.insert(ceModel);
					}else {
						myLog.error(logger, "营口热电【"+date+"】对账失败: 渠道流水号【"+ykwmTracelogModel.getSysTraceno()+"】记录核心状态为【"+ykwmTracelogModel.getCoResult()+"】,与核心记账状态不符");
						YkwmTradeExecuteException e = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10006);
						throw e;
					}
			}
		}
	}
	private List<DayCheckLogInitModel> getCheckLogList(MyLog myLog,Integer platDate) throws SysTradeExecuteException{
		//请求核心接口，获取来账文件
		String localFile = getEsbCheckFile(myLog,platDate.toString());
		//对账文件入库
		initCheckLog(localFile,myLog,platDate);
		//取对账文件数据
		List<DayCheckLogInitModel> dayCheckLogList = dayCheckLogService.getDayCheckLog(myLog, 
				getReqDto().getSysTime(), getReqDto().getSysTraceno(), platDate);
		return 	dayCheckLogList;
	}
	
	private void initCheckLog(String localFile, MyLog myLog,Integer platDate) throws SysTradeExecuteException {
		BufferedReader br = null;
		myLog.info(logger, "账户变动信息入库开始");
		try {
			dayCheckLogService.delete(platDate.toString());
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(localFile)),"GBK"));
			String lineTxt=null;
			while ((lineTxt = br.readLine()) != null) {
				lineTxt += "*|";
				String[] array = lineTxt.split("\\|");
                if(array.length==4){
                    continue;
                }
                DayCheckLogInitModel model = new DayCheckLogInitModel(myLog, platDate,
                		getReqDto().getSysTime(),Integer.parseInt(array[4]));
                model.setBranchNo(array[7]); //机构号
                model.setHostDate(Integer.parseInt(array[0])); //核心交易日期
                model.setHostTraceno("ENS"+array[0]+array[2]); //核心流水号
                BigDecimal bg = new BigDecimal(array[10]==null?"0":array[10]);
                model.setTxAmt(bg); //交易金额
                model.setAccountno(array[5]); //交易账户 
                model.setTelNo(array[8]); //柜员号
                
        		dayCheckLogService.dayCheckLogInit(model);
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
			myLog.info(logger, "核心对账信息入库结束");
		}
		
		
	}
	
	private String getEsbCheckFile(MyLog myLog,String date) throws SysTradeExecuteException {
		ESB_REQ_30065001401 esbReq_30065001401 = new ESB_REQ_30065001401(myLog, Integer.parseInt(date),
				getReqDto().getSysTime(),getReqDto().getSysTraceno());
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "ykwm_txbrno");
			txTel = jedis.get(COMMON_PREFIX + "ykwm_txtel");
		}

		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30065001401.getReqSysHead(),getReqDto())
				.setBranchId(txBrno).setUserId(txTel)
				.setSourceType("BH").build();
		esbReq_30065001401.setReqSysHead(reqSysHead);
		ESB_REQ_30065001401.REQ_BODY esbReqBody_30065001401 = esbReq_30065001401.getReqBody();
		esbReqBody_30065001401.setBusiType("ykjf");
		esbReqBody_30065001401.setSettlementDate(date);
		
		ESB_REP_30065001401 esbRep_30065001401 = forwardToESBService.sendToESB(esbReq_30065001401, esbReqBody_30065001401, ESB_REP_30065001401.class);
		String remoteFile = esbRep_30065001401.getRepSysHead().getFilePath();
		String fileName = esbRep_30065001401.getRepBody().getFileName();
		String localPath="";
		try (Jedis jedis = myJedis.connect()) {
			localPath = jedis.get(COMMON_PREFIX+"txt_path");
		}
		loadTraceLogFile(myLog, remoteFile, localPath+File.separator+fileName);
		return localPath+File.separator+fileName;
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
				YkwmTradeExecuteException e = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10005);
				throw e;
			}
			myLog.info(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载成功！");
		} catch (Exception e) {
			myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！", e);
			YkwmTradeExecuteException e1 = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10005,
					e.getMessage());
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
	
	private DataTransObject getReqDto() {
		DataTransObject reqDto = new DataTransObject();
		Integer sysDate = publicService.getSysDate("CIP");
		Integer sysTime = publicService.getSysTime();
		Integer sysTraceno = publicService.getSysTraceno();
		reqDto.setSourceType("BH");
		reqDto.setSysDate(sysDate);
		reqDto.setSysTime(sysTime);
		reqDto.setSysTraceno(sysTraceno);
		return reqDto;
	}
	
	private String ftpUpload(MyLog myLog,String fileName,String str) throws YkwmTradeExecuteException {
		String localPath="";
		File file = null;
		BufferedWriter bw = null;
		try {
			try (Jedis jedis = myJedis.connect()) {
				localPath = jedis.get(COMMON_PREFIX+"txt_path");
			}
			file = new File(localPath+File.separator+fileName);
			if(!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream writerStream = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(writerStream, "GBK"));
			bw.write(str);
			
		}catch (Exception e) {
			myLog.error(logger,"生成营口热电对账文件失败: "+e);
			YkwmTradeExecuteException e1 = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10006);
			throw e1;
		}finally {
			try {
				bw.close();
			} catch (IOException e) {
				logger.error("关闭临时文件异常", e);
			}
		}
		
		FtpUtil ftp = new FtpUtil("GBK");
		try {
			String host, port, user, password;
			try (Jedis jedis = myJedis.connect()) {
				host = jedis.get(COMMON_PREFIX+"ftp_ip");
				port = jedis.get(COMMON_PREFIX+"ftp_port");
				user = jedis.get(COMMON_PREFIX+"ftp_user");
				password = jedis.get(COMMON_PREFIX+"ftp_pass");
			}
			ftp.connect(host, Integer.parseInt(port), user, password);
			ftp.upload(fileName, file);
		}catch (Exception e) {
			myLog.error(logger,"FTP上传营口热电对账文件失败: "+e);
			YkwmTradeExecuteException e1 = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10006);
			throw e1;
		}finally {
			try {
				ftp.disconnect();
			} catch (IOException e) {
				logger.error("关闭FTP异常", e);
			}
		}
		
		return localPath+File.separator+fileName;
	}


	
	/** 
	* @Title: getPreDateByDate 
	* @Description: 取日期前一天 
	* @param @param strData
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws 
	*/
	private Integer getPreDateByDate(Integer strData) {
		String preDate = "";
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(strData.toString());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		c.setTime(date);
		int day1 = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day1 -1);
		preDate = sdf.format(c.getTime());
		return Integer.parseInt(preDate);
	}


	@Bean(name = "ykwmCheckJobDetail")
	public MethodInvokingJobDetailFactoryBean ykwmCheckJobDetail(YkwmCheckTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "ykwmCheckJobTrigger")
	public CronTriggerFactoryBean ykwmCheckJobTrigger(
			@Qualifier("ykwmCheckJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		String exp = null;
		try (Jedis jedis = myJedis.connect()) {
			exp = jedis.get(QuartzJobConfigration.YKWM_CRON + JOBNAME);
		}
		if (exp == null) {
			exp = "0 0 4 * * ?";
		}
		logger.info("任务[" + JOBNAME + "]启动[" + exp + "]");
		tigger.setCronExpression(exp);
		tigger.setName(JOBNAME);
		return tigger;
	}

	@Bean(name = "ykwmCheckScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("ykwmCheckJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}
}
