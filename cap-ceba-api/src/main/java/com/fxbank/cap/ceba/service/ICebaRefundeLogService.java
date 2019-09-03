package com.fxbank.cap.ceba.service;

import java.util.List;
import com.fxbank.cap.ceba.model.CebaRefundeLogModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: ICebaRefundeLogService 
* @Description: 退款日志服务 
* @作者 杜振铎
* @date 2019年5月14日 下午2:10:32 
*  
*/
public interface ICebaRefundeLogService {

    void initRefundeLog(MyLog myLog,CebaRefundeLogModel model) throws SysTradeExecuteException;
    
    List<CebaRefundeLogModel> queryRefundeLog(MyLog myLog,Integer platDate) throws SysTradeExecuteException;

    void updateRefundeLog(MyLog myLog,CebaRefundeLogModel model) throws SysTradeExecuteException;

    Boolean isInitRefundeLog(MyLog myLog,Integer platDate) throws SysTradeExecuteException;
}
