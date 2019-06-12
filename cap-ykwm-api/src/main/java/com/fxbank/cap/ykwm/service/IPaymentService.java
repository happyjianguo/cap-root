package com.fxbank.cap.ykwm.service;

import java.util.List;
import javax.validation.Valid;
import com.fxbank.cap.ykwm.model.YkwmTraceLogModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

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

	List<YkwmTraceLogModel> getCheckTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date) throws SysTradeExecuteException;
	
	void updateCheck(@Valid YkwmTraceLogModel record) throws SysTradeExecuteException;
	
	YkwmTraceLogModel queryLogByPk(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno) throws SysTradeExecuteException;
	
	String getTotalNum(String date) throws SysTradeExecuteException;
	
	String getTotalAmt(String date) throws SysTradeExecuteException;
	
	List<YkwmTraceLogModel> getUploadCheckSndTrace(MyLog myLog, Integer sysDate, Integer sysTime, Integer sysTraceno,
			String date) throws SysTradeExecuteException;
	
	String getTraceNum(String date, String capResult) throws SysTradeExecuteException;
	
}