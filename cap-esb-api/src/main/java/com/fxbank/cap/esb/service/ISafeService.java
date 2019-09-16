package com.fxbank.cap.esb.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: ISafeService 
* @Description: 加密平台服务
* @author Duzhenduo
* @date 2019年1月31日 下午3:44:38 
*  
*/
public interface ISafeService {
	
	/** 
	* @Title: calcMac 
	* @Description: 计算柜面请求mac
	* @param @param myLog
	* @param @param dataToMAC
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String calcMac(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException;
}
