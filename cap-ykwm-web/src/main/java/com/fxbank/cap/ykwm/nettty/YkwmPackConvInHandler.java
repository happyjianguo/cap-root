package com.fxbank.cap.ykwm.nettty;

import javax.annotation.Resource;

import com.fxbank.cap.ykwm.dto.ykwm.REQ_BASE;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

@Component("ykwmPackConvInHandler")
@Sharable
public class YkwmPackConvInHandler extends ChannelInboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(YkwmPackConvInHandler.class);

	@Resource
	private LogPool logPool;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MyLog myLog = logPool.get();
		try {
			StringBuffer pack = new StringBuffer((String) msg);
			String fixPack = pack.substring(0, pack.length() - 16);
			String mac = pack.substring(pack.length() - 16);
			myLog.info(logger, "mac=[" + mac + "]");
			// 校验MAC TODO
			String txCode = "REQ_" + pack.substring(0, 5);
			myLog.info(logger, "交易代码=[" + txCode + "]");
			REQ_BASE reqBase = null;
			Class<?> ykwmClass = null;
			String className = "com.fxbank.cap.ykwm.dto.ykwm." + txCode;
			try {
				ykwmClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				myLog.error(logger, "类文件[" + className + "]未定义", e);
				throw e;
			}
			
			reqBase = (REQ_BASE) ykwmClass.newInstance();
			reqBase.chanFixPack(fixPack);
			reqBase.setTxCode(txCode);
			reqBase.setSourceType("JH");
			//reqBase.setOthDate(reqBase.getHeader().gettTxnDat());
			//reqBase.setOthTraceno(reqBase.getHeader().getsLogNo());
			ctx.fireChannelRead(reqBase);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		MyLog myLog = logPool.get();
		myLog.info(logger, "解析客户端请求异常", cause);
		ctx.close();
	}

}
