package com.fxbank.cap.ykwm.trade.esb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.model.ses.ESB_REP_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011000101.ServDetail;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.esb.REP_30012002002;
import com.fxbank.cap.ykwm.dto.esb.REP_30012002002.Code;
import com.fxbank.cap.ykwm.dto.esb.REQ_30012002002;
import com.fxbank.cap.ykwm.dto.esb.REQ_30012002002.INVOICE;
import com.fxbank.cap.ykwm.exception.YkwmTradeExecuteException;
import com.fxbank.cap.ykwm.model.REP_Payment;
import com.fxbank.cap.ykwm.model.REQ_Payment;
import com.fxbank.cap.ykwm.model.YkwmTraceLogModel;
import com.fxbank.cap.ykwm.service.IForwardToYkwmService;
import com.fxbank.cap.ykwm.service.IPaymentService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.constant.CIP;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.model.ModelBase;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.pub.service.IPublicService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("REQ_30012002002")
public class PY_Ment extends BaseTradeT1 implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(PY_Ment.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToYkwmService forwardToYkwmService;

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Reference(version = "1.0.0")
	IPaymentService iPaymentService; // cap

	private static final String COMMON_PREFIX = "ykwm.";

	@Resource
	private MyJedis myJedis;
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		super.hostTimeoutException = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10002);
		super.othTimeoutException = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10003);
		super.TRADE_DESC = "入账交易";
		super.othTimeoutQuery = false;
		super.logger = logger;
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		return super.execute(reqDto);
	}

	@Override
	public ModelBase hostCharge(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		REQ_30012002002.REQ_BODY reqBody = reqDto.getReqBody();
		ESB_REQ_30011000101 req_30011000101 = new ESB_REQ_30011000101(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(req_30011000101.getReqSysHead(), reqDto)
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId()).build();
		req_30011000101.setReqSysHead(reqSysHead);

		ESB_REQ_30011000101.REQ_BODY esb_reqBody = req_30011000101.getReqBody();
		esb_reqBody.setBaseAcctNo(reqBody.getAcctNoT());// 卡号
		// 缴费方式 属于 ServDetail List 一部分，得new个对象，然后回传是一个对象
		ESB_REQ_30011000101.ServDetail servDetail = new ESB_REQ_30011000101.ServDetail();
		// servDetail.setChargeMode(reqBody.getPyFeeTpT());
		servDetail.setChargeMode("C");
		servDetail.setFeeType("1");
		servDetail.setFeeAmt("20");
		List<ServDetail> servDeailList = new ArrayList<ServDetail>();
		servDeailList.add(servDetail);

		esb_reqBody.setTranType(reqBody.getPyFeeTpT().equals("1") ? "BH13" : "BH15");
		esb_reqBody.setTranCcy("CNY");
		esb_reqBody.setServDetail(servDeailList);// 缴费方式(对象)
		esb_reqBody.setTranAmt(reqBody.getPyFeeAmtT());// 缴费金额
		esb_reqBody.setPassword(reqBody.getPwdT());
		esb_reqBody.setOthBaseAcctNo("623166001015087122");// 对方账号
		esb_reqBody.setChannelType("BH");// 渠道类型 ESB写死为
		esb_reqBody.setSettlementDate(reqDto.getSysDate().toString());// 渠道日期
		// 捕获异常 对核心记账结果进行判断
		// *****************************

			ESB_REP_30011000101 eSB_REP_30011000101 = forwardToESBService.sendToESB(req_30011000101,
					req_30011000101.getReqBody(), ESB_REP_30011000101.class);
			return eSB_REP_30011000101;
	}

	/** 
	* @Title: hostTimeoutInitLog 
	* @Description: 核心记账超时登记
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void hostTimeoutInitLog(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		REQ_30012002002.REQ_BODY reqBody = reqDto.getReqBody();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		//TODO 登记柜面的输入项
		record.setPyFeeAmtT(reqBody.getPyFeeAmtT());
		
		iPaymentService.hostTimeoutInit(record);
		
	}

	@Override
	public void hostSuccessInitLog(DataTransObject dto, ModelBase model) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		ESB_REP_30011000101 rep = (ESB_REP_30011000101) model;
		REQ_30012002002.REQ_BODY reqBody = reqDto.getReqBody();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		//TODO 登记柜面的输入项和核心记账成功返回的信息
		record.setPyFeeAmtT(reqBody.getPyFeeAmtT());
		record.setCoTransactionno(rep.getRepBody().getReference());
		
		iPaymentService.hostTimeoutInit(record);
		
	}

	@Override
	public ModelBase othCharge(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		REQ_30012002002.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_Payment reqPayment = new REQ_Payment(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());

		reqPayment.setAcctNoT(reqBody.getAcctNoT());

		Map<String, String> map = new HashMap<String, String>();
		map.put("GM", "01");// GM、SJ 问柜面 01\02 热电约定
		map.put("SJ", "02");

		reqPayment.setSource(map.get(reqDto.getReqSysHead().getSourceType()));// 缴费渠道
		// ----------------------------是不是约定的那种 ----------------------------------------
		reqPayment.setBranchNum(reqDto.getReqSysHead().getBranchId());// 网点编号 ??????
		reqPayment.setBatchNum(publicService.getSysDate("CIP").toString());// 批次号 取渠道日期 publicService公共服务

		reqPayment.setCheckNum(reqBody.getCheckNoT());// 流水号 柜面需要处理，将查询的流水送给缴费的接口

		reqPayment.setPyFeeAmtT(reqBody.getPyFeeAmtT());// 缴费金额 加上快递费 不带邮寄费

		// 数据类型 变量名 ： 数组名
		for (INVOICE a : reqBody.getInvoiceList()) {

			REQ_Payment.INVOICE invoice = new REQ_Payment.INVOICE();

			invoice.setBillGetTpT(a.getBillGetTpT());
			invoice.setInvcNaHdT3(a.getInvcNaHdT3());
			invoice.setReimburseAreaT(a.getReimburseAreaT());
			invoice.setNaT1(a.getNaT1());
			invoice.setInvoiceNum(a.getInvoiceNumT());
			invoice.setBankNum(a.getBankNumT());// 开户行行号"
			invoice.setUserAddrT(a.getUserAddrT());

			invoice.setBillGetTpT(a.getBillGetTpT());// 发票获取方式

			reqPayment.getInvoiceList().add(invoice);
		}
		reqPayment.setInvoiceCount(String.valueOf(reqBody.getInvoiceList().size()));

		reqPayment.setCourierCmpnyIdT(reqBody.getCourierCmpnyIdT());// 快递公司
		reqPayment.setMailAddrT(reqBody.getMailAddrT());// 邮寄地址
		reqPayment.setCnttPhnT(reqBody.getCnttPhnT());// 联系电话
		reqPayment.setLnmT3(reqBody.getLnmT3());// 联系人
		reqPayment.setPostNoT5(reqBody.getPostNoT5());// 邮编

		// reqPayment.setInvoiceNum("");// 纳税人识别号

		REP_Payment repPayment = forwardToYkwmService.sendToYkwm(reqPayment, REP_Payment.class);
	return repPayment;
	}

	@Override
	public void queryOth(DataTransObject dto) throws SysTradeExecuteException {
	}

	@Override
	public Boolean othTimeout(SysTradeExecuteException e) throws SysTradeExecuteException {
		return e.getRspCode().equals(SysTradeExecuteException.CIP_E_000009);
	}

	/** 
	* @Title: othSuccess 
	* @Description: 营口热电记账失败，再次发起查询请求判断是否记账成功
	* @param @param e
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public Boolean othSuccess(SysTradeExecuteException e) throws SysTradeExecuteException {
		return false;
	}

	/** 
	* @Title: othErrorUndoHost 
	* @Description: 营口热电记账失败，核心是否冲正 
	* @param @param e
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public Boolean othErrorUndoHost(SysTradeExecuteException e) throws SysTradeExecuteException {
		return true;
	}

	@Override
	public ModelBase undoHostCharge(DataTransObject dto, SysTradeExecuteException e) throws SysTradeExecuteException {
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		MyLog myLog = logPool.get();
		ESB_REQ_30014000101 esbReq_30014000101 = new ESB_REQ_30014000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30014000101.getReqSysHead(), reqDto).build();
		esbReq_30014000101.setReqSysHead(reqSysHead);
		ESB_REQ_30014000101.REQ_BODY reqBody_30014000101 = esbReq_30014000101.getReqBody();

		reqBody_30014000101.setChannelSeqNo(CIP.SYSTEM_ID+reqDto.getSysDate()+String.format("%08d",reqDto.getSysTraceno()));
		
		reqBody_30014000101.setReversalReason(e.getRspMsg());

		ESB_REP_30014000101 esbRep_30014000101 = forwardToESBService.sendToESB(esbReq_30014000101, reqBody_30014000101,
				ESB_REP_30014000101.class);
		return esbRep_30014000101;
	}

	@Override
	public void updateOthTimeout(DataTransObject dto) throws SysTradeExecuteException {
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
    	iPaymentService.othTimeoutUpdate(record);
		
	}

	@Override
	public void updateOthSuccess(DataTransObject dto, ModelBase model) throws SysTradeExecuteException {
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		REP_Payment rep = (REP_Payment)model;
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setTicketNumber(rep.getCode());
    	iPaymentService.othTimeoutUpdate(record);
		
	}

	@Override
	public void updateHostUndoSuccess(DataTransObject dto, ModelBase model) throws SysTradeExecuteException {
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		ESB_REP_30014000101 res = (ESB_REP_30014000101) model;
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		//TODO 更新核心冲正成功返回的信息
		record.setCoTransactionno(res.getRepSysHead().getReference());
    	iPaymentService.othTimeoutUpdate(record);
		
	}

	@Override
	public void updateOthError(DataTransObject dto, SysTradeExecuteException e) throws SysTradeExecuteException {
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		//TODO 更新营口热电记账失败信息
		
    	iPaymentService.othTimeoutUpdate(record);
		
	}

	@Override
	public void updateHostUndoTimeout(DataTransObject dto) throws SysTradeExecuteException {
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
    	iPaymentService.othTimeoutUpdate(record);
		
	}

	@Override
	public void updateOthTimeoutSucc(DataTransObject dto) throws SysTradeExecuteException {
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
    	iPaymentService.othTimeoutUpdate(record);
		
	}

	@Override
	public DataTransObject backMsg(DataTransObject dto,ModelBase model) throws SysTradeExecuteException {
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		REP_30012002002 rep = new REP_30012002002();
		REP_Payment repPayment = (REP_Payment)model;
		rep.getRepBody().setChannelDate(reqDto.getSysDate().toString());
		rep.getRepBody().setChannelSeqNo(reqDto.getSysTraceno().toString());
		List<Code> list = new ArrayList<Code>();
		for(String temp:repPayment.getCode().split("^")) {
			Code c = new Code();
			c.setCode(temp);
			list.add(c);
		}
		rep.getRepBody().setCodeArray(list);
		return rep;
	}

	@Override
	public void updateHostUndoError(DataTransObject dto, SysTradeExecuteException e) throws SysTradeExecuteException {
		REQ_30012002002 reqDto = (REQ_30012002002) dto;
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		//TODO 更新核心冲正失败的错误信息
    	iPaymentService.othTimeoutUpdate(record);
		
	}

	@Override
	public SysTradeExecuteException othErrorException(SysTradeExecuteException e) {
		return e;
	}

	@Override
	public SysTradeExecuteException hostUndoSuccessException(SysTradeExecuteException e) {
		SysTradeExecuteException e1 = new SysTradeExecuteException(e.getRspCode(), e.getRspMsg()+"(退款成功)");
		return e1;
	}

	@Override
	public SysTradeExecuteException hostUndoTimeoutException(SysTradeExecuteException e) {
		SysTradeExecuteException e1 = new SysTradeExecuteException(e.getRspCode(), e.getRspMsg()+"(退款超时，请确认账务状态)");
		return e1;
	}

	@Override
	public SysTradeExecuteException hostUndoErrorException(SysTradeExecuteException e, SysTradeExecuteException e1) {
		SysTradeExecuteException e2 = new SysTradeExecuteException(e.getRspCode(), e.getRspMsg()+"("+e1.getRspMsg()+",退款失败)");
		return e2;
	}

	
}