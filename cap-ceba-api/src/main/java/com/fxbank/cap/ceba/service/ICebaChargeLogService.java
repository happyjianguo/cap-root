package com.fxbank.cap.ceba.service;

import javax.validation.Valid;
import com.fxbank.cap.ceba.model.CebaChargeLogModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: ICebaChargeLogService 
* @Description: 缴费单销账日志服务 
* @作者 杜振铎
* @date 2019年5月14日 下午2:10:32 
*  
*/
public interface ICebaChargeLogService {


	void hostSuccessInit(@Valid CebaChargeLogModel record) throws SysTradeExecuteException;
	
	void hostTimeoutInit(@Valid CebaChargeLogModel record) throws SysTradeExecuteException;
	
	void othSuccessUpdate(@Valid CebaChargeLogModel record) throws SysTradeExecuteException;
	
	void othTimeoutUpdate(@Valid CebaChargeLogModel record) throws SysTradeExecuteException;
	
	void othErrorUpdate(@Valid CebaChargeLogModel record) throws SysTradeExecuteException;
	
	void othTimeoutSuccUpdate(@Valid CebaChargeLogModel record) throws SysTradeExecuteException;
	
	void hostUndoSuccess(@Valid CebaChargeLogModel record) throws SysTradeExecuteException;
	
	void hostUndoTimeout(@Valid CebaChargeLogModel record) throws SysTradeExecuteException;
	
	void hostUndoError(@Valid CebaChargeLogModel record) throws SysTradeExecuteException;
	
	CebaChargeLogModel queryLogBySeqNo(MyLog myLog,String seqNo) throws SysTradeExecuteException;
	
	
}
