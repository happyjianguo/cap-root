package com.fxbank.cap.ceba.netty.ceba;

import com.fxbank.cap.ceba.model.REQ_BASE2;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/** 
* @ClassName: CebaPackConvOutHandler 
* @Description: 客户端请求组包
* @作者 杜振铎
* @date 2019年5月7日 下午5:13:21 
*  
*/
public class CebaPackConvOutHandler extends ChannelOutboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(CebaPackConvOutHandler.class);

	private MyLog myLog;

	public CebaPackConvOutHandler(MyLog myLog) {
		this.myLog = myLog;
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		REQ_BASE2 reqBase = (REQ_BASE2)msg;
		StringBuffer fixPack = new StringBuffer(reqBase.creaFixPack());
		//TODO 生成MAC
		fixPack.append("FFFFFFFFFFFFFFFF");
		ctx.writeAndFlush(fixPack.toString(), promise);
	}

}
