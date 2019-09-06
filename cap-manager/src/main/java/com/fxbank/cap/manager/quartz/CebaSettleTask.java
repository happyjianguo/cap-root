/*
 * @Description: In User Settings Edit
 * @Author: your name
 * @Date: 2019-08-14 08:15:40
 * @LastEditTime: 2019-08-14 08:30:22
 * @LastEditors: Please set LastEditors
 */
package com.fxbank.cap.manager.quartz;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.model.CebaSettleLogModel;
import com.fxbank.cap.ceba.service.ICebaSettleLogService;
import com.fxbank.cap.esb.model.cnaps2.ESB_REP_30041000406;
import com.fxbank.cap.esb.model.cnaps2.ESB_REP_30043000102;
import com.fxbank.cap.esb.model.cnaps2.ESB_REQ_30041000406;
import com.fxbank.cap.esb.model.cnaps2.ESB_REQ_30043000102;
import com.fxbank.cap.esb.model.sms.ESB_REP_50022000401;
import com.fxbank.cap.esb.model.sms.ESB_REQ_50022000401;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.constant.CIP;
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
public class CebaSettleTask {
	private static Logger logger = LoggerFactory.getLogger(CebaSettleTask.class);

	private static final String JOBNAME = "CebaSettle";

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private ICebaSettleLogService settleLogService;

	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "ceba.";

	public void exec() throws Exception {
		MyLog myLog = new MyLog();
		String mobNos;
		String message = "光大云缴费清算信息:";
		try (Jedis jedis = myJedis.connect()) {
			mobNos = jedis.get(COMMON_PREFIX + "settleNoticeMobNos");
		}
		myLog.info(logger, "开始进行缴费清算处理");
		List<CebaSettleLogModel> list;
		try {
			// 0:对账完成；1:等待重新汇款；2:汇款完成；3:汇款成功；4:汇款失败,报错状态改为1，结束任务
			// 查询10天内0、1、2状态的日志
			list = settleLogService.querySettleLog(myLog, getReqDto().getSysDate());
		} catch (SysTradeExecuteException e) {
			myLog.error(logger, "查询缴费清算数据失败");
			throw new RuntimeException("查询缴费清算数据失败");
		}
		if (list == null || list.size() == 0) {
			myLog.info(logger, "没有待处理缴费清算数据");
			return;
		}
		boolean isSend = false;
		for (CebaSettleLogModel model : list) {
			// 跨行付款
			try {
				if (model.getTxSts().equals("0") || model.getTxSts().equals("1")) {
					ESB_REP_30041000406 rep30041000406 = outerCharge(myLog, model.getTxAmt());
					String cnapsCode = rep30041000406.getRepSysHead().getRet().get(0).getRetCode();
					String cnapsMsg = rep30041000406.getRepSysHead().getRet().get(0).getRetMsg();
					Integer cnapsDate = Integer.parseInt(rep30041000406.getRepBody().getSystemDate());
					String cnapsTraceno = rep30041000406.getRepBody().getSystemReference();
					settleLogService.updateTranComplete(myLog, model.getChkDate(), cnapsCode, cnapsMsg, cnapsDate,
							cnapsTraceno);
				}
			} catch (SysTradeExecuteException e) {
				// 超时 状态改为4
				if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000004)
						|| e.getRspCode().equals("ESB_E_000052")) {
					settleLogService.updateSettleTxSts(myLog, model.getChkDate(), "4");
					myLog.error(logger, "光大云缴费清算跨行付款超时，清算日期" + model.getChkDate(), e);
					continue;
				} else {
					settleLogService.updateSettleTxSts(myLog, model.getChkDate(), "4");
					myLog.error(logger, "光大云缴费清算跨行付款失败，清算日期" + model.getChkDate(), e);
					continue;
				}
			} catch (Exception e) {
				settleLogService.updateSettleTxSts(myLog, model.getChkDate(), "4");
				myLog.error(logger, "程序运行异常", e);
				throw e;
			}

			if (model.getTxSts().equals("2")) {
				try {
					ESB_REP_30043000102 rep30043000102 = outerCrdtTranResult(myLog, model.getCnapsDate(),
							model.getCnapsTraceno());
					// 1-成功2-失败3-已撤销 D-大额未清算C-小额未清算
					String tranStatus = rep30043000102.getRepBody().getTranStatus();
					if ("1".equals(tranStatus)) {
						settleLogService.updateSettleTxSts(myLog, model.getChkDate(), "3");
						message += model.getChkDate() + "成功,";
					} else {
						settleLogService.updateSettleTxSts(myLog, model.getChkDate(), "4");
						message += model.getChkDate() + "失败,";
					}
					isSend = true;
				} catch (Exception e) {
					myLog.error(logger, "汇款结果查询异常:", e);
				}
			}

		}
		if (isSend) {
			try {
				sendMessage(mobNos, message.substring(0, message.length()-1));
			}catch(SysTradeExecuteException e) {
				myLog.error(logger, "缴费清算短信通知发送失败",e);
			}
		}
		myLog.info(logger, "缴费清算处理完成");
	}

	/**
	 * @Title: outerCrdtTranResult @Description: 跨行付款交易结果查询 @param @param
	 *         myLog @param @param channelDate @param @param
	 *         chanelSeqNo @param @return @param @throws SysTradeExecuteException
	 *         设定文件 @return ESB_REP_30043000102 返回类型 @throws
	 */
	private ESB_REP_30043000102 outerCrdtTranResult(MyLog myLog, Integer channelDate, String chanelSeqNo)
			throws SysTradeExecuteException {
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "ceba_txbrno");
			txTel = jedis.get(COMMON_PREFIX + "ceba_txtel");
		}

		ESB_REQ_30043000102 esbReq_30043000102 = new ESB_REQ_30043000102(myLog, getReqDto().getSysDate(),
				getReqDto().getSysTime(), getReqDto().getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30043000102.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30043000102.setReqSysHead(reqSysHead);

		ESB_REQ_30043000102.REQ_BODY reqBody_30043000102 = esbReq_30043000102.getReqBody();

		ESB_REP_30043000102 esb_rep_30043000102 = null;
		// 渠道日期
		reqBody_30043000102.setOrigChannelDate(channelDate.toString());
		// 渠道流水号
		reqBody_30043000102.setOrigChannelSeqNo(chanelSeqNo);
		// 渠道系统ID
		reqBody_30043000102.setOrigSysId("HVPS");
		try {
			// 如果第一次查询没查到内容再查询一次
			esb_rep_30043000102 = forwardToESBService.sendToESB(esbReq_30043000102, reqBody_30043000102,
					ESB_REP_30043000102.class);
		} catch (SysTradeExecuteException e) {
			if (e.getRspCode().equals("CN00")) {
				try {
					esb_rep_30043000102 = forwardToESBService.sendToESB(esbReq_30043000102, reqBody_30043000102,
							ESB_REP_30043000102.class);
				} catch (SysTradeExecuteException e1) {
					logger.error(e1.getRspCode() + " | " + e1.getRspMsg());
					throw e1;
				}
			} else {
				logger.error(e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
		}

		return esb_rep_30043000102;
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

	private void sendMessage(String mobNo, String message) throws SysTradeExecuteException {
		// 调用消息发布平台发送短信
		int date = getReqDto().getSysDate();
		int time = getReqDto().getSysTime();
		int tranceNo = (int) Calendar.getInstance().getTimeInMillis();
		DataTransObject dto = new DataTransObject();
		dto.setSourceType("CIP");
		dto.setSysDate(date);
		dto.setSysTime(time);
		dto.setSysTraceno(tranceNo);
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "ceba_txbrno");
			txTel = jedis.get(COMMON_PREFIX + "ceba_txtel");
		}
		
		ESB_REQ_50022000401 req_50022000401 = new ESB_REQ_50022000401(null, date, time, tranceNo);
		// 请求报文头赋值
		req_50022000401.setReqSysHead(new EsbReqHeaderBuilder(req_50022000401.getReqSysHead(), dto).setUserId(txTel)
				.setBranchId(txBrno).build());
		// ESB报文体赋值
		ESB_REQ_50022000401.REQ_BODY reqBody = req_50022000401.getReqBody();
		reqBody.setSendtype("100");// 只发短信
		reqBody.setMid("SMS_FJR003");
		reqBody.setPacket("620001");// 720001-动账类交易 620001-营销类交易
		reqBody.setSrvid("6201");
		reqBody.setChanno(CIP.SYSTEM_ID); // TODO 上线时需在消息发布平台增加系统编号
		reqBody.setTranscode("qdyw");
		reqBody.setImmediaflag("1");// 1-实时，0-预约
		reqBody.setSendtime("" + date + time);
		reqBody.setBranchno("00000");// 00000-阜新总行
		reqBody.setRegNo(mobNo);
		logger.info("开始发送短信...，手机号【" + mobNo + "】");
		reqBody.setData("|||||||||||||"+message + "《CAP》|");

		// 调用ESB服务
		forwardToESBService.sendToESB(req_50022000401, reqBody, ESB_REP_50022000401.class);
	}

	/**
	 * @Title: outerCharge @Description: 跨行转账 @param @param myLog @param @param
	 *         txAmt @param @return @param @throws SysTradeExecuteException
	 *         设定文件 @return ESB_REP_30041000406 返回类型 @throws
	 */
	private ESB_REP_30041000406 outerCharge(MyLog myLog, BigDecimal txAmt) throws SysTradeExecuteException {
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		//付款人账号
		String payerAcctNo = null;
		//付款人名称
		String payerName = null;
		//付款人地址
		String payerAddr = null;
		//收款人账号
		String payeeAcctNo = null;
		//收款人名称
		String payeeName = null;
		//收款人地址
		String payeeAddr = null;
		//接收行行号
		String rcvBankNo = null;
		//接收行行名
		String rcvBankName = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "ceba_txbrno");
			txTel = jedis.get(COMMON_PREFIX + "ceba_txtel");
			payerAcctNo = jedis.get(COMMON_PREFIX + "settle_payer_acct_no");
			payerName = jedis.get(COMMON_PREFIX + "settle_payer_name");
			payerAddr = jedis.get(COMMON_PREFIX + "settle_payer_addr");
			payeeAcctNo = jedis.get(COMMON_PREFIX + "settle_payee_acct_no");
			payeeName = jedis.get(COMMON_PREFIX + "settle_payee_name");
			payeeAddr = jedis.get(COMMON_PREFIX + "settle_payee_addr");
			rcvBankNo = jedis.get(COMMON_PREFIX + "settle_rcv_bank_no");
			rcvBankName = jedis.get(COMMON_PREFIX + "settle_rcv_bank_name");
		}

		ESB_REQ_30041000406 esbReq_30041000406 = new ESB_REQ_30041000406(myLog, publicService.getSysDate("CIP"),
				publicService.getSysTime(), publicService.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30041000406.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30041000406.setReqSysHead(reqSysHead);

		ESB_REQ_30041000406.REQ_BODY reqBody_30041000406 = esbReq_30041000406.getReqBody();
		// 来源系统
		reqBody_30041000406.setSourceSys("CAP");
		// 付款人账号
		reqBody_30041000406.setPayerAcctNo(payerAcctNo);
		// 付款人名称
		reqBody_30041000406.setPayerName(payerName);
		// 付款人地址
		reqBody_30041000406.setPayerAddr(payerAddr);
		// 金额
		reqBody_30041000406.setAmt(txAmt.toString());
		// 收款人账号
		reqBody_30041000406.setPayeeAcctNo(payeeAcctNo);
		// 收款人名称
		reqBody_30041000406.setPayeeName(payeeName);
		// 收款人地址
		reqBody_30041000406.setPayeeAddr(payeeAddr);
		// 到账方式1-实时2-普通延时3-次日延时
		reqBody_30041000406.setTranMethod("1");
		// 系统类型
		reqBody_30041000406.setSysTpT("HVPS");
		// 接收行行号
		reqBody_30041000406.setRcvBankNo(rcvBankNo);
		// 接收行行名
		reqBody_30041000406.setRcvBankName(rcvBankName);
		// 附言
		reqBody_30041000406.setPostScript("");
		// 支取方式
		reqBody_30041000406.setWithDrawalType("W");
		// 付款账户类型0-内部账 1-对私 2-对公
		reqBody_30041000406.setActualPayerAcctTp("0");
		// 费用金额
		reqBody_30041000406.setFeeAmt("0");
		// 费用类型
		reqBody_30041000406.setFeeType("0");

		ESB_REP_30041000406 esbRep_30041000406 = forwardToESBService.sendToESB(esbReq_30041000406, reqBody_30041000406,
				ESB_REP_30041000406.class);
		return esbRep_30041000406;
	}

	@Bean(name = "cebaSettleJobDetail")
	public MethodInvokingJobDetailFactoryBean cebaSettleJobDetail(CebaSettleTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "cebaSettleJobTrigger")
	public CronTriggerFactoryBean cebaSettleJobTrigger(
			@Qualifier("cebaSettleJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		String exp = null;
		try (Jedis jedis = myJedis.connect()) {
			exp = jedis.get(QuartzJobConfigration.CEBA_CRON + JOBNAME);
		}
		if (exp == null) {
			exp = "0 0,5 7,14 ? * *";
		}
		logger.info("任务[" + JOBNAME + "]启动[" + exp + "]");
		tigger.setCronExpression(exp);
		tigger.setName(JOBNAME);
		return tigger;
	}

	@Bean(name = "settleScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("cebaSettleJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}
}
