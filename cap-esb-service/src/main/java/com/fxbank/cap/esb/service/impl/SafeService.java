package com.fxbank.cap.esb.service.impl;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.esb.common.ESB;
import com.fxbank.cap.esb.exception.ESBTradeExecuteException;
import com.fxbank.cap.esb.model.ykwm.SafeModel;
import com.fxbank.cap.esb.service.ISafeService;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import cn.highsuccess.connPool.api.tssc.HisuTSSCAPI;
import cn.highsuccess.connPool.api.tssc.HisuTSSCAPIResult;
import redis.clients.jedis.Jedis;

@Service(version = "1.0.0")
public class SafeService implements ISafeService {

	private static Logger logger = LoggerFactory.getLogger(SafeService.class);
	private final static String COMMON_PREFIX = "tcex_common.";

	@Resource
	private MyJedis myJedis;
	
	@Resource
	private HisuTSSCAPI hisuTSSCAPI;
	
	public String calcMac(MyLog myLog,byte[] dataToMAC) throws SysTradeExecuteException {
		try {
			HisuTSSCAPIResult result = hisuTSSCAPI.calculateHashDataMACBySpecKeyBytes(ESB.macDeginId, ESB.macNodeId,
					ESB.macKeyModelId, 1, 2, dataToMAC, dataToMAC.length);
			if (result.getErrCode() < 0) {
				myLog.error(logger, "调用加密平台计算MAC失败");
				throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
			}
			return ESB.macDeginId+"|"+ESB.macNodeId+"|"+ESB.macKeyModelId+"|"+result.getMAC()+"|";
		} catch (Exception e) {
			myLog.error(logger, "调用加密平台计算MAC失败", e);
			throw new SysTradeExecuteException(SysTradeExecuteException.CIP_E_000003);
		}
	}

}
