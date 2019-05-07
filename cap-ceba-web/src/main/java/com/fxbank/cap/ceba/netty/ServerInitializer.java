package com.fxbank.cap.ceba.netty;

import java.util.concurrent.TimeUnit;

import com.fxbank.cap.ceba.controller.TradeDispatcherHandler;
import com.fxbank.cip.base.common.LogPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/** 
* @ClassName: ServerInitializer 
* @Description: netty服务端处理初始化
* @作者 杜振铎
* @date 2019年5月7日 下午5:20:02 
*  
*/
@Component("serverInitializer")
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	public static final String CODING="UTF-8";

	@Autowired
	private CebaLengthEncoder cebaLengthEncoder;
	
	@Autowired
	private CebaPackConvOutHandler cebaPackConvOutHandler;
	
	@Autowired
	private CebaPackConvInHandler cebaPackConvInHandler;
	
	@Autowired
	private TradeDispatcherHandler tradeDispatcherHandler;
	
	@Autowired
	private LogPool logPool;
	
	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new CebaLenghtDecoder(logPool));
		p.addLast(new ReadTimeoutHandler(10000,TimeUnit.MILLISECONDS));
		p.addLast(this.cebaLengthEncoder);
		p.addLast(this.cebaPackConvOutHandler);
		p.addLast(this.cebaPackConvInHandler);
		p.addLast(this.tradeDispatcherHandler);
	}

}
