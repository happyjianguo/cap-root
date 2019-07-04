package com.fxbank.cap.ceba.netty.ceba;

import com.fxbank.cap.ceba.model.REP_BASE;
import com.fxbank.cap.ceba.model.REP_ERROR;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettySyncClient;
import com.fxbank.cip.base.netty.NettySyncSlot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

/**
 * @ClassName: CebaPackConvInHandler
 * @Description: 客户端应答拆包
 * @作者 杜振铎
 * @date 2019年5月7日 下午5:13:11
 * 
 * @param <T>
 */
public class CebaPackConvInHandler<T> extends ChannelInboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(CebaPackConvInHandler.class);

	private MyLog myLog;

	private Class<T> clazz;

	private static final String ERRORCODE = "Error";

	public CebaPackConvInHandler(MyLog myLog) {
		this.myLog = myLog;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			StringBuffer pack = new StringBuffer((String) msg);
			String mac = pack.substring(pack.length());
			this.myLog.info(logger, "mac=[" + mac + "]");
			// TODO 校验MAC

			String fixPack = pack.substring(0, pack.length() );
			REP_BASE repBase = (REP_BASE) this.clazz.newInstance();
			repBase.chanFixPack(fixPack);
			if (ERRORCODE.equals(repBase.getHead().getAnsTranCode())) {
				REP_ERROR repErr = new REP_ERROR();
				repErr.chanFixPack(fixPack);
				ctx.fireChannelRead(repErr);
			} else {
				ctx.fireChannelRead(repBase);
			}
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Attribute<NettySyncSlot<T>> slotAttr = ctx.channel().attr(AttributeKey.valueOf(NettySyncClient.SLOTKEY));
		NettySyncSlot<T> slot = slotAttr.get();
		slot.setResponse(null);
		this.myLog.info(logger, "解析服务端应答异常", cause);
	}

}
