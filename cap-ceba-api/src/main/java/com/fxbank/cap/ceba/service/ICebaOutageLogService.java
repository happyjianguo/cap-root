package com.fxbank.cap.ceba.service;

import com.fxbank.cap.ceba.model.CebaOutageLogModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: ICebaOutageLogService 
* @Description: 停运通知流水日志服务 
* @作者 杜振铎
* @date 2019年5月14日 下午2:10:32 
*  
*/
public interface ICebaOutageLogService {

    void initOutageLog(MyLog myLog,CebaOutageLogModel model) throws SysTradeExecuteException;
	CebaOutageLogModel isOutageByCompanyId(MyLog myLog,String companyId,Long sysDate) throws SysTradeExecuteException;
}
