package com.fxbank.cap.ykwm.netty.ykwm;

import com.fxbank.cap.ykwm.common.ScrtUtil;
import com.fxbank.cip.base.log.MyLog;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/** 
* @ClassName: YkwmLengthEncoder 
* @Description: 客户端发送程序
* @作者 杜振铎
* @date 2019年4月29日 下午2:21:00 
*  
*/
public class YkwmLengthEncoder extends MessageToByteEncoder<Object> {

	private static Logger logger = LoggerFactory.getLogger(YkwmLengthEncoder.class);
	
	@Resource
	private ScrtUtil scrtUtil;

	private MyLog myLog;

	public YkwmLengthEncoder(MyLog myLog,ScrtUtil scrtUtil) {
		this.myLog = myLog;
		this.scrtUtil = scrtUtil;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		String msgStr = (String) msg;
		StringBuffer sb = new StringBuffer();
		sb.append(msgStr);
		//String reqPack = scrtUtil.encrypt3DES(sb.toString().getBytes(YkwmClient.CODING));
		String reqPack = sb.toString();
		//myLog.info(logger, "发送应答报文=[" + String.format("%04d", reqPack.length()) + reqPack + "]");
		myLog.info(logger, "发送应答报文=[" + reqPack + "]");
		byte[] data=reqPack.getBytes(YkwmClient.CODING);
		out.writeInt(data.length);
		out.writeBytes(data);
	}

}
