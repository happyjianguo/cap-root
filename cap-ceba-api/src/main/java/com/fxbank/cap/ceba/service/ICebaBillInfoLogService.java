package com.fxbank.cap.ceba.service;

import javax.validation.Valid;

import com.fxbank.cap.ceba.model.CebaBillInfoLogModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: ICebaBillInfoLogService 
* @Description: 查询缴费单信息日志服务 
* @作者 杜振铎
* @date 2019年5月14日 下午2:10:32 
*  
*/
public interface ICebaBillInfoLogService {


	void querySuccessInit(@Valid CebaBillInfoLogModel record) throws SysTradeExecuteException;
	
	CebaBillInfoLogModel getBillInfoBySeqNo(MyLog myLog,String seqNo) throws SysTradeExecuteException;
	
	
}
