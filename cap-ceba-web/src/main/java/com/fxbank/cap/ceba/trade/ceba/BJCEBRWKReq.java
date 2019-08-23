package com.fxbank.cap.ceba.trade.ceba;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.ceba.REQ_BJCEBRWKReq;
import com.fxbank.cap.ceba.model.REP_BJCEBRWKRes;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;




/** 
* @ClassName: BJCEBRWKReq 
* @Description: 模拟光大银行工作密钥申请
* @作者 杜振铎
* @date 2019年5月16日 下午4:47:47 
*  
*/
@Service("BJCEBRWKReq")
public class BJCEBRWKReq implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(BJCEBRWKReq.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";
	
	@Reference(version = "1.0.0")
	private IForwardToCebaService forwardToCebaService;
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_BJCEBRWKReq req = (REQ_BJCEBRWKReq) dto;
		
		REP_BJCEBRWKRes rep = new REP_BJCEBRWKRes(myLog,null,null,null);
		rep.getHead().setInstId(req.getHead().getInstId());
		rep.getHead().setAnsTranCode("BJCEBRWKRes");
		rep.getHead().setTrmSeqNum(req.getHead().getTrmSeqNum());
		rep.getTout().setPartnerCode(req.getTin().getPartnerCode());
		rep.getTout().setReturnCode("00");
		rep.getTout().setErrorDescription("");
		rep.getTout().setKeyName("ZPK");
		rep.getTout().setKeyValue("05BE38FE04E6C42A");
		rep.getTout().setVerifyValue("8618C4D2632796AA");
		rep.getTout().setKeyName1("ZAK");
		rep.getTout().setKeyValue1("5339766038244AAC");
		rep.getTout().setVerifyValue1("C4C15BDA9E7B31D9");
		
		forwardToCebaService.sendToFxb(rep);
		return req;
	}
	
	
}