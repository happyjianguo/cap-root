package com.fxbank.cap.ykwm.nettty;

import javax.annotation.Resource;

import com.fxbank.cap.ykwm.common.ScrtUtil;
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
* @ClassName: YkwmLengthEncoder 
* @Description: 来账应答发送
* @作者 杜振铎
* @date 2019年4月29日 下午2:06:43 
*  
*/
@Component("ykwmLengthEncoder")
@Sharable
public class YkwmLengthEncoder extends MessageToByteEncoder<Object> {

	private static Logger logger = LoggerFactory.getLogger(YkwmLengthEncoder.class);

	@Resource
	private LogPool logPool;
	
	@Resource
	private ScrtUtil scrtUtil;

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		MyLog myLog = logPool.get();
		String msgStr = (String) msg;
		StringBuffer sb = new StringBuffer();
		sb.append(msgStr);
		//String reqPack = scrtUtil.encrypt3DES(sb.toString().getBytes(ServerInitializer.CODING));
		String reqPack = sb.toString();
		myLog.info(logger, "发送应答报文=[" + String.format("%04d", reqPack.length()) + reqPack + "]");
		byte[] data=reqPack.getBytes(ServerInitializer.CODING);
		out.writeInt(data.length);
		out.writeBytes(data);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		MyLog myLog = this.logPool.get();
		myLog.error(logger, "发送应答报文异常",cause);
		ctx.close();
	}
}
