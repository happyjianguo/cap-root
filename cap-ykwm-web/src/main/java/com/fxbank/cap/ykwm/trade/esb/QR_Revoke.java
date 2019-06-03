package com.fxbank.cap.ykwm.trade.esb;

import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.esb.REP_30063812301;
import com.fxbank.cap.ykwm.dto.esb.REQ_30063812301;
import com.fxbank.cap.ykwm.model.YkwmTraceLogModel;
import com.fxbank.cap.ykwm.service.IForwardToYkwmService;
import com.fxbank.cap.ykwm.service.IPaymentService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service("REQ_30063812301")
public class QR_Revoke extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(QR_Revoke.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToYkwmService forwardToYkwmService;
	
	@Reference(version = "1.0.0")
	IPaymentService iPaymentService; 

	@Resource
	private MyJedis myJedis;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30063812301 reqDto = (REQ_30063812301) dto;
		REQ_30063812301.REQ_BODY reqBody = reqDto.getReqBody();

		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, Integer.parseInt(reqBody.getPltfrmDateT1()), reqDto.getSysTime(),
				Integer.parseInt(reqBody.getPltfrmSeqT1()));
		record = iPaymentService.queryLogBySeqNo(record);
		
		REP_30063812301 rep = new REP_30063812301();
		rep.getRepBody().setPyFeeAmtT(record.getPyFeeAmtT());
		rep.getRepBody().setCoreDtT2(record.getCoDate());
		rep.getRepBody().setCoreSeqT2(record.getCoTransactionno());
		rep.getRepBody().setHeatSeqNoT(record.getTeCheckNum());
		rep.getRepBody().setPyFeeCardNoT(record.getAcctNoT());
		
		return rep;
	}

}
