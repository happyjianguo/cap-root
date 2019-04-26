package com.fxbank.cap.ykwm.nettty;

import java.util.concurrent.TimeUnit;

import com.fxbank.cap.ykwm.controller.TradeDispatcherHandler;
import com.fxbank.cip.base.common.LogPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @Description: netty服务端处理初始化
 * @Author: 周勇沩
 * @Date: 2019-04-15 11:16:32
 */
@Component("serverInitializer")
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	public static final String CODING="UTF-8";

	@Autowired
	private YkwmLengthEncoder ykwmLengthEncoder;
	
	@Autowired
	private YkwmPackConvOutHandler ykwmPackConvOutHandler;
	
	@Autowired
	private YkwmPackConvInHandler ykwmPackConvInHandler;
	
	@Autowired
	private TradeDispatcherHandler tradeDispatcherHandler;
	
	@Autowired
	private LogPool logPool;
	
	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new YkwmLenghtDecoder(logPool));
		p.addLast(new ReadTimeoutHandler(10000,TimeUnit.MILLISECONDS));
		p.addLast(this.ykwmLengthEncoder);
		p.addLast(this.ykwmPackConvOutHandler);
		p.addLast(this.ykwmPackConvInHandler);
		p.addLast(this.tradeDispatcherHandler);
	}

}
