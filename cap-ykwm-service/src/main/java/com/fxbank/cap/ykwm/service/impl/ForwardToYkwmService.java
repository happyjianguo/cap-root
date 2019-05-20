package com.fxbank.cap.ykwm.service.impl;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ykwm.model.REP_BASE;
import com.fxbank.cap.ykwm.model.REP_ERROR;
import com.fxbank.cap.ykwm.model.REQ_BASE;
import com.fxbank.cap.ykwm.netty.ykwm.YkwmClient;
import com.fxbank.cap.ykwm.service.IForwardToYkwmService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: ForwardToYkwmService 
* @Description: 营口热电接口请求服务
* @作者 杜振铎
* @date 2019年4月29日 下午2:22:19 
*  
*/
@Service(version = "1.0.0")
public class ForwardToYkwmService implements IForwardToYkwmService {

	private static Logger logger = LoggerFactory.getLogger(ForwardToYkwmService.class);
    
	private static final String RSP_CODE = "0"; 
	
	@Resource
	private YkwmClient ykwmClient;

	@Override
	public <T extends REP_BASE> T sendToYkwm(REQ_BASE reqBase, Class<T> clazz) throws SysTradeExecuteException {
		MyLog myLog = reqBase.getMylog();
		reqBase.setTtxnNm(reqBase.getTtxnNm());
		T repModel = null;
		try {
			repModel = ykwmClient.comm(myLog, reqBase, clazz);
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
			String rspCode = repBase.getResult();
			// 返回失败
			if (!RSP_CODE.equals(rspCode)) {
				REP_ERROR repError = (REP_ERROR)repModel;
				String rspMsg = repError.getRepMsg();
				SysTradeExecuteException e = new SysTradeExecuteException(rspCode, rspMsg);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
		}
		return repModel;
	}

}
