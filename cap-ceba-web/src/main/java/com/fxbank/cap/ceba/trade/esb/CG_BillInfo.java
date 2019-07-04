package com.fxbank.cap.ceba.trade.esb;

import java.math.BigDecimal;
import java.util.Map;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.ceba.dto.esb.REP_30062001001;
import com.fxbank.cap.ceba.dto.esb.REQ_30062001001;
import com.fxbank.cap.ceba.exception.CebaTradeExecuteException;
import com.fxbank.cap.ceba.model.CebaChargeLogModel;
import com.fxbank.cap.ceba.model.ErrorInfo;
import com.fxbank.cap.ceba.model.REP_BJCEBBCRes;
import com.fxbank.cap.ceba.model.REP_BJCEBQBIRes;
import com.fxbank.cap.ceba.model.REQ_BJCEBBCReq;
import com.fxbank.cap.ceba.model.REQ_BJCEBQBIReq;
import com.fxbank.cap.ceba.service.ICebaChargeLogService;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.esb.model.ses.ESB_REP_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.cap.esb.service.IForwardToESBService;
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
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.cip.pub.service.IPublicService;
import redis.clients.jedis.Jedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** 
* @ClassName: CG_BillInfo 
* @Description: 缴费单销账
* @作者 杜振铎
* @date 2019年5月24日 下午3:18:25 
*  
*/
@Service("REQ_30062001001")
public class CG_BillInfo extends BaseTradeT1 implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(CG_BillInfo.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToCebaService forwardToCebaService;
	
	@Reference(version = "1.0.0")
	private ICebaChargeLogService cebaChargeLogService;
	
	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Resource
	private LogPool logPool;

	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";
	
	/** 
	* @Fields SUCCESS_FLAG : 客户无欠费（交易超时时，贵方可以再一次查询该笔交易，如果提示客户无欠费，则说明缴费成功。）
	*/ 
	private final static String SUCCESS_FLAG = "DEF0002";
	
	/** 
	* @Fields UNDO_CODE : 根据光大银行返回的错误码判断是否退款，0退款；1不退款 
	*/ 
	private final static String UNDO_CODE = "0";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		super.hostTimeoutException = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10006);
		super.othTimeoutException = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10003);
		super.TRADE_DESC = "缴费单销账";
		super.othTimeoutQuery = true;
		super.logger = logger;
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
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
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		MyLog myLog = logPool.get();
		ESB_REQ_30011000101 esbReq_30011000101 = new ESB_REQ_30011000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000101.getReqSysHead(), reqDto)
				.setSourceType("MB").build();
		esbReq_30011000101.setReqSysHead(reqSysHead);
  
		ESB_REQ_30011000101.REQ_BODY reqBody_30011000101 = esbReq_30011000101.getReqBody();
		REQ_30062001001.REQ_BODY reqBody = reqDto.getReqBody();
		// 账号/卡号
		reqBody_30011000101.setBaseAcctNo(reqBody.getPayAcctNo());
		reqBody_30011000101.setOthBaseAcctNo("30060200000014");
		// 账户名称
		reqBody_30011000101.setAcctName(reqBody.getClientNnae());
		// 交易类型
		reqBody_30011000101.setTranType("GD01");
		// 交易币种
		reqBody_30011000101.setTranCcy("CNY");
		//密码
		reqBody_30011000101.setPassword(reqBody.getPassword());
		// 交易金额
		reqBody_30011000101.setTranAmt(new BigDecimal(reqBody.getUnpaidAmt()).toString());
		// 记账渠道类型
		reqBody_30011000101.setChannelType("GD");
		// 清算日期
		reqBody_30011000101.setSettlementDate(reqDto.getSysDate().toString());
		// 对账标识,Y-参与对账;N-不参与对账
		reqBody_30011000101.setCollateFlag("Y");
		ESB_REP_30011000101 esbRep_30011000101 = forwardToESBService.sendToESB(esbReq_30011000101, reqBody_30011000101,
				ESB_REP_30011000101.class);
		return esbRep_30011000101;
	}


	/** 
	* @Title: othCharge 
	* @Description: 光大银行记账 
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public ModelBase othCharge(DataTransObject dto) throws SysTradeExecuteException {
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		MyLog myLog = logPool.get();
		REQ_30062001001.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_BJCEBBCReq req = new REQ_BJCEBBCReq(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		String instld = null;
		try (Jedis jedis = myJedis.connect()) {
			instld = jedis.get(COMMON_PREFIX + "ceba_instld");
		}
		req.getHead().setInstId(instld);
		req.getHead().setAnsTranCode("BJCEBBCReq");
		req.getHead().setTrmSeqNum(publicService.getSysDate("CIP").toString()+publicService.getSysTraceno());
		REQ_BJCEBBCReq.Tin tin = req.getTin();
		tin.setBillKey(reqBody.getBillKey());
		tin.setCompanyId(reqBody.getPyCityCode()+reqBody.getPyCreditNo());
		tin.setBillNo(reqDto.getSysTraceno().toString());
		tin.setPayDate(reqDto.getSysDate().toString()+reqDto.getSysTime().toString());
		tin.setCustomerName(reqBody.getClientNnae());
		tin.setPayAccount(reqBody.getPayAcctNo());
		tin.setPin("");
		tin.setPayAmount(new BigDecimal(reqBody.getUnpaidAmt()));
		tin.setAcType(reqBody.getAcctType());
		tin.setContractNo(reqBody.getContractNo());
		//TODO
		//REP_BJCEBBCRes res = forwardToCebaService.sendToCeba(req, REP_BJCEBBCRes.class);
		return null;
	}

	/** 
	* @Title: hostTimeoutInitLog 
	* @Description: 核心记账超时登记
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void hostTimeoutInitLog(DataTransObject dto) throws SysTradeExecuteException {
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		MyLog myLog = logPool.get();
		REQ_30062001001.REQ_BODY reqBody = reqDto.getReqBody();
		CebaChargeLogModel record = new CebaChargeLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setTxBranch(reqDto.getReqSysHead().getBranchId());
		record.setTxTel(reqDto.getReqSysHead().getUserId());
		record.setSourceType(reqDto.getSourceType());
        record.setBillKey(reqBody.getBillKey());
        record.setCompanyId(reqBody.getPyCityCode()+reqBody.getPyCreditNo());
        record.setCustomerName(reqBody.getClientNnae());
        record.setPayAccount(reqBody.getPayAcctNo());
        record.setPayAmount(new BigDecimal(reqBody.getUnpaidAmt()));
        record.setAcType(reqBody.getAcctType());
        record.setContractNo(reqBody.getContractNo());
        record.setSeqNo(reqDto.getReqSysHead().getSeqNo());
		cebaChargeLogService.hostTimeoutInit(record);
		
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
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		ESB_REP_30011000101 rep = (ESB_REP_30011000101) model;
		MyLog myLog = logPool.get();
		REQ_30062001001.REQ_BODY reqBody = reqDto.getReqBody();
		CebaChargeLogModel record = new CebaChargeLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setTxBranch(reqDto.getReqSysHead().getBranchId());
		record.setTxTel(reqDto.getReqSysHead().getUserId());
		record.setSourceType(reqDto.getSourceType());
        record.setBillKey(reqBody.getBillKey());
        record.setCompanyId(reqBody.getPyCityCode()+reqBody.getPyCreditNo());
        record.setCustomerName(reqBody.getClientNnae());
        record.setPayAccount(reqBody.getPayAcctNo());
        record.setPayAmount(new BigDecimal(reqBody.getUnpaidAmt()));
        record.setAcType(reqBody.getAcctType());
        record.setContractNo(reqBody.getContractNo());
        record.setSeqNo(reqDto.getReqSysHead().getSeqNo());
        record.setHostDate(Integer.parseInt(rep.getRepSysHead().getTranDate()));
        record.setHostTraceNo(rep.getRepBody().getReference());
        record.setHostRetCode(rep.getRepSysHead().getRet().get(0).getRetCode());
        record.setHostRetMsg(rep.getRepSysHead().getRet().get(0).getRetMsg());
		cebaChargeLogService.hostSuccessInit(record);
		
	}

	/** 
	* @Title: othTimeout 
	* @Description: 根据状态码判断光大银行记账是否超时
	* @param @param e
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public Boolean othTimeout(SysTradeExecuteException e) throws SysTradeExecuteException {
		return e.getRspCode().equals(SysTradeExecuteException.CIP_E_000009) || e.getRspCode().equals("NPP0005");
	}

	/** 
	* @Title: othSuccess 
	* @Description: 光大银行第一次记账超时，判断第二次记账是否成功
	* @param @param e
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public Boolean othSuccess(SysTradeExecuteException e) throws SysTradeExecuteException {
		return e.getRspCode().equals(SUCCESS_FLAG);
	}

	/** 
	* @Title: othErrorUndoHost 
	* @Description: 光大银行核心记账失败，错误码为退款时核心冲正，错误码为暂不退款直接报错 
	* @param @param e
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public Boolean othErrorUndoHost(SysTradeExecuteException e) throws SysTradeExecuteException {
		Map<String, ErrorInfo> errorMap = getErrorMap();
		// 需要将jsonObject转换为ErrorInfo
		ErrorInfo errorInfo = JsonUtil.toBean(JSON.toJSONString(errorMap.get(e.getRspCode())), ErrorInfo.class);
		// cgErrorType 0 退款 1暂不退款
		return errorInfo.getCgErrorType().equals(UNDO_CODE);
	}
	/** 
	* @Title: getErrorList 
	* @Description: 获取redis里错误码信息集合
	* @param @return    设定文件 
	* @return ErrorList    返回类型 
	* @throws 
	*/
	private Map<String,ErrorInfo> getErrorMap() {
		String jsonStr = null;
		try(Jedis jedis = myJedis.connect()){
			jsonStr = jedis.get(COMMON_PREFIX+"ceba_error_list");
        }
		if(jsonStr==null||jsonStr.length()==0){
			logger.error("渠道未配置["+COMMON_PREFIX + "ceba_error_list"+"]");
			throw new RuntimeException("渠道未配置["+COMMON_PREFIX + "ceba_error_list"+"]");
		}
		Map<String,ErrorInfo> map = (Map)JSONObject.parse(jsonStr);
		return map;
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
	public ModelBase undoHostCharge(DataTransObject dto,SysTradeExecuteException e) throws SysTradeExecuteException {
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		MyLog myLog = logPool.get();
		ESB_REQ_30014000101 esbReq_30014000101 = new ESB_REQ_30014000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30014000101.getReqSysHead(), reqDto).build();
		esbReq_30014000101.setReqSysHead(reqSysHead);
		ESB_REQ_30014000101.REQ_BODY reqBody_30014000101 = esbReq_30014000101.getReqBody();

		reqBody_30014000101.setChannelSeqNo(CIP.SYSTEM_ID+reqDto.getSysDate()+String.format("%08d",reqDto.getSysTraceno()));
		Map<String,ErrorInfo> errorMap = getErrorMap();
		//需要将jsonObject转换为ErrorInfo
		ErrorInfo errorInfo = JsonUtil.toBean(JSON.toJSONString(errorMap.get(e.getRspCode())),ErrorInfo.class);
		reqBody_30014000101.setReversalReason(errorInfo.getCgErrorMsg());

		ESB_REP_30014000101 esbRep_30014000101 = forwardToESBService.sendToESB(esbReq_30014000101, reqBody_30014000101,
				ESB_REP_30014000101.class);
		return esbRep_30014000101;
	}

	/** 
	* @Title: updateOthTimeout 
	* @Description: 光大银行记账超时更新日志 
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateOthTimeout(DataTransObject dto) throws SysTradeExecuteException {
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		MyLog myLog = logPool.get();
		CebaChargeLogModel record = new CebaChargeLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
    	cebaChargeLogService.othTimeoutUpdate(record);
		
	}

	/** 
	* @Title: updateOthSuccess 
	* @Description: 光大银行记账成功更新日志 
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateOthSuccess(DataTransObject dto, ModelBase model) throws SysTradeExecuteException {
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		MyLog myLog = logPool.get();
		REP_BJCEBBCRes res = (REP_BJCEBBCRes)model;
		CebaChargeLogModel record = new CebaChargeLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setBankBillNo(res.getTout().getBankBillNo());
		record.setReceiptNo(res.getTout().getReceiptNo());
		record.setAcctDate(res.getTout().getAcctDate());
    	cebaChargeLogService.othSuccessUpdate(record);
		
	}

	/** 
	* @Title: updateHostUndoSuccess 
	* @Description: 核心冲正成功更新日志
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateHostUndoSuccess(DataTransObject dto, ModelBase model) throws SysTradeExecuteException {
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		ESB_REP_30014000101 res = (ESB_REP_30014000101) model;
		MyLog myLog = logPool.get();
		CebaChargeLogModel record = new CebaChargeLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setHostDate(Integer.parseInt(res.getRepSysHead().getTranDate()));
		record.setHostTraceNo(res.getRepSysHead().getReference());
		record.setHostRetCode(res.getRepSysHead().getRet().get(0).getRetCode());
		record.setHostRetMsg(res.getRepSysHead().getRet().get(0).getRetMsg());
    	cebaChargeLogService.hostUndoSuccess(record);
	}

	/** 
	* @Title: updateOthError 
	* @Description: 光大银行记账失败更新日志 
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateOthError(DataTransObject dto,SysTradeExecuteException e) throws SysTradeExecuteException {
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		MyLog myLog = logPool.get();
		CebaChargeLogModel record = new CebaChargeLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setErrorCode(e.getRspCode());
    	cebaChargeLogService.othErrorUpdate(record);
		
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
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		MyLog myLog = logPool.get();
		CebaChargeLogModel record = new CebaChargeLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
    	cebaChargeLogService.hostUndoTimeout(record);
		
	}

	/** 
	* @Title: updateOthTimeoutSucc 
	* @Description: 光大银行第一次记账超时，第二次记账成功更新日志
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateOthTimeoutSucc(DataTransObject dto) throws SysTradeExecuteException {
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		MyLog myLog = logPool.get();
		CebaChargeLogModel record = new CebaChargeLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
    	cebaChargeLogService.othTimeoutSuccUpdate(record);
		
	}

	/** 
	* @Title: backMsg 
	* @Description: 返回渠道日期和流水号
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public DataTransObject backMsg(DataTransObject dto,ModelBase model) throws SysTradeExecuteException {
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		REP_30062001001 rep = new REP_30062001001();
		rep.getRepBody().setChannelDate(reqDto.getSysDate().toString());
		rep.getRepBody().setChannelSeqNo(reqDto.getSysTraceno().toString());
		return rep;
	}

	/** 
	* @Title: updateHostUndoError 
	* @Description: 核心冲正失败更新日志
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateHostUndoError(DataTransObject dto,SysTradeExecuteException e) throws SysTradeExecuteException {
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		MyLog myLog = logPool.get();
		CebaChargeLogModel record = new CebaChargeLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setHostRetCode(e.getRspCode());
		record.setHostRetMsg(e.getRspMsg());
    	cebaChargeLogService.hostUndoError(record);
    	
	}

	/** 
	* @Title: othErrorException 
	* @Description: 光大银行记账失败，根据光大银行错误码返回错误信息
	* @param @param e
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public SysTradeExecuteException othErrorException(SysTradeExecuteException e) {
		Map<String,ErrorInfo> errorMap = getErrorMap();
		ErrorInfo errorInfo = JsonUtil.toBean(JSON.toJSONString(errorMap.get(e.getRspCode())),ErrorInfo.class);
		String errorMsg = errorInfo.getCgErrorMsg();
		SysTradeExecuteException e1 = new SysTradeExecuteException(e.getRspCode(), errorMsg);
		return e1;
	}

	/** 
	* @Title: hostUndoSuccessException 
	* @Description: 核心冲正成功，提示光大银行记账失败错误信息（退款成功）
	* @param @param e
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public SysTradeExecuteException hostUndoSuccessException(SysTradeExecuteException e) {
		Map<String,ErrorInfo> errorMap = getErrorMap();
		ErrorInfo errorInfo = JsonUtil.toBean(JSON.toJSONString(errorMap.get(e.getRspCode())),ErrorInfo.class);
		String errorMsg = errorInfo.getCgErrorMsg();
		SysTradeExecuteException e1 = new SysTradeExecuteException(e.getRspCode(), errorMsg+"(退款成功)");
		return e1;
	}

	/** 
	* @Title: hostUndoTimeoutException 
	* @Description: 核心冲正超时，提示光大银行记账失败错误信息（退款成功）
	* @param @param e
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public SysTradeExecuteException hostUndoTimeoutException(SysTradeExecuteException e) {
		Map<String,ErrorInfo> errorMap = getErrorMap();
		ErrorInfo errorInfo = JsonUtil.toBean(JSON.toJSONString(errorMap.get(e.getRspCode())),ErrorInfo.class);
		String errorMsg = errorInfo.getCgErrorMsg();
		SysTradeExecuteException e1 = new SysTradeExecuteException(e.getRspCode(), errorMsg+"(退款超时，请确认账务状态)");
		return e1;
	}

	/** 
	* @Title: hostUndoErrorException 
	* @Description: 核心冲正失败，提示光大银行记账失败错误信息（核心冲正失败错误信息，退款失败）
	* @param @param e
	* @param @param e1
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public SysTradeExecuteException hostUndoErrorException(SysTradeExecuteException e, SysTradeExecuteException e1) {
		Map<String,ErrorInfo> errorMap = getErrorMap();
		ErrorInfo errorInfo = JsonUtil.toBean(JSON.toJSONString(errorMap.get(e.getRspCode())),ErrorInfo.class);
		String errorMsg = errorInfo.getCgErrorMsg();
		SysTradeExecuteException e2 = new SysTradeExecuteException(e.getRspCode(), errorMsg+"("+e1.getRspMsg()+",退款失败)");
		return e2;
	}

	/** 
	* @Title: queryOth 
	* @Description: 查询缴费单信息 
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void queryOth(DataTransObject dto) throws SysTradeExecuteException {
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		MyLog myLog = logPool.get();
		REQ_BJCEBQBIReq req = new REQ_BJCEBQBIReq(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		String instld = null;
		try (Jedis jedis = myJedis.connect()) {
			instld = jedis.get(COMMON_PREFIX + "ceba_instld");
		}
		req.getHead().setInstId(instld);
		req.getHead().setAnsTranCode("BJCEBQBIReq");
		req.getHead().setTrmSeqNum(publicService.getSysDate("CIP").toString()+publicService.getSysTraceno());
		req.getTin().setBillKey(reqDto.getReqBody().getBillKey());
		req.getTin().setCompanyId(reqDto.getReqBody().getPyCityCode()+reqDto.getReqBody().getPyCreditNo());
		req.getTin().setQueryNum("1");
		//TODO
		//forwardToCebaService.sendToCeba(req, REP_BJCEBQBIRes.class);
	}

}