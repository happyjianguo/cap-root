package com.fxbank.cap.ykwm.nettty;

import javax.annotation.Resource;

import com.fxbank.cap.ykwm.dto.ykwm.REQ_BASE;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/** 
* @ClassName: YkwmPackConvInHandler 
* @Description: 来账请求拆包
* @作者 杜振铎
* @date 2019年4月29日 下午2:06:54 
*  
*/
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
			String fixPack = pack.substring(0, pack.length() );
			String txCode = "REQ_" + fixPack.split("\\|")[0];
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
			reqBase = new FixedUtil(fixPack, "\\|","UTF-8").toBean(reqBase.getClass());
			reqBase.setTxCode(txCode);
			reqBase.setSourceType("YKWM");
			reqBase.setOthDate(0);
			reqBase.setOthTraceno("");
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
