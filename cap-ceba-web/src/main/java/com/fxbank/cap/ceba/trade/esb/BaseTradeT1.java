package com.fxbank.cap.ceba.trade.esb;

import javax.annotation.Resource;

import org.slf4j.Logger;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/**
 * @Description: 1号交易模版。适用场景：核心记账->第三方记账->[冲正核心],日终对账以银行为准。
 * @Author: 周勇沩
 * @Date: 2019-05-20 22:53:50
 */
public abstract class BaseTradeT1 {

	/**
	 * @Description: 核心记账
	 * @Author: 周勇沩
	 * @Date: 2019-05-20 22:55:25
	 */
	public abstract ModelBase hostCharge(DataTransObject dto) throws SysTradeExecuteException;

	/** 
	* @Title: hostTimeoutInitLog 
	* @Description: 核心记账超时登记 
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void hostTimeoutInitLog(DataTransObject dto) throws SysTradeExecuteException;

	/** 
	* @Title: hostSuccessInitLog 
	* @Description: 核心记账成功登记 
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void hostSuccessInitLog(DataTransObject dto, ModelBase model) throws SysTradeExecuteException;

	/** 
	* @Title: othCharge 
	* @Description: 第三方记账 
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ModelBase    返回类型 
	* @throws 
	*/
	public abstract ModelBase othCharge(DataTransObject dto) throws SysTradeExecuteException;
	
	/** 
	* @Title: queryOth 
	* @Description: 第三方记账超时，查询该笔交易
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ModelBase    返回类型 
	* @throws 
	*/
	public abstract void queryOth(DataTransObject dto) throws SysTradeExecuteException;

	/** 
	* @Title: othTimeout 
	* @Description: 根据错误码判断第三方记账是否超时
	* @param @param e
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return Boolean    返回类型 
	* @throws 
	*/
	public abstract Boolean othTimeout(SysTradeExecuteException e) throws SysTradeExecuteException;

	/** 
	* @Title: othSuccess 
	* @Description: 第三方记账超时，再次发起记账请求，根据错误码判断第三方记账是否成功
	* @param @param e
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return Boolean    返回类型 
	* @throws 
	*/
	public abstract Boolean othSuccess(SysTradeExecuteException e) throws SysTradeExecuteException;

	/** 
	* @Title: othErrorUndoHost 
	* @Description: 第三方记账失败，根据错误码判断是否需要核心冲正
	* @param @param e
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return Boolean    返回类型 
	* @throws 
	*/
	public abstract Boolean othErrorUndoHost(SysTradeExecuteException e) throws SysTradeExecuteException;

	/** 
	* @Title: undoHostCharge 
	* @Description: 核心冲正
	* @param @param dto
	* @param @param e
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return ModelBase    返回类型 
	* @throws 
	*/
	public abstract ModelBase undoHostCharge(DataTransObject dto, SysTradeExecuteException e)
			throws SysTradeExecuteException;

	/** 
	* @Title: updateOthTimeout 
	* @Description: 第三方记账超时更新日志
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void updateOthTimeout(DataTransObject dto) throws SysTradeExecuteException;

	/** 
	* @Title: updateOthSuccess 
	* @Description: 第三方记账成功更新日志 
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void updateOthSuccess(DataTransObject dto, ModelBase model) throws SysTradeExecuteException;

	/** 
	* @Title: updateHostUndoSuccess 
	* @Description: 核心冲正成功更新日志
	* @param @param dto
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void updateHostUndoSuccess(DataTransObject dto, ModelBase model) throws SysTradeExecuteException;

	/** 
	* @Title: updateOthError 
	* @Description: 第三方记账失败更新日志 
	* @param @param dto
	* @param @param e
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void updateOthError(DataTransObject dto, SysTradeExecuteException e)
			throws SysTradeExecuteException;

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
	* @Title: updateOthTimeoutSucc 
	* @Description: 第三方记账超时，再次发起记账请求，记账成功，更新日志 
	* @param @param dto
	* @param @throws SysTradeExecuteException    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public abstract void updateOthTimeoutSucc(DataTransObject dto) throws SysTradeExecuteException;

	/** 
	* @Title: backMsg 
	* @Description: 返回渠道日期和渠道流水号等
	* @param @param dto
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @return DataTransObject    返回类型 
	* @throws 
	*/
	public abstract DataTransObject backMsg(DataTransObject dto,ModelBase model) throws SysTradeExecuteException;

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
	* @Title: othErrorException 
	* @Description: 第三方记账失败，提示失败信息
	* @param @param e
	* @param @return    设定文件 
	* @return SysTradeExecuteException    返回类型 
	* @throws 
	*/
	public abstract SysTradeExecuteException othErrorException(SysTradeExecuteException e);

	/** 
	* @Title: hostUndoSuccessException 
	* @Description: 核心冲正成功，提示第三方记账失败错误信息（退款成功）
	* @param @param e
	* @param @return    设定文件 
	* @return SysTradeExecuteException    返回类型 
	* @throws 
	*/
	public abstract SysTradeExecuteException hostUndoSuccessException(SysTradeExecuteException e);

	/** 
	* @Title: hostUndoTimeoutException 
	* @Description: 核心冲正超时，提示第三方记账失败错误信息（退款成功）
	* @param @param e
	* @param @return    设定文件 
	* @return SysTradeExecuteException    返回类型 
	* @throws 
	*/
	public abstract SysTradeExecuteException hostUndoTimeoutException(SysTradeExecuteException e);

	/** 
	* @Title: hostUndoErrorException 
	* @Description: 核心冲正失败，提示第三方记账失败错误信息（核心冲正错误信息，退款失败） 
	* @param @param e
	* @param @param e1
	* @param @return    设定文件 
	* @return SysTradeExecuteException    返回类型 
	* @throws 
	*/
	public abstract SysTradeExecuteException hostUndoErrorException(SysTradeExecuteException e,
			SysTradeExecuteException e1);
	
	/** 
	* @Fields hostTimeoutException : 核心记账超时，提示处理失败
	*/ 
	public SysTradeExecuteException hostTimeoutException = null;

	/** 
	* @Fields othTimeoutException : 第三方记账超时，提示第三方记账超时
	*/ 
	public SysTradeExecuteException othTimeoutException = null;
	
	/** 
	* @Fields TRADE_DESC : 交易描述
	*/ 
	public String TRADE_DESC = "";
	
	public Logger logger = null;
	
	/** 
	* @Fields ESB_TIMEOUT_CODE : 核心超时ESB响应码
	*/ 
	private static final String ESB_TIMEOUT_CODE1 = "ESB_E_000052";
	
	/** 
	* @Fields othTimeoutQuery : 第三方记账超时，是否发起查询该笔交易 
	*/ 
	public Boolean othTimeoutQuery = false;
	
	@Resource
	private LogPool logPool;

	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		ModelBase model = null;
		MyLog myLog = logPool.get();
		try {
			//核心记账
			model = hostCharge(dto);
			myLog.info(logger,TRADE_DESC+"核心记账成功，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno());
		} catch (SysTradeExecuteException e) {
			// 超时
			if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000004) || e.getRspCode().equals(ESB_TIMEOUT_CODE1)) {
				// 超时登记
				hostTimeoutInitLog(dto); 
				myLog.error(logger,TRADE_DESC+"核心记账超时，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
				throw hostTimeoutException;
			} else {
				myLog.error(logger,TRADE_DESC+"核心记账失败，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
				throw e;
			}
		}
		// 主机成功登记
		hostSuccessInitLog(dto, model); 
		ModelBase model1 = null;
		try {
			//第三方记账
			model1 = othCharge(dto);
		} catch (SysTradeExecuteException e) {
			if (othTimeout(e)) { 
				updateOthTimeout(dto);
				myLog.error(logger,TRADE_DESC+"第三方记账超时，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
				if(othTimeoutQuery) {
				try {
					//第三方记账超时，查询该笔交易 
					queryOth(dto);
				} catch (SysTradeExecuteException e1) {
					// 第三方超时当成成功处理
					if (othTimeout(e1)) {
						updateOthTimeoutSucc(dto);
						myLog.error(logger,TRADE_DESC+"记账超时，查询该笔交易超时，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e1);
					} else if (othSuccess(e1)) {
						//查询该笔交易，根据响应码判断是否成功
						updateOthTimeoutSucc(dto);
						myLog.info(logger,TRADE_DESC+"记账超时，查询该笔交易，提示客户无欠费，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno());
					} else {
						//查询该笔交易失败
						myLog.info(logger,TRADE_DESC+"记账超时，查询该笔交易失败，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno());
					    throw e;
					}
				}
				updateOthError(dto, e);
				myLog.error(logger,TRADE_DESC+"第三方记账失败，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
				if (othErrorUndoHost(e)) {
					try {
						//核心冲正
						model = undoHostCharge(dto, e);
					} catch (SysTradeExecuteException e2) {
						//ESB超时
						if (e.getRspCode().equals(SysTradeExecuteException.CIP_E_000004) || e.getRspCode().equals(ESB_TIMEOUT_CODE1)) {
							updateHostUndoTimeout(dto);
							myLog.error(logger,TRADE_DESC+"核心冲正超时，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e2);
							throw hostUndoTimeoutException(e); // 提示第三方错误信息
						} else {
							updateHostUndoError(dto, e2);
							myLog.error(logger,TRADE_DESC+"核心冲正失败，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e2);
							throw hostUndoErrorException(e, e2);
						}
					}
					updateHostUndoSuccess(dto, model);
					myLog.error(logger,TRADE_DESC+"核心冲正成功，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
					throw hostUndoSuccessException(e);
				} else {
					throw othErrorException(e);
				}
				}else {
					updateOthTimeoutSucc(dto);
					myLog.error(logger,TRADE_DESC+"第三方记账超时，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
				}
			} else {
				updateOthError(dto, e);
				myLog.error(logger,TRADE_DESC+"第三方记账失败，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e);
				if (othErrorUndoHost(e)) {
					try {
						//核心冲正
						model = undoHostCharge(dto, e);
					} catch (SysTradeExecuteException e1) {
						if (e1.getRspCode().equals(SysTradeExecuteException.CIP_E_000004) || e1.getRspCode().equals(ESB_TIMEOUT_CODE1)) {
							updateHostUndoTimeout(dto);
							myLog.error(logger,TRADE_DESC+"核心冲正超时，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e1);
							throw hostUndoTimeoutException(e); // 提示第三方错误信息
						} else {
							updateHostUndoError(dto, e1);
							myLog.error(logger,TRADE_DESC+"核心冲正失败，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno(),e1);
							throw hostUndoErrorException(e, e1);
						}
					}
					updateHostUndoSuccess(dto, model);
					myLog.info(logger,TRADE_DESC+"核心冲正成功，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno());
					throw hostUndoSuccessException(e);
				} else {
					throw othErrorException(e);
				}

			}
		}
		updateOthSuccess(dto, model1);
		myLog.info(logger,TRADE_DESC+"第三方记账成功，渠道日期"+dto.getSysDate()+"渠道流水号"+dto.getSysTraceno());
		return backMsg(dto,model1);
	}
}