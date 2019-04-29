package com.fxbank.cap.ykwm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.controller.TradeDispatcherBase;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/** 
* @ClassName: TradeDispatcherHandler 
* @Description: 交易处理入口类 
* @author Duzhenduo
* @date 2019年4月29日 下午1:44:54 
*  
*/
@Service("tradeDispatcherHandler")
@Sharable
public class TradeDispatcherHandler extends ChannelInboundHandlerAdapter {
	private static Logger logger = LoggerFactory.getLogger(TradeDispatcherHandler.class);

	@Resource
	private TradeDispatcherBase tradeDispatcherBase;

	@Resource
	private LogPool logPool;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MyLog myLog = logPool.get();
		myLog.debug(logger, "交易流程执行开始...");
		DataTransObject reqDto = (DataTransObject) msg;
		DataTransObject repDto = tradeDispatcherBase.txMainFlowController(reqDto);
		Map<String,DataTransObject> dtoMap = new HashMap<String,DataTransObject>(16);
		dtoMap.put("reqDto", reqDto);
		dtoMap.put("repDto", repDto);
		myLog.debug(logger, "交易流程执行完毕...");
		ctx.write(dtoMap);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		MyLog logUtil = logPool.get();
		logUtil.error(logger, "TradeDispatcherHandler process error!", cause);
		Map<String,DataTransObject> dtoMap = new HashMap<String,DataTransObject>(16);
		DataTransObject repDto = new DataTransObject(new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999));
		dtoMap.put("repDto", repDto);
		ctx.write(dtoMap);
	}

}
