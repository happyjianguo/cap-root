package com.fxbank.cap.ceba.controller;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.model.REP_BJCEBRWKRes;
import com.fxbank.cap.ceba.model.REQ_BJCEBRWKReq;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.ceba.service.IWorkKeyService;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.pub.service.IPublicService;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: ReqWorkKeyController
 * @Description: 手动发起工作密钥申请
 * @作者 杜振铎
 * @date 2019年8月6日 下午1:38:29
 * 
 */
@Controller
public class ReqWorkKeyController {
	private static Logger logger = LoggerFactory.getLogger(ReqWorkKeyController.class);
	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "ceba.";

	@Reference(version = "1.0.0")
	private IForwardToCebaService forwardToCebaService;

	@Reference(version = "1.0.0", cluster="broadcast")
	private IWorkKeyService workKeyService;

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@RequestMapping("/ceba/test")
	@ResponseBody
	public String test() throws SysTradeExecuteException {
		MyLog myLog = new MyLog();
		REQ_BJCEBRWKReq reqW = new REQ_BJCEBRWKReq(myLog, publicService.getSysDate("CIP"), publicService.getSysTime(),
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
		REP_BJCEBRWKRes res = (REP_BJCEBRWKRes) forwardToCebaService.sendToCeba(reqW);

		REP_BJCEBRWKRes.Tout tout = res.getTout();
		myLog.info(logger, "申请工作密钥成功：" + tout.getKeyName() + "," + tout.getKeyValue() + "," + tout.getVerifyValue()
				+ "," + tout.getKeyName1() + "," + tout.getKeyValue1() + "," + tout.getVerifyValue1());

		workKeyService.updateWorkKey(myLog, tout.getKeyValue1(), tout.getVerifyValue1(), tout.getKeyValue(),
				tout.getVerifyValue());

		return res.creaFixPack();

	}

}
