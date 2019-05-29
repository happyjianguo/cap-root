package com.fxbank.cap.ykwm.trade.ykwm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.ykwm.REP_ERROR;
import com.fxbank.cap.ykwm.dto.ykwm.REP_Payment;
import com.fxbank.cap.ykwm.dto.ykwm.REQ_Payment;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @ClassName: Query
 * @Description: 欠费查询仿真
 * @作者 杜振铎
 * @date 2019年4月29日 下午2:11:41
 * 
 */
@Service("REQ_Payment")
public class Payment implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(Payment.class);

	//private static final String NUM_REG = "[1-9]{1}\\d*$|[0-9]{1}$";

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Resource
	private LogPool logPool;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		//REQ_Payment req = (REQ_Payment) dto;
		/*
		if(!req.getCardNum().matches(NUM_REG)) {
			req.getAddress();
			REP_ERROR repErr = new REP_ERROR();
			repErr.setResult("1");
			repErr.setRepMsg("用户卡号错误");
			return repErr;
		}
		*/

		REP_Payment rep = new REP_Payment();
		rep.setResult("0");//‘0’ 交易成功，否则返回错误信息
		rep.setCode("0380");//取票号

		return rep;
	}
}