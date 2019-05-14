package com.fxbank.cap.ceba.trade.esb;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.esb.REP_30063001402;
import com.fxbank.cap.ceba.dto.esb.REQ_30063001402;
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
import com.fxbank.cip.pub.service.IPublicService;

import redis.clients.jedis.Jedis;



/** 
* @ClassName: QR_BillResult 
* @Description: 销账结果查询 
* @作者 杜振铎
* @date 2019年5月10日 下午4:47:55 
*  
*/
@Service("REQ_30063001402")
public class QR_BillResult extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(QR_BillResult.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToCebaService forwardToCebaService;
	
	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30063001402 reqDto = (REQ_30063001402) dto;
		REQ_30063001402.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30063001402 rep = new REP_30063001402();		
		REQ_BJCEBBRQReq req = new REQ_BJCEBBRQReq(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		String instld = null;
		try (Jedis jedis = myJedis.connect()) {
			instld = jedis.get(COMMON_PREFIX + "ceba_instld");
		}
		req.getHead().setInstId(instld);
		req.getHead().setAnsTranCode("BJCEBBRQReq");
		req.getHead().setTrmSeqNum(publicService.getSysDate("CIP").toString()+publicService.getSysTraceno());
		REQ_BJCEBBRQReq.Tin tin = req.getTin();
		tin.setBillNo(reqBody.getPltfrmSeqNo());
		tin.setPayDate(reqBody.getPayDate());
		REP_BJCEBBRQRes res = forwardToCebaService.sendToCeba(req, 
				REP_BJCEBBRQRes.class);
		REP_BJCEBBRQRes.Tout tout = res.getTout();
		REP_30063001402.REP_BODY repBody = rep.getRepBody();
		repBody.setBillKey(tout.getBillKey());
		repBody.setPltfSeqNo(tout.getBankBillNo());
		repBody.setUnpaidAmt(tout.getPayAmount());
		repBody.setDealStatus(tout.getPayState());
		myLog.info(logger, "查询缴费单销账结果成功，渠道日期"+reqDto.getSysDate()+"渠道流水号"+reqDto.getSysTraceno());
		return rep;
	}
}
