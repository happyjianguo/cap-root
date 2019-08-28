package com.fxbank.cap.ceba.trade.esb;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.esb.REP_30063001402;
import com.fxbank.cap.ceba.dto.esb.REQ_30063001402;
import com.fxbank.cap.ceba.exception.CebaTradeExecuteException;
import com.fxbank.cap.ceba.model.CebaChargeLogModel;
import com.fxbank.cap.ceba.model.REP_BJCEBBRQRes;
import com.fxbank.cap.ceba.model.REQ_BJCEBBRQReq;
import com.fxbank.cap.ceba.service.ICebaChargeLogService;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
	private ICebaChargeLogService cebaChargeLogService;

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
		req.getHead().setTrmSeqNum(dto.getSysDate().toString()+dto.getSysTraceno());
		CebaChargeLogModel logModel = cebaChargeLogService.queryLogBySeqNo(myLog, reqBody.getChannelSeqNo());
		if(logModel==null) {
			CebaTradeExecuteException e = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10005);
			throw e;
		}
		REP_30063001402.REP_BODY repBody = rep.getRepBody();
		//查询光大银行订单状态
        REQ_BJCEBBRQReq.Tin tin = req.getTin();
		tin.setBillNo(logModel.getSysTraceno().toString());
		tin.setPayDate(logModel.getSysDate().toString()+logModel.getSysTime().toString());
		REP_BJCEBBRQRes res = (REP_BJCEBBRQRes) forwardToCebaService.sendToCeba(req);
	
		REP_BJCEBBRQRes.Tout tout = res.getTout();
			repBody.setBillKey(tout.getBillKey());
			//渠道日期+渠道流水号
			repBody.setPltfSeqNo(logModel.getSysDate().toString()+logModel.getSysTraceno().toString());
			repBody.setUnpaidAmt(logModel.getPayAmount().toString());
			repBody.setDealStatus(tout.getPayState());

		myLog.info(logger, "销账结果查询成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
		return rep;
	}
}
