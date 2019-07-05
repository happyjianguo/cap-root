package com.fxbank.cap.ceba.controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.ceba.CebaApp;
import com.fxbank.cap.ceba.dto.ceba.DTO_BASE;
import com.fxbank.cap.ceba.dto.ceba.REP_BJCEBRWKRes;
import com.fxbank.cap.ceba.dto.ceba.REP_ERROR;
import com.fxbank.cap.ceba.model.ErrorInfo;
import com.fxbank.cap.ceba.model.REQ_BJCEBRWKReq;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.ceba.sync.SyncCom;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.cip.pub.service.IPublicService;

import cebenc.softenc.SoftEnc;
import redis.clients.jedis.Jedis;

@Controller
public class TestController {
	private static Logger logger = LoggerFactory.getLogger(TestController.class);
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";
	
	@Reference(version = "1.0.0")
	private IForwardToCebaService forwardToCebaService;
	
	@Resource
	private SyncCom syncCom;
	
	@Reference(version = "1.0.0")
	private IPublicService publicService;
	
	@RequestMapping("/ceba/test")
	@ResponseBody
	public String test() throws SysTradeExecuteException{
		MyLog myLog = new MyLog();
		REQ_BJCEBRWKReq reqW = new REQ_BJCEBRWKReq(myLog,publicService.getSysDate("CIP"), 
				publicService.getSysTime(),
				publicService.getSysTraceno()); 
		String instld = null;
		try (Jedis jedis = myJedis.connect()) {
			instld = jedis.get(COMMON_PREFIX + "ceba_instld");
		}
		reqW.getHead().setInstId(instld);
		reqW.getHead().setAnsTranCode("BJCEBRWKReq");
		reqW.getHead().setTrmSeqNum(publicService.getSysDate("CIP").toString() + publicService.getSysTraceno());
		reqW.getTin().setPartnerCode("746");
		reqW.getTin().setOperationDate(publicService.getSysDate("CIP").toString());
		reqW = (REQ_BJCEBRWKReq) forwardToCebaService.sendToCeba(reqW);
		String channel = reqW.getHead().getTrmSeqNum();
		myLog.info(logger, "查询工作密钥报文发送通道编号=[" + channel);
		DTO_BASE dtoBase = syncCom.get(myLog, channel, 55, TimeUnit.SECONDS);
		if (dtoBase.getHead().getAnsTranCode().equals("Error")) {
			REP_ERROR repError = (REP_ERROR) dtoBase;
			String errorCode = repError.getTout().getErrorCode();
			String jsonStr = null;
			try (Jedis jedis = myJedis.connect()) {
				jsonStr = jedis.get(COMMON_PREFIX + "ceba_error_list");
			}
			if (jsonStr == null || jsonStr.length() == 0) {
				logger.error("渠道未配置[" + COMMON_PREFIX + "ceba_error_list" + "]");
				throw new RuntimeException("渠道未配置[" + COMMON_PREFIX + "ceba_error_list" + "]");
			}
			Map<String, ErrorInfo> map = (Map) JSONObject.parse(jsonStr);
			ErrorInfo errorInfo = JsonUtil.toBean(JSON.toJSONString(map.get(errorCode)), ErrorInfo.class);
			String errorMsg = null;
			errorMsg = errorInfo.getQrErrorMsg();
			SysTradeExecuteException e = new SysTradeExecuteException(errorCode, errorMsg);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		} else if (dtoBase.getHead().getAnsTranCode().equals("BJCEBRWKRes")) {
			REP_BJCEBRWKRes res = (REP_BJCEBRWKRes) dtoBase;
			myLog.info(logger,"工作密钥"+res.getTout().getKeyName()+","+res.getTout().getKeyValue()+","+
			res.getTout().getVerifyValue()+","+res.getTout().getKeyName1()+","+res.getTout().getKeyValue1()+","+
			res.getTout().getVerifyValue1());
	    	try {
				CebaApp.softEnc.WriteMACK(res.getTout().getKeyValue1(), res.getTout().getVerifyValue1());
				CebaApp.softEnc.WritePINK(res.getTout().getKeyValue(), res.getTout().getVerifyValue());
			} catch (Exception e) {
				myLog.error(logger,"加密失败", e);
			}
			return res.creaFixPack();
		}
		return "";
	}
	
}
