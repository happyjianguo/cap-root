package com.fxbank.cap.ceba.service.impl;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.model.REP_BASE;
import com.fxbank.cap.ceba.model.REP_ERROR;
import com.fxbank.cap.ceba.model.REQ_BASE2;
import com.fxbank.cap.ceba.netty.ceba.CebaClient;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: ForwardToCebaService 
* @Description: 光大银行接口调用 
* @作者 杜振铎
* @date 2019年5月7日 下午5:13:45 
*  
*/
@Service(version = "1.0.0")
public class ForwardToCebaService implements IForwardToCebaService {

	private static Logger logger = LoggerFactory.getLogger(ForwardToCebaService.class);

	private static final String ERROR = "Error";
	
	@Resource
	private CebaClient cebaClient;

	@Override
	public <T extends REP_BASE> T sendToCeba(REQ_BASE2 reqBase, Class<T> clazz) throws SysTradeExecuteException {
		MyLog myLog = reqBase.getMylog();
		T repModel = null;
		try {
			repModel = cebaClient.comm(myLog, reqBase, clazz);
		} catch (SysTradeExecuteException e) {
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg(), e);
			throw e;
		} catch (Exception e) {
			SysTradeExecuteException e1 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999,
					e.getMessage());
			myLog.error(logger, e1.getRspCode() + " | " + e1.getRspMsg(), e);
			throw e1;
		}
		REP_BASE repBase = repModel;
		if (repBase == null) {
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg(), e);
			throw e;
		} else {
			String rspCode = repBase.getHead().getAnsTranCode();
			if (ERROR.equals(rspCode)) { 
				REP_ERROR repError = (REP_ERROR)repBase;
				String errorCode = repError.getTout().getErrorCode();
				SysTradeExecuteException e = new SysTradeExecuteException(errorCode);
				myLog.error(logger, e.getRspCode());
				throw e;
			}
		}
		return repModel;
	}
}
