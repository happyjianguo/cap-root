package com.fxbank.cap.ceba.trade.esb;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.esb.REP_30042000903;
import com.fxbank.cap.ceba.dto.esb.REQ_30042000903;
import com.fxbank.cap.ceba.model.REP_BJCEBBRQRes;
import com.fxbank.cap.ceba.model.REQ_BJCEBBRQReq;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;



/** 
* @ClassName: QR_BillResult 
* @Description: 销账结果查询 
* @作者 杜振铎
* @date 2019年5月10日 下午4:47:55 
*  
*/
@Service("REQ_30042000903")
public class QR_BillResult extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(QR_BillResult.class);

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
		REQ_30042000903 reqDto = (REQ_30042000903) dto;
		REQ_30042000903.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30042000903 rep = new REP_30042000903();		
		REQ_BJCEBBRQReq req = new REQ_BJCEBBRQReq(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		req.getHead().setInstId("100000000000001");
		req.getHead().setAnsTranCode("BJCEBBRQReq");
		req.getHead().setTrmSeqNum("2010051000013010");
		REQ_BJCEBBRQReq.Tin tin = req.getTin();
		tin.setBillNo(reqBody.getBillNo());
		tin.setPayDate(reqBody.getPayDate());
		REP_BJCEBBRQRes res = forwardToCebaService.sendToCeba(req, 
				REP_BJCEBBRQRes.class);
		REP_BJCEBBRQRes.Tout tout = res.getTout();
		REP_30042000903.REP_BODY repBody = rep.getRepBody();
		repBody.setBillKey(tout.getBillKey());
		repBody.setBankBillNo(tout.getBankBillNo());
		repBody.setPayAmount(tout.getPayAmount());
		repBody.setPayState(tout.getPayState());
		return rep;
	}
}
