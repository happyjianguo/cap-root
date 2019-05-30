package com.fxbank.cap.ykwm.trade.esb;

import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.esb.REP_30012002003;
import com.fxbank.cap.ykwm.dto.esb.REQ_30012002003;
import com.fxbank.cap.ykwm.exception.YkwmTradeExecuteException;
import com.fxbank.cap.ykwm.model.REP_Cancel;
import com.fxbank.cap.ykwm.model.REQ_Cancel;
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

@Service("REQ_30012002003")
public class TR_Cancel extends BaseTradeT2 implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(TR_Cancel.class);

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
		super.TRADE_DESC = "冲正交易";
		super.logger = logger;
		REQ_30012002003 reqDto = (REQ_30012002003) dto;
		super.notExistException = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10004);
		super.hostUndoTimeoutException = new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10002);
		return super.execute(reqDto);
	}

	/** 
	* @Title: backMsg 
	* @Description: 给柜面返回
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public DataTransObject backMsg(DataTransObject dto) throws SysTradeExecuteException {
		REP_30012002003 rep = new REP_30012002003();
		return rep;
	}

	/** 
	* @Title: undoOth 
	* @Description: 热电冲正
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void undoOth(DataTransObject dto,ModelBase model) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30012002003 reqDto = (REQ_30012002003) dto;
		YkwmTraceLogModel record = (YkwmTraceLogModel)model;
		REQ_Cancel reqCancel = new REQ_Cancel(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());

		reqCancel.setSource(reqDto.getReqSysHead().getSourceType());// 缴费渠道
		reqCancel.setBatch(reqDto.getReqBody().getChannelDate());//批次号
		reqCancel.setCheckNum(record.getTeCheckNum());//交易流水号
		
		forwardToYkwmService.sendToYkwm(reqCancel, REP_Cancel.class);
	}

	/** 
	* @Title: othTimeout 
	* @Description: 热电冲正是否超时
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
	* @Title: undoHostCharge 
	* @Description: 核心冲正
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public ModelBase undoHostCharge(DataTransObject dto) throws SysTradeExecuteException {
		REQ_30012002003 reqDto = (REQ_30012002003) dto;
		REQ_30012002003.REQ_BODY reqBody = reqDto.getReqBody();
		MyLog myLog = logPool.get();
		ESB_REQ_30014000101 esbReq_30014000101 = new ESB_REQ_30014000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30014000101.getReqSysHead(), reqDto).
				setBranchId(reqDto.getReqSysHead().getBranchId()).setUserId(reqDto.getReqSysHead().getUserId()).build();
		reqSysHead.setProgramId("7J13");
		esbReq_30014000101.setReqSysHead(reqSysHead);
		ESB_REQ_30014000101.REQ_BODY reqBody_30014000101 = esbReq_30014000101.getReqBody();

		reqBody_30014000101.setChannelSeqNo(CIP.SYSTEM_ID+reqBody.getChannelDate()+String.format("%08d",Integer.parseInt(reqBody.getChannelSeqNo())));
		reqBody_30014000101.setReversalReason(reqBody.getUndoReason());

		ESB_REP_30014000101 esbRep_30014000101 = forwardToESBService.sendToESB(esbReq_30014000101, reqBody_30014000101,
				ESB_REP_30014000101.class);
		return esbRep_30014000101;
	}

	/** 
	* @Title: updateHostUndoTimeout 
	* @Description: 核心冲正超时更新日志
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateHostUndoTimeout(DataTransObject dto) throws SysTradeExecuteException {
		REQ_30012002003 reqDto = (REQ_30012002003) dto;
		REQ_30012002003.REQ_BODY reqBody = reqDto.getReqBody();
		MyLog myLog = logPool.get();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, Integer.parseInt(reqBody.getChannelDate()), reqDto.getSysTime(),
				Integer.parseInt(reqBody.getChannelSeqNo()));
    	iPaymentService.hostUndoTimeout(record);
		
	}

	/** 
	* @Title: updateHostUndoError 
	* @Description: 核心冲正失败更新日志
	* @param @param dto
	* @param @param e
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateHostUndoError(DataTransObject dto, SysTradeExecuteException e) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30012002003 reqDto = (REQ_30012002003) dto;
		REQ_30012002003.REQ_BODY reqBody = reqDto.getReqBody();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, Integer.parseInt(reqBody.getChannelDate()), reqDto.getSysTime(),
				Integer.parseInt(reqBody.getChannelSeqNo()));
		record.setCoRspcode(e.getRspCode());
		record.setCoRspmsg(e.getRspMsg());
    	iPaymentService.hostUndoError(record);
		
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
	public SysTradeExecuteException hostUndoErrorException(SysTradeExecuteException e) {
		SysTradeExecuteException e2 = new SysTradeExecuteException(e.getRspCode(), e.getRspMsg()+"(退款失败)");
		return e2;
	}

	/** 
	* @Title: updateHostUndoSucc 
	* @Description: 核心冲正成功 更新日志 
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateHostUndoSucc(DataTransObject dto, ModelBase model) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30012002003 reqDto = (REQ_30012002003) dto;
		ESB_REP_30014000101 res = (ESB_REP_30014000101) model;
		REQ_30012002003.REQ_BODY reqBody = reqDto.getReqBody();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, Integer.parseInt(reqBody.getChannelDate()), reqDto.getSysTime(),
				Integer.parseInt(reqBody.getChannelSeqNo()));
		record.setCoDate(res.getRepSysHead().getTranDate());
		record.setCoTransactionno(res.getRepSysHead().getReference());
		record.setCoRspcode(res.getRepSysHead().getRet().get(0).getRetCode());
		record.setCoRspmsg(res.getRepSysHead().getRet().get(0).getRetMsg());
    	iPaymentService.hostUndoSuccess(record);
		
	}

	/** 
	* @Title: updateOthUndoSucc 
	* @Description: 热电冲正成功更新日志 
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateOthUndoSucc(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30012002003 reqDto = (REQ_30012002003) dto;
		REQ_30012002003.REQ_BODY reqBody = reqDto.getReqBody();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, Integer.parseInt(reqBody.getChannelDate()), reqDto.getSysTime(),
				Integer.parseInt(reqBody.getChannelSeqNo()));
		iPaymentService.othUndoSuccUpdate(record);
		
	}

	/** 
	* @Title: updateOthUndoTimeout 
	* @Description: 热电冲正超时更新日志
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateOthUndoTimeout(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30012002003 reqDto = (REQ_30012002003) dto;
		REQ_30012002003.REQ_BODY reqBody = reqDto.getReqBody();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, Integer.parseInt(reqBody.getChannelDate()), reqDto.getSysTime(),
				Integer.parseInt(reqBody.getChannelSeqNo()));
		iPaymentService.othUndoTimeoutUpdate(record);
		
	}

	/** 
	* @Title: queryRecord 
	* @Description: 根据渠道流水号和日期查询记账记录
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public ModelBase queryRecord(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30012002003 reqDto = (REQ_30012002003) dto;
		REQ_30012002003.REQ_BODY reqBody = reqDto.getReqBody();
		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, Integer.parseInt(reqBody.getChannelDate()), reqDto.getSysTime(),
				Integer.parseInt(reqBody.getChannelSeqNo()));
		record = iPaymentService.queryLogBySeqNo(record);
		return record;
	}
	
}