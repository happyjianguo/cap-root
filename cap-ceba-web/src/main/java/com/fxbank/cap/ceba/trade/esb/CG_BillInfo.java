package com.fxbank.cap.ceba.trade.esb;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.esb.REP_30042000902;
import com.fxbank.cap.ceba.dto.esb.REQ_30042000902;
import com.fxbank.cap.ceba.model.REP_BJCEBBCRes;
import com.fxbank.cap.ceba.model.REQ_BJCEBBCReq;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;



/** 
* @ClassName: CG_BillInfo 
* @Description: 缴费单销账 
* @作者 杜振铎
* @date 2019年5月10日 上午11:30:05 
*  
*/
@Service("REQ_30042000902")
public class CG_BillInfo extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(CG_BillInfo.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToCebaService forwardToCebaService;

	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30042000902 reqDto = (REQ_30042000902) dto;
		REQ_30042000902.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30042000902 rep = new REP_30042000902();		
		REQ_BJCEBBCReq req = new REQ_BJCEBBCReq(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		req.getHead().setInstId("100000000000001");
		req.getHead().setAnsTranCode("BJCEBBCReq");
		req.getHead().setTrmSeqNum("2010051000013010");
		REQ_BJCEBBCReq.Tin tin = req.getTin();
		tin.setBillKey(reqBody.getBillKey());
		tin.setCompanyId(reqBody.getCityCode()+reqBody.getProjCode());
		tin.setBillNo(reqBody.getBillNo());
		tin.setPayDate(reqBody.getPayDate());
		tin.setCustomerName(reqBody.getCustomerName());
		tin.setPayAccount(reqBody.getPayAccount());
		tin.setPin(reqBody.getPin());
		tin.setPayAmount(reqBody.getPayAmount());
		tin.setAcType(reqBody.getAcType());
		tin.setContractNo(reqBody.getContractNo());
		REP_BJCEBBCRes res = forwardToCebaService.sendToCeba(req, 
				REP_BJCEBBCRes.class);
		REP_BJCEBBCRes.Tout tout = res.getTout();
		rep.getRepBody().setBankBillNo(tout.getBankBillNo());
		rep.getRepBody().setReceiptNo(tout.getReceiptNo());
		rep.getRepBody().setAcctDate(tout.getAcctDate());
		return rep;
	}
}
