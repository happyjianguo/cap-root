package com.fxbank.cap.ceba.trade.ceba;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.ceba.REP_BJCEBBCRes;
import com.fxbank.cap.ceba.dto.ceba.REP_ERROR;
import com.fxbank.cap.ceba.dto.ceba.REQ_BJCEBBCReq;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;



/** 
* @ClassName: BJCEBBCReq 
* @Description: 模拟光大银行缴费单销账
* @作者 杜振铎
* @date 2019年5月10日 下午4:05:48 
*  
*/
@Service("BJCEBBCReq")
public class BJCEBBCReq implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(BJCEBBCReq.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";
	
	private static final String ERR_BILLKEY = "12345";
	
	private static final String TIMEOUT_BILLKEY = "12346";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_BJCEBBCReq req = (REQ_BJCEBBCReq) dto;
		
		
		REP_BJCEBBCRes rep = new REP_BJCEBBCRes();
		rep.getHead().setInstId(req.getHead().getInstId());
		rep.getHead().setAnsTranCode("BJCEBBCRes");
		rep.getHead().setTrmSeqNum(req.getHead().getTrmSeqNum());
		rep.getTout().setBillKey("123456");
		rep.getTout().setCompanyId("654321");
		rep.getTout().setBillNo(req.getTin().getBillNo());
		rep.getTout().setPayDate(req.getTin().getPayDate());
		rep.getTout().setPayAmount(req.getTin().getPayAmount());
		rep.getTout().setBankBillNo("123237202");
		rep.getTout().setAcctDate("20110512");
		
		return rep;
	}
	
	
}