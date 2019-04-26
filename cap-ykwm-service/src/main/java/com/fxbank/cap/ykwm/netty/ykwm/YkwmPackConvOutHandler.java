package com.fxbank.cap.ykwm.netty.ykwm;

import com.fxbank.cap.ykwm.model.REQ_BASE;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class YkwmPackConvOutHandler extends ChannelOutboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(YkwmPackConvOutHandler.class);

	private MyLog myLog;

	public YkwmPackConvOutHandler(MyLog myLog) {
		this.myLog = myLog;
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		REQ_BASE reqBase = (REQ_BASE)msg;
		StringBuffer fixPack = new StringBuffer(reqBase.creaFixPack());
		//TODO 生成MAC
		fixPack.append("FFFFFFFFFFFFFFFF");
		ctx.writeAndFlush(fixPack.toString(), promise);
	}

}
