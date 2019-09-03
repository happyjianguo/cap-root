package com.fxbank.cap.ceba.service;

import java.math.BigDecimal;
import java.util.List;
import com.fxbank.cap.ceba.model.CebaSettleLogModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: ICebaSettleLogService 
* @Description: 缴费清算日志服务 
* @作者 杜振铎
* @date 2019年5月14日 下午2:10:32 
*  
*/
public interface ICebaSettleLogService {

    void initSettleLog(MyLog myLog,Integer chkDate,BigDecimal txAmt) throws SysTradeExecuteException;
	List<CebaSettleLogModel> querySettleLog(MyLog myLog,Integer chkDate) throws SysTradeExecuteException;
	void updateSettleTxSts(MyLog myLog,Integer chkDate,String txSts) throws SysTradeExecuteException;
	void updateTranComplete(MyLog myLog,Integer chkDate,String cnapsCode,String cnapsMsg,Integer cnapsDate,String cnapsTraceno) throws SysTradeExecuteException;
    Boolean isSettleSucc(MyLog myLog,Integer chkDate) throws SysTradeExecuteException;
    CebaSettleLogModel querySettleLogByPk(MyLog myLog,Integer chkDate) throws SysTradeExecuteException;
}
