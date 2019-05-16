package com.fxbank.cap.ceba.service;

import javax.validation.Valid;
import com.fxbank.cap.ceba.model.CebaChargeLogModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: ICebaChargeLogService 
* @Description: 缴费单销账日志服务 
* @作者 杜振铎
* @date 2019年5月14日 下午2:10:32 
*  
*/
public interface ICebaChargeLogService {

	/** 
	 * 缴费单销账登记
	* @Title: billChargeLogInit 
	* @Description: 缴费单销账登记
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void logInit(@Valid CebaChargeLogModel record) throws SysTradeExecuteException;
	
	/**
	 * 缴费单销账更新日志 
	* @Title: logUpd 
	* @Description: 缴费单销账更新日志
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void logUpd(@Valid CebaChargeLogModel record) throws SysTradeExecuteException;
	
	/** 
	 * 通过来源渠道号查询缴费单销账日志 
	* @Title: queryLogBySeqNo 
	* @Description: 通过来源渠道号查询缴费单销账日志 
	* @param @param myLog
	* @param @param seqNo
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return CebaChargeLogModel    返回类型 
	* @throws 
	*/
	CebaChargeLogModel queryLogBySeqNo(MyLog myLog,String seqNo) throws SysTradeExecuteException;
	
	
}
