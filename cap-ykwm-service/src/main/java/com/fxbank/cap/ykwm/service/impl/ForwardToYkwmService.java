package com.fxbank.cap.ykwm.service.impl;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ykwm.model.REP_BASE;
import com.fxbank.cap.ykwm.model.REQ_BASE;
import com.fxbank.cap.ykwm.netty.ykwm.YkwmClient;
import com.fxbank.cap.ykwm.service.IForwardToYkwmService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service(version = "1.0.0")
public class ForwardToYkwmService implements IForwardToYkwmService {

	private static Logger logger = LoggerFactory.getLogger(ForwardToYkwmService.class);

	@Resource
	private YkwmClient ykwmClient;

	@Override
	public <T extends REP_BASE> T sendToYkwm(REQ_BASE reqBase, Class<T> clazz) throws SysTradeExecuteException {
		MyLog myLog = reqBase.getMylog();
		reqBase.getHeader().settTxnNm(reqBase.getHeader().gettTxnNm());
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
			String rspCode = repBase.getHeader().getResult();
			if (!rspCode.equals("0")) { // 返回失败
				String rspMsg = repBase.getHeader().getErrorMsg();
				SysTradeExecuteException e = new SysTradeExecuteException(rspCode, rspMsg);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
		}
		return repModel;
	}

}
