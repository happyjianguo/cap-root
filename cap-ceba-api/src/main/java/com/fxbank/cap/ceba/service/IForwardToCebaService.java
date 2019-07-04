package com.fxbank.cap.ceba.service;

import com.fxbank.cap.ceba.model.MODEL_BASE;
import com.fxbank.cap.ceba.model.REP_BASE;
import com.fxbank.cap.ceba.model.REQ_BASE2;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

/** 
* @ClassName: IForwardToCebaService 
* @Description: 光大银行接口调用
* @作者 杜振铎
* @date 2019年5月7日 下午5:08:42 
*  
*/
public interface IForwardToCebaService {
	
	/** 
	 * 接口调用
	* @Title: sendToCeba 
	* @Description: 光大银行接口调用 
	* @param @param reqBase
	* @param @param clazz
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return T    返回类型 
	* @throws 
	*/
	public MODEL_BASE sendToCeba(MODEL_BASE reqBase) throws SysTradeExecuteException; 
	

}
