package com.fxbank.cap.ykwm.trade.esb;

import javax.annotation.Resource;
import org.slf4j.Logger;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: BaseTradeT2 
* @Description:  2号交易模版。适用场景：第三方冲正->核心冲正,日终对账以银行为准。
* @作者 杜振铎
* @date 2019年5月24日 下午1:42:20 
*  
*/
public abstract class BaseTradeT2 {

	/** 
	* @Title: backMsg 
	* @Description: 返回渠道日期和渠道流水等
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return DataTransObject    返回类型 
	* @throws 
	*/
	public abstract DataTransObject backMsg(DataTransObject dto) throws SysTradeExecuteException;

	@Resource
	private LogPool logPool;

	public Logger logger = null;

	/**
	 * @Fields TRADE_DESC : 交易描述
	 */
	public String TRADE_DESC = "";

	/** 
	* @Title: undoOth 
	* @Description: 第三方冲正 
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ModelBase    返回类型 
	* @throws 
	*/
	public abstract void undoOth(DataTransObject dto, ModelBase model) throws SysTradeExecuteException;

	/** 
	* @Title: othTimeout 
	* @Description: 判断第三方冲正是否超时 
	* @param @param e
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return Boolean    返回类型 
	* @throws 
	*/
	public abstract Boolean othTimeout(SysTradeExecuteException e) throws SysTradeExecuteException;
	
	/** 
	* @Title: isOrigHostCharge 
	* @Description: 判断是当天冲正还是隔日冲正 
	* @param @param model
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return Boolean    返回类型 
	* @throws 
	*/
	public abstract Boolean isOrigHostCharge(DataTransObject dto) throws SysTradeExecuteException;

	/**
	 * @param model  
	* @Title: undoHostCharge 
	* @Description: 核心冲正
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ModelBase    返回类型 
	* @throws 
	*/
	public abstract ModelBase undoHostCharge(DataTransObject dto) throws SysTradeExecuteException;

	/** 
	* @Fields ESB_TIMEOUT_CODE : 核心超时ESB响应码
	*/ 
	private static final String ESB_TIMEOUT_CODE1 = "ESB_E_000052";
	
	/** 
	* @Fields ESB_TIMEOUT_CODE2 :  核心超时ESB响应码
	*/ 
	private static final String ESB_TIMEOUT_CODE2 = "ES000033";
	
	/** 
	* @Title: updateHostUndoTimeout 
	* @Description: 核心冲正超时更新日志 
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void updateHostUndoTimeout(DataTransObject dto) throws SysTradeExecuteException;
	
	/** 
	* @Title: hostUndoTimeoutException 
	* @Description: 核心冲正超时提示错误
	* @param @param e
	* @param @return    设定文件 
	* @return SysTradeExecuteException    返回类型 
	* @throws 
	*/
	public SysTradeExecuteException hostUndoTimeoutException = null;
	
	/** 
	* @Title: updateHostUndoError 
	* @Description: 核心冲正失败更新日志
	* @param @param dto
	* @param @param e
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void updateHostUndoError(DataTransObject dto, SysTradeExecuteException e)
			throws SysTradeExecuteException;
	
	/** 
	* @Title: hostUndoErrorException 
	* @Description: 核心冲正失败提示错误
	* @param @param e
	* @param @return    设定文件 
	* @return SysTradeExecuteException    返回类型 
	* @throws 
	*/
	public abstract SysTradeExecuteException hostUndoErrorException(SysTradeExecuteException e);
	
	/** 
	* @Fields othTimeoutException : 第三方冲正超时提示错误
	*/ 
	public SysTradeExecuteException othTimeoutException = null;
	
	/** 
	* @Title: updateHostUndoSucc
	* @Description: 核心冲正成功更新日志
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void updateHostUndoSucc(DataTransObject dto, ModelBase model) throws SysTradeExecuteException;
	
	/** 
	* @Title: updateOthUndoSucc 
	* @Description: 更新第三方冲正成功
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void updateOthUndoSucc(DataTransObject dto) throws SysTradeExecuteException;
	
	/** 
	* @Title: updateOthUndoTimeout 
	* @Description: 更新第三方冲正超时
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void updateOthUndoTimeout(DataTransObject dto) throws SysTradeExecuteException;
	
	/** 
	* @Title: queryRecord 
	* @Description: 查询记账日志获取记录
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return Boolean    返回类型 
	* @throws 
	*/
	public abstract ModelBase queryRecord(DataTransObject dto) throws SysTradeExecuteException;
	
	/** 
	* @Fields notExistException : 待冲正信息不存在提示错误 
	*/ 
	public SysTradeExecuteException notExistException = null;
	
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		ModelBase model = null;
		MyLog myLog = logPool.get();
		//查询日志是否有该笔记录
		model = queryRecord(dto);
		if(model==null) {
			myLog.error(logger, TRADE_DESC + "待冲正信息不存在");
			throw notExistException;
		}
		try {
			// 第三方冲正
			undoOth(dto,model);
			myLog.info(logger, TRADE_DESC + "第三方冲正成功，渠道日期" + dto.getSysDate() + "渠道流水号" + dto.getSysTraceno());
		} catch (SysTradeExecuteException e) {
			// 第三方冲正超时
			if (othTimeout(e)) {
				//更新第三方冲正超时
				updateOthUndoTimeout(dto); 
				myLog.error(logger, TRADE_DESC + "第三方冲正超时，渠道日期" + dto.getSysDate() + "渠道流水号" + dto.getSysTraceno(), e);
			    throw othTimeoutException;
			} else {
				myLog.error(logger, TRADE_DESC + "第三方冲正失败，渠道日期" + dto.getSysDate() + "渠道流水号" + dto.getSysTraceno(), e);
                throw e;
			}
		}
		//更新第三方冲正成功
		updateOthUndoSucc(dto);
		try {
			model = undoHostCharge(dto);
		} catch (SysTradeExecuteException e) {
			//ESB超时
			if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000004) || e.getRspCode().equals(ESB_TIMEOUT_CODE1)
					|| e.getRspCode().equals(ESB_TIMEOUT_CODE2)) {
				updateHostUndoTimeout(dto);
				myLog.error(logger,TRADE_DESC+"核心冲正超时，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno());
				throw hostUndoTimeoutException; 
			} else {
				//核心冲正失败2
				updateHostUndoError(dto, e);
				myLog.error(logger,TRADE_DESC+"核心冲正失败，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno());
				throw hostUndoErrorException(e);
			}
		}
		
		//更新核心冲正成功
		updateHostUndoSucc(dto, model);
		myLog.info(logger,TRADE_DESC+"核心冲正成功，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno());
		return backMsg(dto);
	}
}