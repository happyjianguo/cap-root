package com.fxbank.cap.ceba.service;

import javax.validation.Valid;
import com.fxbank.cap.ceba.model.BillChargeLogModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

/** 
* @ClassName: IBillChargeLogService 
* @Description: 缴费单销账日志服务 
* @作者 杜振铎
* @date 2019年5月14日 下午2:10:32 
*  
*/
public interface IBillChargeLogService {

	/** 
	 * 缴费单销账登记
	* @Title: billChargeLogInit 
	* @Description: 缴费单销账登记
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void logInit(@Valid BillChargeLogModel record) throws SysTradeExecuteException;
	
	/**
	 * 缴费单销账更新日志 
	* @Title: logUpd 
	* @Description: 缴费单销账更新日志
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void logUpd(@Valid BillChargeLogModel record) throws SysTradeExecuteException;
	
	
}
