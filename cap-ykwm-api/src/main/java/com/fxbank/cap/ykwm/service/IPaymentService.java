package com.fxbank.cap.ykwm.service;

import javax.validation.Valid;
import com.fxbank.cap.ykwm.model.YkwmTraceLogModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

/**
 * @ClassName: IPaymentService<br>
 * @Description: 活动平台支付渠道服务<br>
 * @author ly<br>
 * @date 2019年5月8日<br>
 */
public interface IPaymentService {

	void hostSuccessInit(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException;

	void hostTimeoutInit(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException;

	void othSuccessUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException;

	void othTimeoutUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException;

	void othErrorUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException;

	void othTimeoutSuccUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException;

	void hostUndoSuccess(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException;

	void hostUndoTimeout(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException;

	void hostUndoError(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException;
	
	void othUndoSuccUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException;
	
	void othUndoTimeoutUpdate(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException;
	
	YkwmTraceLogModel queryLogBySeqNo(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException;

}