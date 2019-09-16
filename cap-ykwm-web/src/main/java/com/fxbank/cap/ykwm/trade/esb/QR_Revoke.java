package com.fxbank.cap.ykwm.trade.esb;

import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.esb.REP_30063001701;
import com.fxbank.cap.ykwm.dto.esb.REQ_30063001701;
import com.fxbank.cap.ykwm.exception.YkwmTradeExecuteException;
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



@Service("REQ_30063001701")
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
		REQ_30063001701 reqDto = (REQ_30063001701) dto;
		REQ_30063001701.REQ_BODY reqBody = reqDto.getReqBody();

		YkwmTraceLogModel record = new YkwmTraceLogModel(myLog, Integer.parseInt(reqBody.getChannelDate()), reqDto.getSysTime(),
				Integer.parseInt(reqBody.getChannelSeqNo()));
		record = iPaymentService.queryLogBySeqNo(record);
		
		if(null == record) {
			myLog.error(logger, "冲正快查交易查询失败，渠道日期"+reqDto.getSysDate()+"渠道流水号"+reqDto.getSysTraceno());
			throw new YkwmTradeExecuteException(YkwmTradeExecuteException.YKWM_E_10004);
		}
		REP_30063001701 rep = new REP_30063001701();
		rep.getRepBody().setPyFeeAmtT(record.getPyFeeAmtT());
		rep.getRepBody().setHostDate(record.getCoDate());
		rep.getRepBody().setHostTraceNo(record.getCoTransactionno());
		rep.getRepBody().setChannelRefNo(record.getTeCheckNum());
		rep.getRepBody().setPyFeeCardNoT(record.getAcctNoT());
		
		return rep;
	}

}
