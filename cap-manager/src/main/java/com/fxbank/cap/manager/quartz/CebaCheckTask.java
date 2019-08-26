package com.fxbank.cap.manager.quartz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.server.error.FtpException;
import com.fxbank.cap.ceba.exception.CebaTradeExecuteException;
import com.fxbank.cap.ceba.model.CebaChargeLogModel;
import com.fxbank.cap.ceba.model.CebaCheckLogInitModel;
import com.fxbank.cap.ceba.model.CebaRefundeLogModel;
import com.fxbank.cap.ceba.model.CheckErrorModel;
import com.fxbank.cap.ceba.model.HostCheckLogInitModel;
import com.fxbank.cap.ceba.service.ICebaChargeLogService;
import com.fxbank.cap.ceba.service.ICebaCheckLogService;
import com.fxbank.cap.ceba.service.ICebaRefundeLogService;
import com.fxbank.cap.ceba.service.ICebaSettleLogService;
import com.fxbank.cap.ceba.service.ICheckErrorService;
import com.fxbank.cap.ceba.service.IHostCheckLogService;
import com.fxbank.cap.esb.model.ses.ESB_REP_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REP_50015000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_50015000101;
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
import redis.clients.jedis.Jedis;

/**
 * @ClassName: CebaCheckTask
 * @Description: 对账定时任务
 * @作者 杜振铎
 * @date 2019年8月8日 下午4:47:39
 * 
 */
@Configuration
@Component
@EnableScheduling
public class CebaCheckTask {
	private static Logger logger = LoggerFactory.getLogger(CebaCheckTask.class);

	private static final String JOBNAME = "CebaCheck";

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private ICebaChargeLogService cebaChargeLogService;

	@Reference(version = "1.0.0")
	private ICebaRefundeLogService cebaRefundeLogService;

	@Reference(version = "1.0.0")
	private IHostCheckLogService hostCheckLogService;

	@Reference(version = "1.0.0")
	private ICebaCheckLogService cebaCheckLogService;

	@Reference(version = "1.0.0")
	private ICheckErrorService checkErrorService;

	@Reference(version = "1.0.0")
	private ICebaSettleLogService cebaSettleLogService;

	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "ceba.";

	public void exec() throws Exception {
		MyLog myLog = new MyLog();
		myLog.info(logger, "开始进行对账处理");
		String mobNos;
		try (Jedis jedis = myJedis.connect()) {
			mobNos = jedis.get(COMMON_PREFIX + "checkNoticeMobNos");
		}
		// 渠道日期前一天对账
		Integer date = getPreDateByDate(getReqDto().getSysDate());
		myLog.info(logger, "核心与外围对账开始");
		checkErrorService.delete(date.toString());
		// 取核心文件 核心文件入库
		List<HostCheckLogInitModel> checkLogList = getHostCheckLogList(myLog, date.toString());
		// 核心与外围对账
		hostCheckChannelTraceLog(myLog, date.toString(), checkLogList);
		myLog.info(logger, "核心与外围对账结束");
		myLog.info(logger, "外围与核心对账开始");
		// 获取未核心对账的缴费记录
		List<CebaChargeLogModel> traceList = cebaChargeLogService.getCheckTrace(myLog, date.toString(), "1", "1");
		// 外围与核心对账
		channelCheckHostTraceLog(myLog, date.toString(), traceList);
		myLog.info(logger, "外围与核心对账结束");
		myLog.info(logger, "光大银行与外围对账开始");
		// 取对账文件数据
		List<CebaCheckLogInitModel> checkLogList1 = cebaCheckLogService.getCebaCheckLog(myLog, getReqDto().getSysTime(),
				getReqDto().getSysTraceno(), getReqDto().getSysDate());
		// 光大银行与外围对账
		cebaCheckChannelTraceLog(myLog, date.toString(), checkLogList1);
		myLog.info(logger, "光大银行与外围对账结束");
		myLog.info(logger, "外围与光大银行对账开始");
		// 获取光大银行未对账、核心已对账的缴费记录
		List<CebaChargeLogModel> traceList1 = cebaChargeLogService.getCheckTrace(myLog, date.toString(), "2", "1");
		// 外围与光大银行对账
		channelCheckCebaTraceLog(myLog, date.toString(), traceList1);
		myLog.info(logger, "外围与光大银行对账结束");
		// 光大银行对账标志，1-未对账，2-已对账，3-光大银行多，4-渠道多
		// 核心对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
		// 核心对账标志不为1并且光大银行对账标志不为1的缴费流水数
		String totalCheckNum = cebaChargeLogService.getTotalCheckNum(date.toString());
		// 核心对账标志为4的缴费流水数
		String hostCheckNum = cebaChargeLogService.getHostCheckNum(date.toString(), "4");
		// 光大银行对账标志为4的缴费流水数
		String cebaCheckNum = cebaChargeLogService.getCebaCheckNum(date.toString(), "4");
		//核心对账标志不为2并且光大银行对账标志不为2的缴费流水数
		String checkSuccNum = cebaChargeLogService.getCheckSuccNum(date.toString());
		//核心对账标志不为2并且光大银行对账标志不为2的缴费流水总金额
		BigDecimal checkSuccAmt = new BigDecimal(cebaChargeLogService.getCheckSuccAmt(date.toString()));
		//对账成功标志，以光大银行对账文件为准
		Boolean checkFlag = false;
		String checkMsg = "光大云缴费【" + date + "】对账统计：共【" + totalCheckNum + "】笔，" + "其中已对账【" + checkSuccNum + "】笔，比核心多出【"
				+ hostCheckNum + "】笔，" + "比光大银行多出【" + cebaCheckNum + "】笔，";
		if(checkLogList1.size() == Integer.parseInt(checkSuccNum)) {
			checkFlag = true;
			checkMsg += "对账成功";
		}else {
			checkMsg += "对账失败";
		}
		myLog.info(logger, checkMsg);
		try {
			sendMessage(mobNos, checkMsg);
		}catch(SysTradeExecuteException e) {
			myLog.error(logger, "缴费清算短信通知发送失败",e);
		}
		try {
			//如果光大银行对账文件成功笔数和对账成功笔数相同并且清算日志没有该对账日期流水，登记清算流水表
			if (checkFlag && null == cebaSettleLogService.querySettleLogByPk(myLog, date)) {
				cebaSettleLogService.initSettleLog(myLog, date, checkSuccAmt);
				myLog.info(logger, "登记清算流水表成功，渠道日期" + date);
			}
		} catch (SysTradeExecuteException e) {
			myLog.error(logger, "登记清算流水表失败，渠道日期" + date, e);
		}
		myLog.info(logger, "对账处理完成");
	}

	/** 
	* @Title: hostRefunde 
	* @Description: 对账核心退款
	* @param @param myLog
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000101    返回类型 
	* @throws 
	*/
	public ESB_REP_30011000101 hostRefunde(MyLog myLog, CebaChargeLogModel model) throws SysTradeExecuteException {
		ESB_REQ_30011000101 esbReq_30011000101 = new ESB_REQ_30011000101(myLog, getReqDto().getSysDate(),
				getReqDto().getSysTime(), getReqDto().getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000101.getReqSysHead(), getReqDto())
				.setSourceType("MB").build();
		esbReq_30011000101.setReqSysHead(reqSysHead);
		// 退款账号
		String baseAcctNo = null;
		try (Jedis jedis = myJedis.connect()) {
			baseAcctNo = jedis.get(COMMON_PREFIX + "refunde_acct_no");
		}
		ESB_REQ_30011000101.REQ_BODY reqBody_30011000101 = esbReq_30011000101.getReqBody();
		// 账号/卡号
		reqBody_30011000101.setBaseAcctNo(baseAcctNo);
		reqBody_30011000101.setOthBaseAcctNo(model.getPayAccount());
		// 账户名称
		reqBody_30011000101.setAcctName(model.getCustomerName());
		// 交易类型
		reqBody_30011000101.setTranType("GD01");
		// 交易币种
		reqBody_30011000101.setTranCcy("CNY");
		// 支取方式 支取时必输S凭印鉴支取P凭密码支取W无密码无印鉴支取B凭印鉴和密码支取O凭证件支取
		reqBody_30011000101.setWithdrawalType("W");
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

	/** 
	* @Title: channelCheckHostTraceLog 
	* @Description: 外围与核心对账 
	* @param @param myLog
	* @param @param date
	* @param @param traceList
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void channelCheckHostTraceLog(MyLog myLog, String date, List<CebaChargeLogModel> traceList)
			throws SysTradeExecuteException {
		for (CebaChargeLogModel model : traceList) {
			// 核心对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
			model.setHostCheckState("4");
			cebaChargeLogService.updateCheck(model);
			// 核心记账状态，0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-处理超时
			if (model.getHostState().equals("0")||model.getHostState().equals("2")) {
				CheckErrorModel ceModel = new CheckErrorModel(myLog, model.getSysDate(), model.getSysTime(),
						model.getSysTraceno());
				ceModel.setPreHostState(model.getHostState());
				ceModel.setRePayState("");
				// 对账问题数据表 核心对账标志，1-记账状态不符，2-核心多，3-渠道多
				ceModel.setCebaCheckFlag("3");
				ceModel.setTxAmt(model.getPayAmount());
				ceModel.setRemark("渠道多出记录，渠道日期【" + model.getSysDate() + "】，渠道流水【"
						+ model.getSysTraceno() + "】，渠道记账状态【" + model.getHostState() + "】");
				checkErrorService.insert(ceModel);
				myLog.error(logger, "光大云缴费【" + date + "】与核心对账失败: 渠道多出记录，渠道流水号【" + model.getSysTraceno() + "】，渠道核心记账状态【"
						+ model.getHostState() + "】");
			} else {
				myLog.info(logger, "渠道比核心多数据，渠道日期【" + model.getSysDate() + "】，渠道流水【" + model.getSysTraceno()
						+ "】，渠道核心记账状态【" + model.getHostState() + "】");
			}
		}
	}

	/**
	 * @Title: hostCheckChannelTraceLog 
	 * @Description: 核心与外围对账 
	 * @param @param myLog 
	 * @param @param date 
	 * @param @param dayCheckLogList 
	 * @param @throws SysTradeExecuteException 设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	private void hostCheckChannelTraceLog(MyLog myLog, String date, List<HostCheckLogInitModel> dayCheckLogList)
			throws SysTradeExecuteException {
		for (HostCheckLogInitModel model : dayCheckLogList) {
			// 根据渠道日期和渠道流水取销账日志流水数据
			CebaChargeLogModel cebaChargeLogModel = cebaChargeLogService.queryLogByPk(myLog, model.getSysDate(),
					model.getSysTraceno());
			// 若渠道缺少数据则报错
			if (cebaChargeLogModel == null) {
				CheckErrorModel ceModel = new CheckErrorModel(myLog, model.getSysDate(), model.getSysTime(),
						model.getSysTraceno());
				// 对账问题数据表 核心对账标志，1-记账状态不符，2-核心多，3-渠道多
				ceModel.setHostCheckFlag("2");
				// 核心记账状态，0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-处理超时
				ceModel.setPreHostState("");
				ceModel.setReHostState("0");
				ceModel.setTxAmt(model.getTxAmt());
				ceModel.setHostDate(model.getHostDate());
				ceModel.setHostTraceno(model.getHostTraceno());
				ceModel.setRemark("渠道补充数据，渠道日期【" + model.getSysDate() + "】，渠道流水【" + model.getSysTraceno() + "】");
				checkErrorService.insert(ceModel);
				myLog.error(logger, "光大云缴费【" + date + "】与核心对账失败,渠道数据丢失: 渠道流水号【" + model.getSysTraceno() + "】渠道日期为【"
						+ model.getSysDate() + "】");
				CebaTradeExecuteException e = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10011);
				throw e;
			} else {
				String hostState = cebaChargeLogModel.getHostState(); // 渠道记录的核心记账状态
				if (hostState.equals("0")) {
					// 核心对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
					cebaChargeLogModel.setHostCheckState("2");
					cebaChargeLogService.updateCheck(cebaChargeLogModel);
					// 核心记账状态，0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-处理超时
				} else if (hostState.equals("2") || hostState.equals("3") || hostState.equals("4")) {
					// 核心记账成功，渠道状态为超时、冲正超时、冲正失败，修改渠道状态为成功
					CebaChargeLogModel record = new CebaChargeLogModel(myLog, model.getSysDate(), model.getSysTime(),
							model.getSysTraceno());
					record.setHostState("0");
					record.setHostCheckState("2");
					record.setHostDate(model.getHostDate());
					record.setHostTraceNo(model.getHostTraceno());
					cebaChargeLogService.updateCheck(record);
					myLog.info(logger, "渠道调整核心记账状态，渠道日期【" + cebaChargeLogModel.getSysDate() + "】，渠道流水【"
							+ model.getSysTraceno() + "】，调整前状态【" + hostState + "】，调整后状态【0】");
				} else {
					myLog.error(logger, "光大云缴费【" + date + "】与核心对账失败: 渠道流水号【" + cebaChargeLogModel.getSysTraceno()
							+ "】记录核心状态为【" + cebaChargeLogModel.getHostState() + "】,与核心不符");
					CheckErrorModel ceModel = new CheckErrorModel(myLog, model.getSysDate(), model.getSysTime(),
							model.getSysTraceno());
					ceModel.setPreHostState(hostState);
					ceModel.setReHostState("0");
					// 对账问题数据表 核心对账标志，1-记账状态不符，2-核心多，3-渠道多
					ceModel.setHostCheckFlag("1");
					ceModel.setHostDate(model.getHostDate());
					ceModel.setHostTraceno(model.getHostTraceno());
					ceModel.setTxAmt(cebaChargeLogModel.getPayAmount());
					ceModel.setRemark("渠道和核心记账状态不符，渠道日期【" + cebaChargeLogModel.getSysDate() + "】，渠道流水【"
							+ cebaChargeLogModel.getSysTraceno() + "】，渠道记账状态【" + hostState + "】，核心记账状态【0】");
					checkErrorService.insert(ceModel);
				}
			}
		}
	}

	/** 
	* @Title: channelCheckCebaTraceLog 
	* @Description: 外围与光大银行对账 
	* @param @param myLog
	* @param @param date
	* @param @param traceList
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void channelCheckCebaTraceLog(MyLog myLog, String date, List<CebaChargeLogModel> traceList)
			throws SysTradeExecuteException {
		for (CebaChargeLogModel model : traceList) {
			// 光大银行对账标志，1-未对账，2-已对账，3-光大银行多，4-渠道多
			model.setCebaCheckState("4");
			cebaChargeLogService.updateCheck(model);
			// 光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败
			if (model.getPayState().equals("2")) {
				myLog.error(logger, "光大云缴费【" + date + "】与光大银行对账失败: 渠道多出记录，渠道流水号【" + model.getSysTraceno() + "】，光大银行记账状态【"
						+ model.getPayState() + "】");
				// 当实时交易成功，但是对账文件里没有，需要通过对账给客户退款
				ESB_REP_30011000101 esbRep30011000101 = null;
				CebaRefundeLogModel refundeLogModel = new CebaRefundeLogModel(myLog, model.getSysDate(),
						model.getSysTime(), model.getSysTraceno());
				// 来源类型，0对账退款，1退款文件退款
				refundeLogModel.setFlag("0");
				try {
					esbRep30011000101 = hostRefunde(myLog, model);
				} catch (SysTradeExecuteException e) {
					if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000004)
							|| e.getRspCode().equals("ESB_E_000052")) {
						// 退款超时
						// 退款状态，0未退款,1已退款,2退款失败,3退款超时,4销账流水日志未查到
						refundeLogModel.setStatus("3");
						cebaRefundeLogService.initRefundeLog(myLog, refundeLogModel);
						myLog.error(logger, "对账退款超时,渠道日期" + model.getSysDate() + "渠道流水号" + model.getSysTraceno(), e);
					} else {
						// 退款失败
						// 退款状态，0未退款,1已退款,2退款失败,3退款超时,4销账流水日志未查到
						refundeLogModel.setStatus("2");
						cebaRefundeLogService.initRefundeLog(myLog, refundeLogModel);
						myLog.error(logger, "对账退款失败,渠道日期" + model.getSysDate() + "渠道流水号" + model.getSysTraceno(), e);
					}
					continue;
				}
				try {
					// 退款状态，0未退款,1已退款,2退款失败,3退款超时,4销账流水日志未查到
					refundeLogModel.setStatus("1");
					refundeLogModel.setHostCode(esbRep30011000101.getRepSysHead().getRet().get(0).getRetCode());
					refundeLogModel.setHostMsg(esbRep30011000101.getRepSysHead().getRet().get(0).getRetMsg());
					cebaRefundeLogService.initRefundeLog(myLog, refundeLogModel);
				} catch (SysTradeExecuteException e) {
					myLog.error(logger, "登记退款流水日志失败", e);
				}
				myLog.info(logger, "对账退款成功,渠道日期" + model.getSysDate() + "渠道流水号" + model.getSysTraceno());
			} else {
				myLog.info(logger, "渠道比光大银行多数据，渠道日期【" + model.getSysDate() + "】，渠道流水【" + model.getSysTraceno()
						+ "】，渠道光大银行记账状态【" + model.getPayState() + "】");
			}
		}
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

	/** 
	* @Title: cebaCheckChannelTraceLog 
	* @Description: 光大银行与外围对账
	* @param @param myLog
	* @param @param date
	* @param @param dayCheckLogList
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void cebaCheckChannelTraceLog(MyLog myLog, String date, List<CebaCheckLogInitModel> dayCheckLogList)
			throws SysTradeExecuteException {
		for (CebaCheckLogInitModel model : dayCheckLogList) {
			// 根据光大银行对账日期取渠道数据
			CebaChargeLogModel cebaChargeLogModel = cebaChargeLogService.queryLogByPk(myLog, model.getSysDate(),
					model.getSysTraceno());
			// 若渠道缺少数据则报错
			if (cebaChargeLogModel == null) {
				CheckErrorModel ceModel = new CheckErrorModel(myLog, model.getSysDate(), model.getSysTime(),
						model.getSysTraceno());
				ceModel.setBankBillNo(model.getBankBillNo());
				// 对账问题数据表 光大银行对账标志，1-记账状态不符，2-光大银行多，3-渠道多
				ceModel.setCebaCheckFlag("2");
				ceModel.setPrePayState("");
				ceModel.setRePayState("2");
				ceModel.setTxAmt(model.getTxAmt());
				ceModel.setBankBillNo(model.getBankBillNo());
				ceModel.setRemark("渠道补充数据，渠道日期【" + model.getSysDate() + "】，渠道流水【" + model.getSysTraceno() + "】");
				checkErrorService.insert(ceModel);
				myLog.error(logger, "光大云缴费【" + date + "】与光大银行对账失败,渠道数据丢失: 渠道流水号【" + model.getSysTraceno() + "】渠道日期为【"
						+ model.getSysDate() + "】");
				CebaTradeExecuteException e = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10011);
				throw e;
			} else {
				String payState = cebaChargeLogModel.getPayState(); // 渠道记录的光大银行记账状态
				// 光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败
				if (payState.equals("2")) {
					// 光大银行与渠道状态一致
					cebaChargeLogModel.setCebaCheckState("2");
					cebaChargeLogService.updateCheck(cebaChargeLogModel);
				} else if (payState.equals("1")) {
					// 光大银行记账成功，渠道状态为超时，修改渠道状态为成功
					cebaChargeLogModel.setPayState("2");
					cebaChargeLogModel.setCebaCheckState("2");
					cebaChargeLogService.updateCheck(cebaChargeLogModel);
					myLog.info(logger, "渠道调整光大银行记账状态，渠道日期【" + cebaChargeLogModel.getSysDate() + "】，渠道流水【"
							+ model.getSysTraceno() + "】，调整前状态【" + payState + "】，调整后状态【2】");
				} else {
					// 光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败（渠道光大银行记账状态0和3）
					myLog.error(logger, "光大云缴费【" + date + "】与光大银行对账失败: 渠道流水号【" + cebaChargeLogModel.getSysTraceno()
							+ "】记录光大记账状态为【" + cebaChargeLogModel.getPayState() + "】,与光大银行不符");
					CheckErrorModel ceModel = new CheckErrorModel(myLog, model.getSysDate(), model.getSysTime(),
							model.getSysTraceno());
					ceModel.setPrePayState(payState);
					ceModel.setRePayState("2");
					// 对账问题数据表 光大银行对账标志，1-记账状态不符，2-光大银行多，3-渠道多
					ceModel.setCebaCheckFlag("1");
					ceModel.setTxAmt(cebaChargeLogModel.getPayAmount());
					ceModel.setBankBillNo(cebaChargeLogModel.getBankBillNo());
					ceModel.setRemark("渠道与光大银行记账状态不符，渠道日期【" + cebaChargeLogModel.getSysDate() + "】，渠道流水【"
							+ cebaChargeLogModel.getSysTraceno() + "】，渠道记账状态【" + payState + "】，光大银行记账状态【2】");
					checkErrorService.insert(ceModel);
				}
			}
		}
	}

	/** 
	* @Title: getEsbCheckFile 
	* @Description: 下载核心对账文件 
	* @param @param myLog
	* @param @param date
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	private String getEsbCheckFile(MyLog myLog, String date) throws SysTradeExecuteException {
		ESB_REQ_50015000101 esbReq_50015000101 = new ESB_REQ_50015000101(myLog, getReqDto().getSysDate(),
				getReqDto().getSysTime(), getReqDto().getSysTraceno());
		String txBrno = null, txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "txbrno");
			txTel = jedis.get(COMMON_PREFIX + "txtel");
		}
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_50015000101.getReqSysHead(), getReqDto())
				.setBranchId(txBrno).setUserId(txTel).setSourceType("MB").build();
		esbReq_50015000101.setReqSysHead(reqSysHead);
		ESB_REQ_50015000101.REQ_BODY esbReqBody_50015000101 = esbReq_50015000101.getReqBody();
		esbReqBody_50015000101.setChannelType("GD");
		esbReqBody_50015000101.setStartDate(date);
		esbReqBody_50015000101.setEndDate(date);
		esbReqBody_50015000101.setDirection("");

		ESB_REP_50015000101 esbRep_50015000101 = forwardToESBService.sendToESB(esbReq_50015000101,
				esbReqBody_50015000101, ESB_REP_50015000101.class);
		String remoteFile = esbRep_50015000101.getRepSysHead().getFilePath();
		String fileName = esbRep_50015000101.getRepBody().getFileName();
		String localPath = "";
		try (Jedis jedis = myJedis.connect()) {
			localPath = jedis.get(COMMON_PREFIX + "txt_path");
		}
		try {
			loadTraceLogFile(myLog, remoteFile, localPath + File.separator + fileName);
		} catch (Exception e) {
			myLog.error(logger, "下载核心对账文件失败");
		}
		return localPath + File.separator + fileName;
	}

	/**
	 * @Title: loadFile 
	 * @Description: 从文件传输平台下载文件 
	 * @param @param myLog 
	 * @param @param remoteFile 文件传输平台路径+文件名 
	 * @param @param localFile 文件本地路径+文件名 
	 * @param @throws PafTradeExecuteException 设定文件 
	 * @return void 返回类型
	 * @throws
	 */
	private void loadTraceLogFile(MyLog myLog, String remoteFile, String localFile) throws Exception {
		FtpClientConfigSet configSet = new FtpClientConfigSet();
		FtpGet ftpGet = null;
		try {
			ftpGet = new FtpGet(remoteFile, localFile, configSet);
			boolean result = ftpGet.doGetFile();
			if (!result) {
				myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！");
			}
			myLog.info(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载成功！");
		} catch (Exception e) {
			myLog.error(logger, "文件[" + remoteFile + "]TO[" + localFile + "下载失败！", e);
			throw e;
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

	/** 
	* @Title: getHostCheckLogList 
	* @Description: 查询核心对账流水
	* @param @param myLog
	* @param @param date
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return List<HostCheckLogInitModel>    返回类型 
	* @throws 
	*/
	private List<HostCheckLogInitModel> getHostCheckLogList(MyLog myLog, String date) throws SysTradeExecuteException {
		String localFile = getEsbCheckFile(myLog, date);
		// 对账文件入库
		initHostCheckLog(localFile, myLog, date);
		// 取对账文件数据
		List<HostCheckLogInitModel> dayCheckLogList = hostCheckLogService.getHostCheckLog(myLog,getReqDto().getSysDate());
		return dayCheckLogList;
	}

	/** 
	* @Title: initHostCheckLog 
	* @Description: 核心对账信息登记
	* @param @param localFile
	* @param @param myLog
	* @param @param platDate
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void initHostCheckLog(String localFile, MyLog myLog, String platDate) throws SysTradeExecuteException {
		BufferedReader br = null;
		myLog.info(logger, "核心对账信息入库开始");
		int i = 0;
		String hostChkTraceno = "";
		String platChkTraceno = "";
		try {
			hostCheckLogService.delete(platDate);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(localFile)), "UTF-8"));
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				lineTxt += "*|";
				String[] array = lineTxt.split("\\|");
				if (array.length < 11) {
					myLog.info(logger, "文件【" + localFile + "】内容缺失");
					continue;
				}

				HostCheckLogInitModel model = new HostCheckLogInitModel(myLog, Integer.parseInt(platDate), 0,
						Integer.parseInt(array[1].substring(14)));
				// 清算日期
				model.setSettleDate(Integer.parseInt(array[2]));
				// 交易类型
				model.setTranType(array[9]);
				// 清算机构
				model.setSettleBranch(array[3]);
				// 核心交易日期
				model.setHostDate(Integer.parseInt(array[4]));
				// 核心流水号
				model.setHostTraceno(array[5]);
				// 交易币种
				model.setCcy(array[10]); 
				BigDecimal bg = new BigDecimal(array[11] == null ? "0" : array[11]);
				// 交易金额
				model.setTxAmt(bg); 
				// 交易账户
				model.setAccountno(array[12]);
				model.setReversal(array[14]); // 冲正标志
				model.setTxStatus(array[20]); // 交易状态
				// 记录核心流水
				hostChkTraceno = model.getHostTraceno();
				// 记录渠道流水
				platChkTraceno = model.getSysTraceno() + "";
				// 核心记账流水插入对账临时表
				hostCheckLogService.hostCheckLogInit(model);
				i++;
				myLog.info(logger, "核心记账流水入库,核心流水号：【" + model.getHostTraceno() + "】交易类型【" + model.getTranType()
						+ "】，渠道日期【" + platDate + "】");
			}
			myLog.info(logger, "记账日期【" + platDate + "】核心记账笔数【" + i + "】");

		} catch (Exception e) {
			myLog.error(logger, "对账文件流水入库失败:存在重复记账记录,核心流水号【" + hostChkTraceno + "】渠道流水号【" + platChkTraceno + "】 ");
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
			myLog.info(logger, "核心对账信息入库结束");
		}
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

	@Bean(name = "cebaCheckJobDetail")
	public MethodInvokingJobDetailFactoryBean cebaCheckJobDetail(CebaCheckTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "cebaCheckJobTrigger")
	public CronTriggerFactoryBean cebaCheckJobTrigger(
			@Qualifier("cebaCheckJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		String exp = null;
		try (Jedis jedis = myJedis.connect()) {
			exp = jedis.get(QuartzJobConfigration.CEBA_CRON + JOBNAME);
		}
		if (exp == null) {
			exp = "0 0 4 ? * *";
		}
		logger.info("任务[" + JOBNAME + "]启动[" + exp + "]");
		tigger.setCronExpression(exp);
		tigger.setName(JOBNAME);
		return tigger;
	}

	@Bean(name = "cebaCheckScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("cebaCheckJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}
}
