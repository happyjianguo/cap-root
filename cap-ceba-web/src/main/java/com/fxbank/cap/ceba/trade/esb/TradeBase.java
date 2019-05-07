package com.fxbank.cap.ceba.trade.esb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/** 
* @ClassName: TradeBase 
* @Description: 交易程序基础公共类 
* @作者 杜振铎
* @date 2019年5月7日 下午5:21:29 
*  
*/
@Component
public class TradeBase {
	private static Logger logger = LoggerFactory.getLogger(TradeBase.class);

	public String convPin(String oPin){
		String nPin = oPin;
		//TODO
		return nPin;
	}
}
