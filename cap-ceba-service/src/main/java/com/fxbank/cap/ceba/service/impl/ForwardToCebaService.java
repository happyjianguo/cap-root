package com.fxbank.cap.ceba.service.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.ceba.model.ErrorInfo;
import com.fxbank.cap.ceba.model.MODEL_BASE;
import com.fxbank.cap.ceba.model.MODEL_BASE2;
import com.fxbank.cap.ceba.model.REP_ERROR;
import com.fxbank.cap.ceba.netty.ceba.CebaClient;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.ceba.sync.SyncCom;
import com.fxbank.cap.ceba.util.JAXBUtils;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.util.JsonUtil;
import redis.clients.jedis.Jedis;
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

	private static final String CHARGE_PREFIX = "BJCEBBC";

	@Resource
	private CebaClient cebaClient;

	private static final String COMMON_PREFIX = "ceba.";

	private static final String REDIS_PREFIX = "CEBA-";

	@Resource
	private MyJedis myJedis;

	@Resource
	private SyncCom syncCom;

	@Override
	public <T extends MODEL_BASE> T sendToCeba(MODEL_BASE reqBase) throws SysTradeExecuteException {
		MyLog myLog = reqBase.getMylog();
		try {
			cebaClient.comm(myLog, reqBase.creaFixPack());
		} catch (SysTradeExecuteException e) {
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg(), e);
			throw e;
		} catch (Exception e) {
			SysTradeExecuteException e1 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999,
					e.getMessage());
			myLog.error(logger, e1.getRspCode() + " | " + e1.getRspMsg(), e);
			throw e1;
		}
		T repModel = null;
		MODEL_BASE2 repBase = null;
		Integer timeout = 0;
		try (Jedis jedis = myJedis.connect()) {
			String stimeout = jedis.get(COMMON_PREFIX + "ceba_timeout");
			if (stimeout == null) {
				timeout = 60;
			} else {
				try {
					timeout = Integer.valueOf(stimeout);
				} catch (Exception e) {
					myLog.error(logger, "报文同步等待超时时间配置异常，取默认值60");
					timeout = 60;
				}
			}
		}
		byte[] strBytes = syncCom.get(myLog, REDIS_PREFIX + reqBase.getHead().getTrmSeqNum(), timeout,
				TimeUnit.SECONDS);
		if (strBytes == null) {
			SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg(), e);
			throw e;
		} else {
			String strMsg;
			try {
				strMsg = new String(strBytes,CebaClient.CODING);
				repBase = (MODEL_BASE2) JAXBUtils.unmarshal(strMsg.getBytes(), MODEL_BASE2.class);
			} catch (Exception e) {
				SysTradeExecuteException e1 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999,
						e.getMessage());
				myLog.error(logger, e1.getRspCode() + " | " + e1.getRspMsg(), e1);
				throw e1;
			}
			String rspCode = repBase.getHead().getAnsTranCode();
			if (ERROR.equals(rspCode)) {
				REP_ERROR repError;
				try {
					repError = (REP_ERROR) JAXBUtils.unmarshal(strMsg.getBytes(), REP_ERROR.class);
				} catch (JAXBException e1) {
					SysTradeExecuteException e2 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999,
							e1.getMessage());
					myLog.error(logger, e2.getRspCode() + " | " + e2.getRspMsg(), e2);
					throw e2;
				}
				String errorCode = repError.getTout().getErrorCode();
				String jsonStr = null;
				try (Jedis jedis = myJedis.connect()) {
					jsonStr = jedis.get(COMMON_PREFIX + "ceba_error_list");
				}
				if (jsonStr == null || jsonStr.length() == 0) {
					logger.error("渠道未配置[" + COMMON_PREFIX + "ceba_error_list" + "]");
					SysTradeExecuteException e = new SysTradeExecuteException(errorCode, "");
					myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
					throw e;
				}
				Map<String, ErrorInfo> map = (Map) JSONObject.parse(jsonStr);
				ErrorInfo errorInfo = JsonUtil.toBean(JSON.toJSONString(map.get(errorCode)), ErrorInfo.class);
				String errorMsg = null;
				if (errorInfo == null) {
					errorMsg = "";
				} else {
					if (reqBase.getHead().getAnsTranCode().startsWith(CHARGE_PREFIX)) {
						errorMsg = errorInfo.getCgErrorMsg();
					} else {
						errorMsg = errorInfo.getQrErrorMsg();
					}
				}

				SysTradeExecuteException e = new SysTradeExecuteException(errorCode, errorMsg);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}else {
				Class<?> cebaClass = null;
				String className = "com.fxbank.cap.ceba.model.REP_" + rspCode;
				try {
					cebaClass = Class.forName(className);
					repModel = (T) cebaClass.newInstance();
					repModel.chanFixPack(strMsg);
				} catch (Exception e) {
					SysTradeExecuteException e1 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999,
							e.getMessage());
					myLog.error(logger, e1.getRspCode() + " | " + e1.getRspMsg());
					throw e1;
				}
				
			}
		}
		return repModel;
	}

	@Override
	public MODEL_BASE sendToFxb(MODEL_BASE reqBase) throws SysTradeExecuteException {
		MyLog myLog = reqBase.getMylog();
		try {
			cebaClient.comm(myLog, reqBase.creaFixPack());
		} catch (SysTradeExecuteException e) {
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg(), e);
			throw e;
		} catch (Exception e) {
			SysTradeExecuteException e1 = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999,
					e.getMessage());
			myLog.error(logger, e1.getRspCode() + " | " + e1.getRspMsg(), e);
			throw e1;
		}

		return reqBase;
	}
}
