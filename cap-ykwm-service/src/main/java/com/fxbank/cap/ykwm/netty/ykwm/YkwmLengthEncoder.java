package com.fxbank.cap.ykwm.netty.ykwm;

import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class YkwmLengthEncoder extends MessageToByteEncoder<Object> {

	private static Logger logger = LoggerFactory.getLogger(YkwmLengthEncoder.class);

	private MyLog myLog;

	public YkwmLengthEncoder(MyLog myLog) {
		this.myLog = myLog;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		String msgStr = (String) msg;
		Integer msgStrLen = msgStr.getBytes(YkwmClient.CODING).length;
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%08d", msgStrLen));
		sb.append(msgStr);
		String reqPack = sb.toString();
		this.myLog.info(logger, "发送请求报文=[" + reqPack);
		out.writeBytes(reqPack.getBytes(YkwmClient.CODING));
	}

}
