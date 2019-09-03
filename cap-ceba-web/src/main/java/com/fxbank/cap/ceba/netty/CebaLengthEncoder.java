package com.fxbank.cap.ceba.netty;

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

/** 
* @ClassName: CebaLengthEncoder 
* @Description: 光大银行来账应答发送 
* @作者 杜振铎
* @date 2019年5月7日 下午5:19:12 
*  
*/
@Component("cebaLengthEncoder")
@Sharable
public class CebaLengthEncoder extends MessageToByteEncoder<Object> {

	private static Logger logger = LoggerFactory.getLogger(CebaLengthEncoder.class);

	@Resource
	private LogPool logPool;

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		MyLog myLog = logPool.get();
		String msgStr = (String) msg;
		Integer msgStrLen = msgStr.getBytes(ServerInitializer.CODING).length;
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%06d", msgStrLen));
		sb.append(msgStr);
		String reqPack = sb.toString();
		myLog.info(logger, "发送应答报文=[" + reqPack);
		out.writeBytes(reqPack.getBytes(ServerInitializer.CODING));
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		MyLog myLog = this.logPool.get();
		myLog.error(logger, "发送应答报文异常",cause);
		ctx.close();
	}
}
