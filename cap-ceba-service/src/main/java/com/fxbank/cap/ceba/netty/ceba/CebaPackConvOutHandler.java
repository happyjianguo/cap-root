package com.fxbank.cap.ceba.netty.ceba;

import com.fxbank.cap.ceba.CebaServer;
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
		
		StringBuffer fixPack = new StringBuffer((String)msg);
		//TODO 生成MAC
		if(!fixPack.toString().contains("BJCEBRWKReq")&&!fixPack.toString().contains("BJCEBRWKRes")) {
			fixPack.append(CebaServer.softEnc.GenMac(fixPack.toString().getBytes(CebaClient.CODING)));
		}
		ctx.writeAndFlush(fixPack.toString(), promise);
	}

}
