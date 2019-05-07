package com.fxbank.cap.ceba.netty.ceba;

import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/** 
* @ClassName: CebaLengthEncoder 
* @Description: 客户端发送程序 
* @作者 杜振铎
* @date 2019年5月7日 下午5:13:00 
*  
*/
public class CebaLengthEncoder extends MessageToByteEncoder<Object> {

	private static Logger logger = LoggerFactory.getLogger(CebaLengthEncoder.class);

	private MyLog myLog;

	public CebaLengthEncoder(MyLog myLog) {
		this.myLog = myLog;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		String msgStr = (String) msg;
		Integer msgStrLen = msgStr.getBytes(CebaClient.CODING).length;
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%08d", msgStrLen));
		sb.append(msgStr);
		String reqPack = sb.toString();
		this.myLog.info(logger, "发送请求报文=[" + reqPack);
		out.writeBytes(reqPack.getBytes(CebaClient.CODING));
	}

}
