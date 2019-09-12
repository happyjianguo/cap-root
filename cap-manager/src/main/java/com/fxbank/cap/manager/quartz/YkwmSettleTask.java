package com.fxbank.cap.manager.quartz;

import java.util.List;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.model.ses.ESB_REP_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011000101;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.model.YkwmSettleLogModel;
import com.fxbank.cap.ykwm.service.IYkwmSettleLogService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
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
public class YkwmSettleTask {
	private static Logger logger = LoggerFactory.getLogger(YkwmSettleTask.class);

	private static final String JOBNAME = "YkwmSettle";

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IYkwmSettleLogService ykwmSettleLogService;
	
	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "ykwm.";

	public void exec() throws Exception {
		MyLog myLog = new MyLog();
		myLog.info(logger, "开始进行缴费清算处理");
		List<YkwmSettleLogModel> list;
		try {
			// 状态 （0:对账完成；1:等待重新汇款；2:转账成功；3:转账失败；4:转账超时）
			// 查询10天内0、1状态的日志
			list = ykwmSettleLogService.querySettleLog(myLog, getReqDto().getSysDate());
		} catch (SysTradeExecuteException e) {
			myLog.error(logger, "查询缴费清算数据失败");
			throw new RuntimeException("查询缴费清算数据失败");
		}
		if (list == null || list.size() == 0) {
			myLog.info(logger, "没有待处理缴费清算数据");
			return;
		}
		for (YkwmSettleLogModel model : list) {
			try {
				if (model.getTxSts().equals("0") || model.getTxSts().equals("1")) {
					ESB_REP_30011000101 rep = hostCharge(myLog, model.getTxAmt().toString(),model.getChkDate().toString());
					String hostCode = rep.getRepSysHead().getRet().get(0).getRetCode();
					String hostMsg = rep.getRepSysHead().getRet().get(0).getRetMsg();
					Integer hostDate = Integer.parseInt(rep.getRepSysHead().getTranDate());
					String hostTraceno = rep.getRepBody().getReference();
					model.setHostCode(hostCode);
					model.setHostMsg(hostMsg);
					model.setHostDate(hostDate);
					model.setHostTraceno(hostTraceno);
					model.setTxSts("2");
					ykwmSettleLogService.updateSettle(myLog, model);
				}
			} catch (SysTradeExecuteException e) {
				// 超时 状态改为4
				if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000004)
						|| e.getRspCode().equals("ESB_E_000052")) {
					model.setTxSts("4");
					ykwmSettleLogService.updateSettle(myLog, model);
					myLog.error(logger, "营口热力缴费清算超时，清算日期" + model.getChkDate(), e);
					continue;
				} else {
					model.setTxSts("3");
					model.setHostCode(e.getRspCode());
					model.setHostMsg(e.getRspMsg());
					ykwmSettleLogService.updateSettle(myLog, model);
					myLog.error(logger, "营口热力缴费清算失败，清算日期" + model.getChkDate(), e);
					continue;
				}
			} catch (Exception e) {
				model.setTxSts("3");
				ykwmSettleLogService.updateSettle(myLog, model);
				myLog.error(logger, "程序运行异常", e);
				throw e;
			}
		}
		myLog.info(logger, "缴费清算处理完成");
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


	/** 
	* @Title: hostCharge 
	* @Description: 核心记账
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	public ESB_REP_30011000101 hostCharge(MyLog myLog,String txAmt,String date) throws SysTradeExecuteException {
		ESB_REQ_30011000101 req_30011000101 = new ESB_REQ_30011000101(myLog, getReqDto().getSysDate(),
				getReqDto().getSysTime(),getReqDto().getSysTraceno());
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		String baseAcctNo = null;
		String othBaseAcctNo = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "ykwm_txbrno");
			txTel = jedis.get(COMMON_PREFIX + "ykwm_txtel");
			baseAcctNo = jedis.get(COMMON_PREFIX + "settle_base_acct_no");
			othBaseAcctNo = jedis.get(COMMON_PREFIX + "settle_oth_base_acct_no");
		}
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(req_30011000101.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).build();
		reqSysHead.setSourceBranchNo("HOST|hostToesb|RZPK||");
		req_30011000101.setReqSysHead(reqSysHead);
		ESB_REQ_30011000101.REQ_BODY esb_reqBody = req_30011000101.getReqBody();
		esb_reqBody.setTranType("BH15");
		esb_reqBody.setTranCcy("CNY");
		esb_reqBody.setTranAmt(txAmt);// 缴费金额
		esb_reqBody.setBaseAcctNo(baseAcctNo);// 卡号
		esb_reqBody.setOthBaseAcctNo(othBaseAcctNo);// 对方账号
		esb_reqBody.setChannelType("BH");// 渠道类型 ESB写死为
		esb_reqBody.setSettlementDate(date);// 渠道日期
		esb_reqBody.setWithdrawalType("P");
		esb_reqBody.setNarrative("营口缴费过度户转账");
		// 捕获异常 对核心记账结果进行判断
		// *****************************

			ESB_REP_30011000101 eSB_REP_30011000101 = forwardToESBService.sendToESB(req_30011000101,
					req_30011000101.getReqBody(), ESB_REP_30011000101.class);
			return eSB_REP_30011000101;
	}

	@Bean(name = "ykwmSettleJobDetail")
	public MethodInvokingJobDetailFactoryBean ykwmaSettleJobDetail(YkwmSettleTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "ykwmSettleJobTrigger")
	public CronTriggerFactoryBean ykwmSettleJobTrigger(
			@Qualifier("ykwmSettleJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		String exp = null;
		try (Jedis jedis = myJedis.connect()) {
			exp = jedis.get(QuartzJobConfigration.YKWM_CRON + JOBNAME);
		}
		if (exp == null) {
			exp = "0 0 5 * * ?";
		}
		logger.info("任务[" + JOBNAME + "]启动[" + exp + "]");
		tigger.setCronExpression(exp);
		tigger.setName(JOBNAME);
		return tigger;
	}

	@Bean(name = "ykwmSettleScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("ykwmSettleJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}
}

