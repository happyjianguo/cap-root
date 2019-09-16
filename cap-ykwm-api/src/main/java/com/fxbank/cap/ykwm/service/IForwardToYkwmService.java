package com.fxbank.cap.ykwm.service;

import com.fxbank.cap.ykwm.model.REP_BASE;
import com.fxbank.cap.ykwm.model.REQ_BASE;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

/** 
* @ClassName: IForwardToYkwmService 
* @Description: 营口热电接口服务
* @作者 杜振铎
* @date 2019年4月29日 下午3:04:56 
*  
*/
public interface IForwardToYkwmService {
	
	/** 
	 * 请求营口热电接口
	* @Title: sendToYkwm 
	* @Description: 请求营口热电接口
	* @param @param reqBase
	* @param @param clazz
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return T    返回类型 
	* @throws 
	*/
	public <T extends REP_BASE> T sendToYkwm(REQ_BASE reqBase, Class<T> clazz) throws SysTradeExecuteException; 

}
