package com.fxbank.cap.ceba.service.impl;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.model.ErrorInfo;
import com.fxbank.cap.ceba.model.ErrorList;
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
	
	@Resource
	private CebaClient cebaClient;
	
	private static final String COMMON_PREFIX = "ceba.";

	@Resource
	private MyJedis myJedis;

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
				String jsonStrErrorList = null;
				try(Jedis jedis = myJedis.connect()){
					jsonStrErrorList = jedis.get(COMMON_PREFIX+"ceba_error_list");
		        }
				if(jsonStrErrorList==null||jsonStrErrorList.length()==0){
					logger.error("渠道未配置["+COMMON_PREFIX + "ceba_error_list"+"]");
					throw new RuntimeException("渠道未配置["+COMMON_PREFIX + "ceba_error_list"+"]");
				}
				ErrorList errorList = JsonUtil.toBean(jsonStrErrorList, ErrorList.class);
		        String errorMsg = null;
				for(ErrorInfo errorInfo:errorList.getData()){
					if(errorInfo.getErrorCode().equals(errorCode)) {
						if(reqBase.getHead().getAnsTranCode().startsWith("BJCEBBC")) {
							errorMsg = errorInfo.getCgErrorMsg();
						}else {
							errorMsg = errorInfo.getQrErrorMsg();
						}
					}
				}
				SysTradeExecuteException e = new SysTradeExecuteException(errorCode,errorMsg);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
		}
		return repModel;
	}
}
