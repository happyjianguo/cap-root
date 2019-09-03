package com.fxbank.cap.ceba.service;

import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: IWorkKeyService 
* @Description: 更新和生成工作密钥
* @作者 杜振铎
* @date 2019年5月7日 下午5:08:42 
*  
*/
public interface IWorkKeyService {
	
	/** 
	* @Title: updateWorkKey 
	* @Description: 更新工作密钥 
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void updateWorkKey(MyLog myLog,String macValue,String macVerifyValue,String pinValue,String pinVerifyValue) throws SysTradeExecuteException; 
	
	/** 
	* @Title: genMac 
	* @Description: 生成MAC
	* @param @param pack
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String genMac(MyLog myLog,String pack) throws SysTradeExecuteException; 
	
}
