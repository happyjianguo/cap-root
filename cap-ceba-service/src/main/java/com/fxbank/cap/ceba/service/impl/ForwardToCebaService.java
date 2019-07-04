package com.fxbank.cap.ceba.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.ceba.model.ErrorInfo;
import com.fxbank.cap.ceba.model.MODEL_BASE;
import com.fxbank.cap.ceba.model.REP_BASE;
import com.fxbank.cap.ceba.model.REP_ERROR;
import com.fxbank.cap.ceba.model.REQ_BASE2;
import com.fxbank.cap.ceba.netty.ceba.CebaClient;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
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

	@Resource
	private MyJedis myJedis;

	@Override
	public MODEL_BASE sendToCeba(MODEL_BASE reqBase) throws SysTradeExecuteException {
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
