package com.fxbank.cap.ceba.trade.esb;

import java.math.BigDecimal;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.esb.REP_30062001001;
import com.fxbank.cap.ceba.dto.esb.REQ_30062001001;
import com.fxbank.cap.ceba.exception.CebaTradeExecuteException;
import com.fxbank.cap.ceba.model.BillChargeLogModel;
import com.fxbank.cap.ceba.model.ErrorInfo;
import com.fxbank.cap.ceba.model.ErrorList;
import com.fxbank.cap.ceba.model.REP_BJCEBBCRes;
import com.fxbank.cap.ceba.model.REP_BJCEBBRQRes;
import com.fxbank.cap.ceba.model.REQ_BJCEBBCReq;
import com.fxbank.cap.ceba.model.REQ_BJCEBBRQReq;
import com.fxbank.cap.ceba.service.IBillChargeLogService;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.esb.model.ses.ESB_REP_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REP_30014000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30011000101;
import com.fxbank.cap.esb.model.ses.ESB_REQ_30014000101;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.EsbReqHeaderBuilder;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.cip.pub.service.IPublicService;

import redis.clients.jedis.Jedis;



/** 
* @ClassName: CG_BillInfo 
* @Description: 缴费单销账 
* @作者 杜振铎
* @date 2019年5月10日 上午11:30:05 
*  
*/
@Service("REQ_30062001001")
public class CG_BillInfo extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(CG_BillInfo.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToCebaService forwardToCebaService;
	
	@Reference(version = "1.0.0")
	private IBillChargeLogService billChargeLogService;
	
	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";
	
	private final static String SUCCESS_FLAG = "DEF0002";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30062001001 reqDto = (REQ_30062001001) dto;
		REP_30062001001 rep = new REP_30062001001();
		//核心记账日期
		String hostDate = null;
		//核心记账流水
		String hostTraceNo = null;
		//核心记账响应码
		String hostRetCode = null;
		//核心记账响应信息
		String hostRetMsg = null;
		//核心记账
		ESB_REP_30011000101 esbRep_30011000101 = hostCharge(reqDto);
		hostDate = esbRep_30011000101.getRepSysHead().getTranDate();
		hostTraceNo = esbRep_30011000101.getRepBody().getReference();
		hostRetCode = esbRep_30011000101.getRepSysHead().getRet().get(0).getRetCode();
		hostRetMsg = esbRep_30011000101.getRepSysHead().getRet().get(0).getRetMsg();
		myLog.info(logger, "缴费单销账核心记账成功，渠道日期"+reqDto.getSysDate()+"渠道流水号"+reqDto.getSysTraceno());
		//缴费单销账登记
		BillChargeLogModel record = new BillChargeLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record = initBillCharge(reqDto,hostDate,hostTraceNo,hostRetCode,hostRetMsg);
		//光大银行记账流水号
		String bankBillNo = null;
		//打印凭证号码
		String receiptNo = null;
		//光大银行记账日期
		String acctDate = null;
		try {
			//光大银行核心记账
			REP_BJCEBBCRes res = cebaCharge(reqDto);
			myLog.info(logger, "缴费单销账光大银行核心记账成功，渠道日期"+reqDto.getSysDate()+"渠道流水号"+reqDto.getSysTraceno());
			REP_BJCEBBCRes.Tout tout = res.getTout();
			bankBillNo = tout.getBankBillNo();
			receiptNo = tout.getReceiptNo();
			acctDate = tout.getAcctDate();
		} catch (SysTradeExecuteException e) {
			//交易超时时，再一次查询该笔交易，如果提示客户无欠费，则说明缴费成功
			if(e.getRspCode().equals(SysTradeExecuteException.CIP_E_000004)) {
				//光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败
				record.setPayState("1");
            	billChargeLogService.logUpd(record);
				try {
					REP_BJCEBBCRes res = cebaCharge(reqDto);
					REP_BJCEBBCRes.Tout tout = res.getTout();
					bankBillNo = tout.getBankBillNo();
					receiptNo = tout.getReceiptNo();
					acctDate = tout.getAcctDate();
				} catch (SysTradeExecuteException e1) {
					//缴费成功
                    if(e1.getRspCode().equals(SUCCESS_FLAG)) {
                    	REP_BJCEBBRQRes res = queryCebaResult(reqDto);
                    	bankBillNo = res.getTout().getBankBillNo();
                    	record.setBankBillNo(res.getTout().getBankBillNo());
                    	//光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败
                    	record.setPayState("2");
                    	billChargeLogService.logUpd(record);
                    }else {
                    	//缴费失败
                    	cebaChargeFail(reqDto,hostTraceNo,record,e1);
                    }
				}
			}else {
				//缴费失败
				cebaChargeFail(reqDto,hostTraceNo,record,e);
		}
		}
		myLog.info(logger, "缴费单销账成功，渠道日期"+reqDto.getSysDate()+"渠道流水号"+reqDto.getSysTraceno());
		record.setBankBillNo(bankBillNo);
		record.setAcctDate(acctDate);
		record.setReceiptNo(receiptNo);
		//光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败
		record.setPayState("2");
		billChargeLogService.logUpd(record);
		rep.getRepBody().setPltfSeqNo(bankBillNo);
		rep.getRepBody().setPrintVoucherNo(receiptNo);
		rep.getRepBody().setPostDate(acctDate);
		return rep;
	}
	/** 
	* @Title: cebaChargeFail 
	* @Description: 光大银行核心记账失败处理
	* @param @param reqDto
	* @param @param hostTraceNo
	* @param @param record
	* @param @param e
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void cebaChargeFail(REQ_30062001001 reqDto,String hostTraceNo,BillChargeLogModel record,SysTradeExecuteException e) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		//光大银行核心记账失败，错误码为退款时核心冲正，错误码为暂不退款直接报错	
		ErrorList errorList = getErrorList();
        String errorMsg = null, hostDate = null,hostRetCode = null,hostRetMsg = null;
		for(ErrorInfo errorInfo:errorList.getData()){
			if(errorInfo.getErrorCode().equals(e.getRspCode())) {
				//cgErrorType 0 退款 1暂不退款
				if (errorInfo.getCgErrorType().equals("0")) {
						try {
							ESB_REP_30014000101 res = hostReversal(reqDto, hostTraceNo);
							hostDate = res.getRepSysHead().getTranDate();
							hostTraceNo = res.getRepSysHead().getReference();
							hostRetCode = res.getRepSysHead().getRet().get(0).getRetCode();
							hostRetMsg = res.getRepSysHead().getRet().get(0).getRetMsg();
						} catch (SysTradeExecuteException e2) {
							if("CIP_E_000004".equals(e2.getRspCode())) {
								//核心记账状态，0-成功，1-冲正成功，2-冲正失败，3-冲正超时
								record.setHostState("3");
								//光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败
								record.setPayState("3");
								myLog.error(logger, "缴费单销账核心冲正超时，渠道日期" + reqDto.getSysDate() + 
										"渠道流水号" + reqDto.getSysTraceno(), e2);
								billChargeLogService.logUpd(record);
								CebaTradeExecuteException e3 = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10002,e.getRspMsg()+"(退款成功)");
								throw e3;
							}else {
								//核心记账状态，0-成功，1-冲正成功，2-冲正失败，3-冲正超时
								record.setHostState("2");
								//光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败
								record.setPayState("3");
								myLog.error(logger, "缴费单销账核心冲正失败，渠道日期" + reqDto.getSysDate() + 
										"渠道流水号" + reqDto.getSysTraceno(), e2);
								billChargeLogService.logUpd(record);
								CebaTradeExecuteException e3 = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10002,e.getRspMsg()+"(退款失败)");
								throw e3;
							}
						}
					myLog.info(logger, "缴费单销账核心冲正成功，渠道日期"+reqDto.getSysDate()+"渠道流水号"+reqDto.getSysTraceno());
					//核心记账状态，0-成功，1-冲正成功，2-冲正失败，3-冲正超时
					record.setHostState("1");
					//光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败
					record.setPayState("3");
					record.setHostDate(Integer.parseInt(hostDate));
					record.setHostTraceNo(hostTraceNo);
					record.setHostRetCode(hostRetCode);
					record.setHostRetMsg(hostRetMsg);
					//光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败
					record.setPayState("3");
					billChargeLogService.logUpd(record);
					CebaTradeExecuteException e3 = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10002,e.getRspMsg()+"(退款成功)");
					throw e3;
				} else {
					//光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败
					record.setPayState("3");
					billChargeLogService.logUpd(record);
					errorMsg = errorInfo.getCgErrorMsg();
					SysTradeExecuteException e1 = new SysTradeExecuteException(e.getRspCode(), errorMsg);
					myLog.error(logger, e1.getRspCode() + " | " + e1.getRspMsg());
					throw e1;
				}
			}
		}
	}
	/** 
	* @Title: hostCharge 
	* @Description: 核心记账 
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30011000101    返回类型 
	* @throws 
	*/
	private ESB_REP_30011000101 hostCharge(REQ_30062001001 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		// 交易机构
		String txBrno = null;
		// 柜员号
		String txTel = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "ceba_txbrno");
			txTel = jedis.get(COMMON_PREFIX + "ceba_txtel");
		}

		ESB_REQ_30011000101 esbReq_30011000101 = new ESB_REQ_30011000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30011000101.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		esbReq_30011000101.setReqSysHead(reqSysHead);

		ESB_REQ_30011000101.REQ_BODY reqBody_30011000101 = esbReq_30011000101.getReqBody();
		REQ_30062001001.REQ_BODY reqBody = reqDto.getReqBody();
		// 账号/卡号
		reqBody_30011000101.setBaseAcctNo(reqBody.getPayAcctNo());
		// 账户名称
		reqBody_30011000101.setAcctName(reqBody.getClientNnae());
		// 交易类型
		reqBody_30011000101.setTranType("CEBA");
		// 交易币种
		reqBody_30011000101.setTranCcy("CNY");
		// 交易金额
		reqBody_30011000101.setTranAmt(new BigDecimal(reqBody.getUnpaidAmt()).movePointLeft(2).toString());
		// 记账渠道类型
		reqBody_30011000101.setChannelType("CEBA");
		// 清算日期
		reqBody_30011000101.setSettlementDate(reqDto.getSysDate().toString());
		// 对账标识,Y-参与对账;N-不参与对账
		reqBody_30011000101.setCollateFlag("Y");
		ESB_REP_30011000101 esbRep_30011000101 = forwardToESBService.sendToESB(esbReq_30011000101, reqBody_30011000101,
				ESB_REP_30011000101.class);
		return esbRep_30011000101;
	}
	/** 
	* @Title: getErrorList 
	* @Description: 获取redis里错误码信息集合
	* @param @return    设定文件 
	* @return ErrorList    返回类型 
	* @throws 
	*/
	private ErrorList getErrorList() {
		String jsonStrErrorList = null;
		try(Jedis jedis = myJedis.connect()){
			jsonStrErrorList = jedis.get(COMMON_PREFIX+"ceba_error_list");
        }
		if(jsonStrErrorList==null||jsonStrErrorList.length()==0){
			logger.error("渠道未配置["+COMMON_PREFIX + "ceba_error_list"+"]");
			throw new RuntimeException("渠道未配置["+COMMON_PREFIX + "ceba_error_list"+"]");
		}
		return JsonUtil.toBean(jsonStrErrorList, ErrorList.class);
	}
	/** 
	* @Title: cebaCharge 
	* @Description: 光大银行核心记账
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_BJCEBBCRes    返回类型 
	* @throws 
	*/
	private REP_BJCEBBCRes cebaCharge(REQ_30062001001 reqDto) throws SysTradeExecuteException {
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
		tin.setBillNo(reqBody.getPltfrmSeqNo());
		tin.setPayDate(reqBody.getPayDate());
		tin.setCustomerName(reqBody.getClientNnae());
		tin.setPayAccount(reqBody.getPayAcctNo());
		tin.setPin(reqBody.getPassword());
		tin.setPayAmount(reqBody.getUnpaidAmt());
		tin.setAcType(reqBody.getAcctType());
		tin.setContractNo(reqBody.getContractNo());
		REP_BJCEBBCRes res = forwardToCebaService.sendToCeba(req, REP_BJCEBBCRes.class);
		return res;
	}

	/** 
	* @Title: queryCebaResult 
	* @Description: 查询缴费单销账结果
	* @param @param reqDto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return REP_BJCEBBRQRes    返回类型 
	* @throws 
	*/
	private REP_BJCEBBRQRes queryCebaResult(REQ_30062001001 reqDto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30062001001.REQ_BODY reqBody = reqDto.getReqBody();
		REQ_BJCEBBRQReq req = new REQ_BJCEBBRQReq(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		String instld = null;
		try (Jedis jedis = myJedis.connect()) {
			instld = jedis.get(COMMON_PREFIX + "ceba_instld");
		}
		req.getHead().setInstId(instld);
		req.getHead().setAnsTranCode("BJCEBBRQReq");
		req.getHead().setTrmSeqNum("2010051000013010");
		REQ_BJCEBBRQReq.Tin tin = req.getTin();
		tin.setBillNo(reqBody.getPltfrmSeqNo());
		tin.setPayDate(reqBody.getPayDate());
		REP_BJCEBBRQRes res = forwardToCebaService.sendToCeba(req, REP_BJCEBBRQRes.class);
		return res;
	}
	/** 
	* @Title: initBillCharge 
	* @Description: 登记缴费单销账记录 
	* @param @param reqDto
	* @param @param hostDate
	* @param @param hostTraceNo
	* @param @param hostRetCode
	* @param @param hostRetMsg
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return BillChargeLogModel    返回类型 
	* @throws 
	*/
	private BillChargeLogModel initBillCharge(REQ_30062001001 reqDto,String hostDate,String hostTraceNo,
			String hostRetCode,String hostRetMsg) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		// 交易机构
		String txBrno = null;
		try (Jedis jedis = myJedis.connect()) {
			txBrno = jedis.get(COMMON_PREFIX + "ceba_txbrno");
		}
		REQ_30062001001.REQ_BODY reqBody = reqDto.getReqBody();
		BillChargeLogModel record = new BillChargeLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		record.setTxBranch(txBrno);
		record.setSourceType(reqDto.getSourceType());
        record.setBillKey(reqBody.getBillKey());
        record.setCompanyId(reqBody.getPyCityCode()+reqBody.getPyCreditNo());
        record.setCustomerName(reqBody.getClientNnae());
        record.setPayAccount(reqBody.getPayAcctNo());
        record.setPayAmount(new BigDecimal(reqBody.getUnpaidAmt()));
        record.setAcType(reqBody.getAcctType());
        record.setContractNo(reqBody.getContractNo());
        record.setBillNo(reqBody.getPltfrmSeqNo());
        record.setPayDate(Integer.parseInt(reqBody.getPayDate()));
        record.setHostDate(Integer.parseInt(hostDate));
        record.setHostTraceNo(hostTraceNo);
        record.setHostRetCode(hostRetCode);
        record.setHostRetMsg(hostRetMsg);
		billChargeLogService.logInit(record);
		return record;
	}
	/** 
	* @Title: hostReversal 
	* @Description: 核心冲正
	* @param @param reqDto
	* @param @param hostSeqno
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ESB_REP_30014000101    返回类型 
	* @throws 
	*/
	private ESB_REP_30014000101 hostReversal(REQ_30062001001 reqDto,String hostSeqno)
			throws SysTradeExecuteException {
		MyLog myLog = logPool.get();

		// 交易机构
		String txBrno = reqDto.getReqSysHead().getBranchId();
		// 柜员号
		String txTel = reqDto.getReqSysHead().getUserId();

		ESB_REQ_30014000101 esbReq_30014000101 = new ESB_REQ_30014000101(myLog, reqDto.getSysDate(),
				reqDto.getSysTime(), reqDto.getSysTraceno());
		ESB_REQ_SYS_HEAD reqSysHead = new EsbReqHeaderBuilder(esbReq_30014000101.getReqSysHead(), reqDto)
				.setBranchId(txBrno).setUserId(txTel).build();
		reqSysHead.setProgramId(reqDto.getReqSysHead().getProgramId());
		esbReq_30014000101.setReqSysHead(reqSysHead);

		ESB_REQ_30014000101.REQ_BODY reqBody_30014000101 = esbReq_30014000101.getReqBody();
		esbReq_30014000101.setReqSysHead(reqSysHead);	

		reqBody_30014000101.setReference(hostSeqno);
		reqBody_30014000101.setReversalReason("");
		reqBody_30014000101.setEventType("");
		

		ESB_REP_30014000101 esbRep_30014000101 = forwardToESBService.sendToESB(esbReq_30014000101, reqBody_30014000101,
				ESB_REP_30014000101.class);
		return esbRep_30014000101;
	}

}
