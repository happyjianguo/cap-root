package com.fxbank.cap.ceba.service;

import java.util.List;

import com.fxbank.cap.ceba.model.CheckErrorModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: ICheckErrorService 
* @Description: 对账问题数据
* @author Duzhenduo
* @date 2019年1月31日 上午10:11:23 
*  
*/
public interface ICheckErrorService {

	List<CheckErrorModel> getListByDate(MyLog myLog,Integer sysTime, Integer sysDate,Integer sysTraceno, String date)throws SysTradeExecuteException;
	
	void insert(CheckErrorModel model)throws SysTradeExecuteException;
	
	void delete(String date)throws SysTradeExecuteException;
}
