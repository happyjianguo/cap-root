package com.fxbank.cap.ceba.service;

import java.util.List;
import javax.validation.Valid;

import com.fxbank.cap.ceba.model.CebaCheckLogInitModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: IDayCheckLogService 
* @Description: 对账 
* @author Duzhenduo
* @date 2019年1月31日 上午10:11:23 
*  
*/
public interface ICebaCheckLogService {

	/** 
	* @Title: dayCheckLogInit 
	* @Description: 对账登记 
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void cebaCheckLogInit(@Valid CebaCheckLogInitModel model) throws SysTradeExecuteException; 
	
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
	List<CebaCheckLogInitModel> getCebaCheckLog(MyLog myLog,Integer sysTime, Integer sysTraceno,Integer platDate) throws SysTradeExecuteException;

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
