package com.fxbank.cap.ceba.trade.ceba;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.ceba.REP_BJCEBBCNotify;
import com.fxbank.cap.ceba.dto.ceba.REP_BJCEBOANotice;
import com.fxbank.cap.ceba.dto.ceba.REQ_BJCEBOANotice;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

/** 
* @ClassName: BJCEBOANotice 
* @Description: 停运通知报文 
* @作者 杜振铎
* @date 2019年6月12日 下午2:20:17 
*  
*/
@Service("BJCEBOANotice")
public class BJCEBOANotice implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(BJCEBOANotice.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";
	
	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_BJCEBOANotice req = (REQ_BJCEBOANotice) dto;
		System.out.println(req.getTin().getFileName());
		REP_BJCEBOANotice rep = new REP_BJCEBOANotice();
		return rep;
	}
	
	
}