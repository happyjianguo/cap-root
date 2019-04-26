package com.fxbank.cap.ykwm.trade.esb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;

@Component
public class TradeBase {
	private static Logger logger = LoggerFactory.getLogger(TradeBase.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

}
