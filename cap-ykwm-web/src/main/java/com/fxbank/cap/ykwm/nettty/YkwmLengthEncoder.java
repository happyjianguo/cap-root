package com.fxbank.cap.ykwm.nettty;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@Component("ykwmLengthEncoder")
@Sharable
public class YkwmLengthEncoder extends MessageToByteEncoder<Object> {

	private static Logger logger = LoggerFactory.getLogger(YkwmLengthEncoder.class);

	@Resource
	private LogPool logPool;

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		MyLog myLog = logPool.get();
		String msgStr = (String) msg;
		StringBuffer sb = new StringBuffer();
		sb.append(msgStr);
		String reqPack = sb.toString();
		myLog.info(logger, "发送应答报文=[" + reqPack + "]");
		out.writeBytes(reqPack.getBytes(ServerInitializer.CODING));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		MyLog myLog = this.logPool.get();
		myLog.error(logger, "发送应答报文异常",cause);
		ctx.close();
	}
}
