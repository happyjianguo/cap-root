package com.fxbank.cap.ykwm.trade.esb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;

/** 
* @ClassName: TradeBase 
* @Description: 交易程序基础公共类
* @作者 杜振铎
* @date 2019年4月29日 下午2:11:15 
*  
*/
@Component
public class TradeBase {
	private static Logger logger = LoggerFactory.getLogger(TradeBase.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

}
