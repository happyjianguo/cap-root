package com.fxbank.cap.ykwm.service;

import java.math.BigDecimal;
import java.util.List;
import com.fxbank.cap.ykwm.model.YkwmSettleLogModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

public interface IYkwmSettleLogService {

    void initSettleLog(MyLog myLog,Integer chkDate,BigDecimal txAmt) throws SysTradeExecuteException;
	List<YkwmSettleLogModel> querySettleLog(MyLog myLog,Integer chkDate) throws SysTradeExecuteException;
	void updateSettle(MyLog myLog,YkwmSettleLogModel model) throws SysTradeExecuteException;
    YkwmSettleLogModel querySettleLogByPk(MyLog myLog,Integer chkDate) throws SysTradeExecuteException;

}
