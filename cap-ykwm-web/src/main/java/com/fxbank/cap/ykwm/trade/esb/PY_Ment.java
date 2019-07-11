package com.fxbank.cap.ykwm.trade.esb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.model.ses.ESB_REP_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.esb.REP_30061001201;
import com.fxbank.cap.ykwm.dto.esb.REP_30061001201.TicketCode;
import com.fxbank.cap.ykwm.dto.esb.REQ_30061001201;
import com.fxbank.cap.ykwm.exception.YkwmTradeExecuteException;
import com.fxbank.cap.ykwm.model.REP_Payment;
import com.fxbank.cap.ykwm.model.REQ_Payment;
import com.fxbank.cap.ykwm.model.REQ_Payment.Invoice;
import com.fxbank.cap.ykwm.model.YkwmTraceLogModel;
import com.fxbank.cap.ykwm.service.IForwardToYkwmService;
import com.fxbank.cap.ykwm.service.IPaymentService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
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

@Service("REQ_30061001201")
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
		REQ_30061001201 reqDto = (REQ_30061001201) dto;
		//缴费金额
		BigDecimal pyFeeAmtT = new BigDecimal(reqDto.getReqBody().getPyFeeAmtT());
		//用户欠费金额
		BigDecimal userDbtAmtT = new BigDecimal(reqDto.getReqBody().getUserDbtAmtT());
		if(pyFeeAmtT.compareTo(userDbtAmtT)>0) {
			YkwmTradeExecuteException e = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10003);
			throw e;
		}
		return super.execute(reqDto);
	}

	/** 
	* @Title: hostCharge 
	* @Description: 核心记账
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public ModelBase hostCharge(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061001201 reqDto = (REQ_30061001201) dto;
		REQ_30061001201.REQ_BODY reqBody = reqDto.getReqBody();
		ESB_REQ_30011000101 req_30011000101 = new ESB_REQ_30011000101(myLog, dto.getSysDate(), dto.getSysTime(),
				dto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(req_30011000101.getReqSysHead(), reqDto)
				.setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId()).build();
		reqSysHead.setProgramId(reqDto.getReqSysHead().getProgramId());
		reqSysHead.setSourceBranchNo("CIP|cipToesb|RZAK|");
		req_30011000101.setReqSysHead(reqSysHead);

		ESB_REQ_30011000101.REQ_BODY esb_reqBody = req_30011000101.getReqBody();
		esb_reqBody.setBaseAcctNo("34128070020000004");// 卡号
		// 缴费方式 属于 ServDetail List 一部分，得new个对象，然后回传是一个对象
		/**
		ESB_REQ_30011000101.ServDetail servDetail = new ESB_REQ_30011000101.ServDetail();
		// servDetail.setChargeMode(reqBody.getPyFeeTpT());
		servDetail.setChargeMode("C");
		servDetail.setFeeType("1");
		servDetail.setFeeAmt("20");
		List<ServDetail> servDeailList = new ArrayList<ServDetail>();
		servDeailList.add(servDetail);
		esb_reqBody.setServDetail(servDeailList);// 缴费方式(对象)
		**/
		esb_reqBody.setTranType(reqBody.getPyFeeTpT().equals("1") ? "BH13" : "BH15");
		esb_reqBody.setTranCcy("CNY");
		
		esb_reqBody.setTranAmt(reqBody.getPyFeeAmtT());// 缴费金额
		esb_reqBody.setPassword(reqBody.getPassword());
		esb_reqBody.setOthBaseAcctNo(reqBody.getAcctNo());// 对方账号
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
		REQ_30061001201 reqDto = (REQ_30061001201) dto;
		REQ_30061001201.REQ_BODY reqBody = reqDto.getReqBody();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setAcctNoT(reqBody.getAcctNo());
		record.setPyFeeAmtT(reqBody.getPyFeeAmtT());
		record.setUserDbtAmtT(reqBody.getUserDbtAmtT());
		record.setUserCardNoT(reqBody.getUserCardNoT());
        record.setPyFeeTpT(reqBody.getPyFeeTpT());
        record.setHeatCompanyIdT(reqBody.getHeatCompanyIdT());
        record.setTeCheckNum(reqBody.getChannelRefNo());
        List<YkwmTraceLogModel.Invoice> list = new ArrayList<YkwmTraceLogModel.Invoice>();
        for(com.fxbank.cap.ykwm.dto.esb.REQ_30061001201.Invoice temp:reqBody.getInvoiceArray()) {
        	YkwmTraceLogModel.Invoice invoice = new YkwmTraceLogModel.Invoice();
        	invoice.setInvoiceTitle(temp.getInvcNaHdT3());
        	invoice.setArea(temp.getReimburseAreaT());
        	invoice.setInvoiceName(temp.getName());
        	invoice.setInvoiceNum(temp.getTxpyrDistNo());
        	invoice.setInvoiceAddress(temp.getUserAddr());
        	invoice.setBankNum(temp.getOpnAcctBnkNo());
        	record.setBillGetTpT(temp.getInvoiceDealMode());
        	list.add(invoice);
        }
        record.setInvoiceList(list);
		iPaymentService.hostTimeoutInit(record);
		
	}

	/** 
	* @Title: hostSuccessInitLog 
	* @Description: 核心记账成功登记
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void hostSuccessInitLog(DataTransObject dto, ModelBase model) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061001201 reqDto = (REQ_30061001201) dto;
		ESB_REP_30011000101 rep = (ESB_REP_30011000101) model;
		REQ_30061001201.REQ_BODY reqBody = reqDto.getReqBody();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setAcctNoT(reqBody.getAcctNo());
		record.setPyFeeAmtT(reqBody.getPyFeeAmtT());
		record.setUserDbtAmtT(reqBody.getUserDbtAmtT());
		record.setUserCardNoT(reqBody.getUserCardNoT());
        record.setPyFeeTpT(reqBody.getPyFeeTpT());
        record.setHeatCompanyIdT(reqBody.getHeatCompanyIdT());
        record.setTeCheckNum(reqBody.getChannelRefNo());
        if(null!=reqBody.getInvoiceArray()) {
        List<YkwmTraceLogModel.Invoice> list = new ArrayList<YkwmTraceLogModel.Invoice>();
        for(com.fxbank.cap.ykwm.dto.esb.REQ_30061001201.Invoice temp:reqBody.getInvoiceArray()) {
        	YkwmTraceLogModel.Invoice invoice = new YkwmTraceLogModel.Invoice();
        	invoice.setInvoiceTitle(temp.getInvcNaHdT3());
        	invoice.setArea(temp.getReimburseAreaT());
        	invoice.setInvoiceName(temp.getName());
        	invoice.setInvoiceNum(temp.getTxpyrDistNo());
        	invoice.setInvoiceAddress(temp.getUserAddr());
        	invoice.setBankNum(temp.getOpnAcctBnkNo());
        	record.setBillGetTpT(temp.getInvoiceDealMode());
        	list.add(invoice);
        }
        record.setInvoiceList(list);
        }
		record.setCoTransactionno(rep.getRepBody().getReference());
		record.setCoDate(rep.getRepSysHead().getTranDate());
		record.setCoRspcode(rep.getRepSysHead().getRet().get(0).getRetCode());
		record.setCoRspmsg(rep.getRepSysHead().getRet().get(0).getRetMsg());
		iPaymentService.hostSuccessInit(record);
		
	}

	/** 
	* @Title: othCharge 
	* @Description: 热电记账
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public ModelBase othCharge(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30061001201 reqDto = (REQ_30061001201) dto;
		REQ_30061001201.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_Payment reqPayment = new REQ_Payment(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());

		Map<String, String> map = new HashMap<String, String>();
		map.put("GM", "01");// GM、SJ 问柜面 01\02 热电约定
		map.put("SJ", "02");

		reqPayment.setSource(map.get(reqDto.getReqSysHead().getSourceType()));// 缴费渠道
		// ----------------------------是不是约定的那种 ----------------------------------------
		reqPayment.setBranchNum(reqDto.getReqSysHead().getBranchId());// 网点编号 ??????
		reqPayment.setBatchNum(publicService.getSysDate("CIP").toString());// 批次号 取渠道日期 publicService公共服务

		reqPayment.setCheckNum(reqBody.getChannelRefNo());// 流水号 柜面需要处理，将查询的流水送给缴费的接口
        BigDecimal payment = new BigDecimal(reqBody.getPyFeeAmtT());
		reqPayment.setPayment(Double.parseDouble(payment.toString()));// 缴费金额 加上快递费 不带邮寄费
		
		reqPayment.setInvoiceStyle(reqBody.getInvoiceArray()==null?0:Integer.parseInt(reqBody.getInvoiceArray().get(0).getInvoiceDealMode()));
		reqPayment.setInvoiceCount(reqBody.getInvoiceArray()==null?0:reqBody.getInvoiceArray().size());
		List<Invoice> list = new ArrayList<Invoice>();
		// 数据类型 变量名 ： 数组名
		if(reqBody.getInvoiceArray()!=null) {
		for (REQ_30061001201.Invoice a : reqBody.getInvoiceArray()) {

			Invoice invoice = new Invoice();

			invoice.setInvoiceTitle(a.getInvcNaHdT3());
			invoice.setArea(Double.parseDouble((a.getReimburseAreaT())));
			invoice.setInvoiceName(a.getName());
			invoice.setInvoiceNum(a.getTxpyrDistNo());
			invoice.setBankNum(a.getOpnAcctBnkNo());
			invoice.setInvoiceAddress(a.getUserAddr());
			list.add(invoice);
		}
		}
		reqPayment.setInvoiceList(list);
		REP_Payment repPayment = forwardToYkwmService.sendToYkwm(reqPayment, REP_Payment.class);
	return repPayment;
	}

	/** 
	* @Title: queryOth 
	* @Description: 热电记账超时情况下，查询交易状态，此交易不再次查询
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void queryOth(DataTransObject dto) throws SysTradeExecuteException {
	}

	/** 
	* @Title: othTimeout 
	* @Description: 判断热电记账是否超时
	* @param @param e
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
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

	/** 
	* @Title: undoHostCharge 
	* @Description: 核心冲正
	* @param @param dto
	* @param @param e
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public ModelBase undoHostCharge(DataTransObject dto,ModelBase model, SysTradeExecuteException e) throws SysTradeExecuteException {
		REQ_30061001201 reqDto = (REQ_30061001201) dto;
		ESB_REP_30011000101 rep = (ESB_REP_30011000101) model;
		MyLog myLog = logPool.get();
		ESB_REQ_30014000101 esbReq_30014000101 = new ESB_REQ_30014000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30014000101.getReqSysHead(), reqDto).
				setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId())
				.setSourceType("BH").build();
		reqSysHead.setProgramId(reqDto.getReqSysHead().getProgramId());
		esbReq_30014000101.setReqSysHead(reqSysHead);
		ESB_REQ_30014000101.REQ_BODY reqBody_30014000101 = esbReq_30014000101.getReqBody();

		reqBody_30014000101.setReference(rep.getRepBody().getReference());
		
		reqBody_30014000101.setReversalReason(e.getRspMsg());

		ESB_REP_30014000101 esbRep_30014000101 = forwardToESBService.sendToESB(esbReq_30014000101, reqBody_30014000101,
				ESB_REP_30014000101.class);
		return esbRep_30014000101;
	}

	/** 
	* @Title: updateOthTimeout 
	* @Description: 热电记账超时 更新日志 
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateOthTimeout(DataTransObject dto) throws SysTradeExecuteException {
		REQ_30061001201 reqDto = (REQ_30061001201) dto;
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
    	iPaymentService.othTimeoutUpdate(record);
		
	}

	/** 
	* @Title: updateOthSuccess 
	* @Description: 热电记账成功更新日志
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateOthSuccess(DataTransObject dto, ModelBase model) throws SysTradeExecuteException {
		REQ_30061001201 reqDto = (REQ_30061001201) dto;
		REP_Payment rep = (REP_Payment)model;
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setTicketNumber(rep.getCode());
		record.setPyRspcode(rep.getResult());
    	iPaymentService.othSuccessUpdate(record);
		
	}

	/** 
	* @Title: updateHostUndoSuccess 
	* @Description: 核心冲正成功 更新日志 
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateHostUndoSuccess(DataTransObject dto, ModelBase model) throws SysTradeExecuteException {
		REQ_30061001201 reqDto = (REQ_30061001201) dto;
		ESB_REP_30014000101 res = (ESB_REP_30014000101) model;
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setCoDate(res.getRepSysHead().getTranDate());
		record.setCoTransactionno(res.getRepSysHead().getReference());
		record.setCoRspcode(res.getRepSysHead().getRet().get(0).getRetCode());
		record.setCoRspmsg(res.getRepSysHead().getRet().get(0).getRetMsg());
    	iPaymentService.hostUndoSuccess(record);
		
	}

	/** 
	* @Title: updateOthError 
	* @Description: 热电记账失败 更新日志
	* @param @param dto
	* @param @param e
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateOthError(DataTransObject dto, SysTradeExecuteException e) throws SysTradeExecuteException {
		REQ_30061001201 reqDto = (REQ_30061001201) dto;
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setPyRspcode(e.getRspCode());
		record.setPyErrorMsg(e.getRspMsg());
    	iPaymentService.othErrorUpdate(record);
		
	}

	/** 
	* @Title: updateHostUndoTimeout 
	* @Description: 核心冲正超时 更新日志
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateHostUndoTimeout(DataTransObject dto) throws SysTradeExecuteException {
		REQ_30061001201 reqDto = (REQ_30061001201) dto;
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
    	iPaymentService.hostUndoTimeout(record);
		
	}

	/** 
	* @Title: updateOthTimeoutSucc 
	* @Description: 热电记账超时情况，再次发起查询交易状态，返回成功则更新第三方记账成功，此交易不再查询 
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateOthTimeoutSucc(DataTransObject dto) throws SysTradeExecuteException {
	}

	/** 
	* @Title: backMsg 
	* @Description: 给柜面输出 
	* @param @param dto
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public DataTransObject backMsg(DataTransObject dto,ModelBase model,ModelBase model1) throws SysTradeExecuteException {
		REQ_30061001201 reqDto = (REQ_30061001201) dto;
		REP_30061001201 rep = new REP_30061001201();
		ESB_REP_30011000101 rep30011000101 = (ESB_REP_30011000101)model;
		REP_Payment repPayment = (REP_Payment)model1;
		rep.getRepBody().setChannelDate(reqDto.getSysDate().toString());
		rep.getRepBody().setChannelSeqNo(reqDto.getSysTraceno().toString());
		rep.getRepBody().setHostTraceNo(rep30011000101.getRepBody().getReference());
		List<TicketCode> list = new ArrayList<TicketCode>();
		for(String temp:repPayment.getCode().split("\\^")) {
			TicketCode c = new TicketCode();
			c.setTicketCodeT(temp);
			list.add(c);
		}
		rep.getRepBody().setTicketCodeArray(list);
		return rep;
	}

	/** 
	* @Title: updateHostUndoError 
	* @Description: 核心冲正失败 更新日志
	* @param @param dto
	* @param @param e
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateHostUndoError(DataTransObject dto, SysTradeExecuteException e) throws SysTradeExecuteException {
		REQ_30061001201 reqDto = (REQ_30061001201) dto;
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setCoRspcode(e.getRspCode());
		record.setCoRspmsg(e.getRspMsg());
    	iPaymentService.hostUndoError(record);
		
	}

	/** 
	* @Title: othErrorException 
	* @Description: 热电记账失败，返回热电记账失败错误
	* @param @param e
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public SysTradeExecuteException othErrorException(SysTradeExecuteException e) {
		return e;
	}

	/** 
	* @Title: hostUndoSuccessException 
	* @Description: 核心冲正成功，提示热电记账失败错误和退款成功
	* @param @param e
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public SysTradeExecuteException hostUndoSuccessException(SysTradeExecuteException e) {
		SysTradeExecuteException e1 = new SysTradeExecuteException(e.getRspCode(), e.getRspMsg()+"(退款成功)");
		return e1;
	}

	/** 
	* @Title: hostUndoTimeoutException 
	* @Description: 核心冲正超时，提示热电记账失败错误和退款超时，请确认账务状态
	* @param @param e
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public SysTradeExecuteException hostUndoTimeoutException(SysTradeExecuteException e) {
		SysTradeExecuteException e1 = new SysTradeExecuteException(e.getRspCode(), e.getRspMsg()+"(退款超时，请确认账务状态)");
		return e1;
	}

	/** 
	* @Title: hostUndoErrorException 
	* @Description: 核心冲正失败，提示热电记账失败错误和退款失败
	* @param @param e
	* @param @param e1
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public SysTradeExecuteException hostUndoErrorException(SysTradeExecuteException e, SysTradeExecuteException e1) {
		SysTradeExecuteException e2 = new SysTradeExecuteException(e.getRspCode(), e.getRspMsg()+"("+e1.getRspMsg()+",退款失败)");
		return e2;
	}

	
}