package com.fxbank.cap.ykwm.trade.ykwm;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.ykwm.REP_Cancel;
import com.fxbank.cap.ykwm.dto.ykwm.REP_ERROR;
import com.fxbank.cap.ykwm.dto.ykwm.REP_Query;
import com.fxbank.cap.ykwm.dto.ykwm.REP_Query.AccountDetail;
import com.fxbank.cap.ykwm.dto.ykwm.REP_Query.Express;
import com.fxbank.cap.ykwm.dto.ykwm.REQ_Cancel;
import com.fxbank.cap.ykwm.dto.ykwm.REQ_Query;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("REQ_Cancel")
public class Cancel implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(Cancel.class);

	private static final String NUM_REG = "[1-9]{1}\\d*$|[0-9]{1}$";
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Resource
	private LogPool logPool;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_Cancel req = (REQ_Cancel) dto;
        try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!req.getCheckNum().matches(NUM_REG)) {
			REP_ERROR repErr = new REP_ERROR();
			repErr.setResult("1");
			repErr.setRepMsg("用户卡号错误");
			return repErr;
		}
		REP_Cancel rep = new REP_Cancel();
		return rep;
	}
}