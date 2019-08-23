package com.fxbank.cap.ceba.service;

import java.util.List;
import javax.validation.Valid;
import com.fxbank.cap.ceba.model.HostCheckLogInitModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: IDayCheckLogService 
* @Description: 对账 
* @author Duzhenduo
* @date 2019年1月31日 上午10:11:23 
*  
*/
public interface IHostCheckLogService {

	/** 
	* @Title: hostCheckLogInit 
	* @Description: 核心对账登记 
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void hostCheckLogInit(@Valid HostCheckLogInitModel model) throws SysTradeExecuteException; 
	
	/** 
	* @Title: getDayCheckLog 
	* @Description: 查询对账日志 
	* @param @param myLog
	* @param @param sysTime
	* @param @param sysTraceno
	* @param @param platDate
	* @param @param direction
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return List<DayCheckLogInitModel>    返回类型 
	* @throws 
	*/
	List<HostCheckLogInitModel> getHostCheckLog(MyLog myLog,Integer platDate) throws SysTradeExecuteException;

	/** 
	* @Title: delete 
	* @Description: 删除对账日志
	* @param @param direction
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void delete(String platDate)throws SysTradeExecuteException; 

}
