package com.fxbank.cap.ceba.service;

import java.util.List;

import javax.validation.Valid;
import com.fxbank.cap.ceba.model.CebaBillCheckModel;
import com.fxbank.cip.base.exception.SysTradeExecuteException;


/** 
* @ClassName: ICebaBillCheckService 
* @Description: 对账文件导入 
* @作者 杜振铎
* @date 2019年9月24日 下午2:27:00 
*  
*/
public interface ICebaBillCheckService {


	void billCheckInit(@Valid CebaBillCheckModel record) throws SysTradeExecuteException;

	void billCheckUpdate(@Valid CebaBillCheckModel record) throws SysTradeExecuteException;
	
	List<CebaBillCheckModel> queryBillCheck() throws SysTradeExecuteException;
}
