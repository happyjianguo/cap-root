package com.fxbank.cap.manager.quartz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
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
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.model.CebaChargeLogModel;
import com.fxbank.cap.ceba.model.CebaRefundeLogModel;
import com.fxbank.cap.ceba.service.ICebaChargeLogService;
import com.fxbank.cap.ceba.service.ICebaRefundeLogService;
import com.fxbank.cap.ceba.service.ICebaSettleLogService;
import com.fxbank.cap.esb.model.ses.ESB_REP_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011000101;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.util.SFtpUtil;
import com.fxbank.cip.pub.service.IPublicService;
import redis.clients.jedis.Jedis;

/** 
* @ClassName: CebaRefundeTask 
* @Description: 退款定时任务
* @作者 杜振铎
* @date 2019年8月8日 下午4:47:39 
*  
*/
@Configuration
@Component
@EnableScheduling
public class CebaRefundeTask {
	private static Logger logger = LoggerFactory.getLogger(CebaRefundeTask.class);

	private static final String JOBNAME = "CebaRefunde";

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private ICebaChargeLogService cebaChargeLogService;

	@Reference(version = "1.0.0")
	private ICebaRefundeLogService cebaRefundeLogService;

	@Reference(version = "1.0.0")
	private ICebaSettleLogService cebaSettleLogService;

	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "ceba.";

	public void exec() throws Exception {
		MyLog myLog = new MyLog();
		myLog.info(logger, "开始进行批量退款处理");
		// 商户简称
		String partnerName = null;
		try (Jedis jedis = myJedis.connect()) {
			partnerName = jedis.get(COMMON_PREFIX + "parter_name");
		}
		Integer sysDate = getReqDto().getSysDate();
		// 导入当天退款文件
		String fileName = null;
		String localFile = null;
		fileName = partnerName + sysDate + "01Refunde.txt";
		localFile = getRefundeFile(myLog, fileName);
		// 根据退款文件申请日期判断是否导入过
		if (!cebaRefundeLogService.isInitRefundeLog(myLog, sysDate)) {
			initRefundeLog(localFile, myLog, sysDate);
		}
		List<CebaRefundeLogModel> list;
		try {
			//退款状态，0未退款,1已退款,2退款失败,3退款超时,4销账流水日志未查到
			//查询3天内未退款的日志
			list = cebaRefundeLogService.queryRefundeLog(myLog, sysDate);
		} catch (SysTradeExecuteException e) {
			myLog.error(logger, "查询待退款数据失败");
			throw new RuntimeException("查询待退款数据失败");
		}
		if (list == null || list.size() == 0) {
			myLog.info(logger, "没有待处理退款数据");
			return;
		}
		for (CebaRefundeLogModel model : list) {
			CebaChargeLogModel chargeLog = cebaChargeLogService.queryLogByPk(myLog, model.getSysDate(),
					model.getSysTraceno());
			if(null==chargeLog) {
				model.setStatus("4");
				cebaRefundeLogService.updateRefundeLog(myLog, model);
				myLog.error(logger, "销账流水日志未查到该条流水，渠道日期"+model.getSysDate()+",渠道流水"+model.getSysTraceno());
				continue;
			}
			model.setStatus("*");	//退款中
			cebaRefundeLogService.updateRefundeLog(myLog, model);
			ESB_REP_30011000101 esbRep30011000101 = null;
			try {
				 esbRep30011000101 = hostRefunde(myLog, chargeLog);
			} catch (SysTradeExecuteException e) {
				if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000004)
						|| e.getRspCode().equals("ESB_E_000052")) {
					// 退款超时
					model.setStatus("3");
					cebaRefundeLogService.updateRefundeLog(myLog, model);
					myLog.error(logger, "退款文件退款超时,渠道日期" + model.getSysDate() + "渠道流水号" + model.getSysTraceno(), e);
					continue;
				} else {
					// 退款失败
					model.setStatus("2");
					model.setHostCode(e.getRspCode());
					model.setHostMsg(e.getRspMsg());
					cebaRefundeLogService.updateRefundeLog(myLog, model);
					myLog.error(logger, "退款文件退款失败,渠道日期" + model.getSysDate() + "渠道流水号" + model.getSysTraceno(), e);
				    continue;
				}
			}
			model.setStatus("1");
			model.setHostCode(esbRep30011000101.getRepSysHead().getRet().get(0).getRetCode());
			model.setHostMsg(esbRep30011000101.getRepSysHead().getRet().get(0).getRetMsg());
			cebaRefundeLogService.updateRefundeLog(myLog, model);
			myLog.info(logger, "退款文件退款成功,渠道日期" + model.getSysDate() + "渠道流水号" + model.getSysTraceno());
		}
		myLog.info(logger, "批量退款处理完成");
	}

	public ESB_REP_30011000101 hostRefunde(MyLog myLog, CebaChargeLogModel model) throws SysTradeExecuteException {
		ESB_REQ_30011000101 esbReq_30011000101 = new ESB_REQ_30011000101(myLog, getReqDto().getSysDate(),
				getReqDto().getSysTime(), getReqDto().getSysTraceno());
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "ceba_txbrno");
			txTel = jedis.get(COMMON_PREFIX + "ceba_txtel");
		}
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000101.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).setSourceType("GD").build();
		esbReq_30011000101.setReqSysHead(reqSysHead);
		// 退款账号
		String othBaseAcctNo = null;
		try (Jedis jedis = myJedis.connect()) {
			othBaseAcctNo = jedis.get(COMMON_PREFIX + "refunde_acct_no");
		}
		ESB_REQ_30011000101.REQ_BODY reqBody_30011000101 = esbReq_30011000101.getReqBody();
		// 账号/卡号
		reqBody_30011000101.setBaseAcctNo(model.getPayAccount());
		reqBody_30011000101.setOthBaseAcctNo(othBaseAcctNo);
		// 账户名称
		reqBody_30011000101.setAcctName(model.getCustomerName());
		// 交易类型
		reqBody_30011000101.setTranType("GD01");
		// 交易币种
		reqBody_30011000101.setTranCcy("CNY");
		//支取方式 支取时必输S凭印鉴支取P凭密码支取W无密码无印鉴支取B凭印鉴和密码支取O凭证件支取
		//reqBody_30011000101.setWithdrawalType("W");
		// 交易金额
		reqBody_30011000101.setTranAmt(model.getPayAmount().toString());
		// 记账渠道类型
		reqBody_30011000101.setChannelType("GD");
		// 清算日期
		reqBody_30011000101.setSettlementDate(getReqDto().getSysDate().toString());
		// 对账标识,Y-参与对账;N-不参与对账
		reqBody_30011000101.setCollateFlag("Y");
		ESB_REP_30011000101 esbRep_30011000101 = forwardToESBService.sendToESB(esbReq_30011000101, reqBody_30011000101,
				ESB_REP_30011000101.class);
		return esbRep_30011000101;
	}

	private void initRefundeLog(String localFile, MyLog myLog, Integer refundeDate) throws SysTradeExecuteException {
		BufferedReader br = null;
		myLog.info(logger, "光大银行退款文件入库开始");
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(localFile)), "GBK"));
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				lineTxt += "|";
				String[] array = lineTxt.split("\\|");
				if (array.length == 2) {
					int totalNum = Integer.parseInt(array[0]);
					BigDecimal totalAmt = new BigDecimal(array[1]).movePointLeft(2).setScale(2,
							BigDecimal.ROUND_HALF_UP);
					int chargeTotalNum = Integer.parseInt(cebaChargeLogService.getTotalNum(refundeDate.toString()));
					String chargeAmt = cebaChargeLogService.getTotalAmt(refundeDate.toString());
					BigDecimal chargeTotalAmt = new BigDecimal(chargeAmt);
					if (totalNum != chargeTotalNum || totalAmt.compareTo(chargeTotalAmt) != 0) {
						myLog.error(logger, "退款文件总金额和总笔数与销账流水日志不符,渠道日期"+refundeDate);
						break;
					}
				}
				if (array.length == 3) {
					Integer platDate = Integer.parseInt(array[1]);
					Integer platTraceno = Integer.parseInt(array[2]);
					CebaRefundeLogModel model = new CebaRefundeLogModel(myLog, platDate, null, platTraceno);
					model.setFlag("1");
					cebaRefundeLogService.initRefundeLog(myLog, model);
				}

			}

		} catch (Exception e) {
			myLog.error(logger, "文件【" + localFile + "】插入失败", e);
			throw new RuntimeException("文件【" + localFile + "】插入失败");
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					myLog.error(logger, "文件流关闭失败", e);
				}
			}
			myLog.info(logger, "光大银行对账信息入库结束");
		}

	}

	private String getRefundeFile(MyLog myLog, String fileName) throws SysTradeExecuteException {
		// 对账文件保存到本地路径
		String localPath = null;
		// 光大银行上传文件FTP地址
		String ftpIP = null;
		// 光大银行上传文件FTP端口号
		Integer ftpPort = null;
		// 光大银行上传文件FTP用户名
		String ftpUser = null;
		// 光大银行上传文件FTP密码
		String ftpPassword = null;
		// 光大银行上传文件FTP路径
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
			File localFilePath = new File(localPath);
			if (!localFilePath.exists()) {
				localFilePath.mkdirs();
			}
			sftpUtil.download(ftpPath, fileName, localPath + File.separator + fileName);
			// 上传商户T日交易的对账文件时，会按照T日的日期为名在商户指定目录下创建一个目录，然后把对应T日的对账文件放到这个目录下。
		} catch (Exception e) {
			myLog.error(logger, "下载对账文件失败", e);
		}
		return localPath + File.separator + fileName;
	}

	private DataTransObject getReqDto() {
		DataTransObject reqDto = new DataTransObject();
		Integer sysDate = publicService.getSysDate("CIP");
		Integer sysTime = publicService.getSysTime();
		Integer sysTraceno = publicService.getSysTraceno();
		reqDto.setSourceType("GD");
		reqDto.setSysDate(sysDate);
		reqDto.setSysTime(sysTime);
		reqDto.setSysTraceno(sysTraceno);
		return reqDto;
	}

	@Bean(name = "cebaRefundeJobDetail")
	public MethodInvokingJobDetailFactoryBean cebaRefundeJobDetail(CebaRefundeTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "cebaRefundeJobTrigger")
	public CronTriggerFactoryBean cebaRefundeJobTrigger(
			@Qualifier("cebaRefundeJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		String exp = null;
		try (Jedis jedis = myJedis.connect()) {
			exp = jedis.get(QuartzJobConfigration.CEBA_CRON + JOBNAME);
		}
		if (exp == null) {
			exp = "0 0 23 ? * *";
		}
		logger.info("任务[" + JOBNAME + "]启动[" + exp + "]");
		tigger.setCronExpression(exp);
		tigger.setName(JOBNAME);
		return tigger;
	}

	@Bean(name = "cebaRefundeScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("cebaRefundeJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}
}
