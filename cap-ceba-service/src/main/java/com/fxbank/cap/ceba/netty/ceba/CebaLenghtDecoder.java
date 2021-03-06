package com.fxbank.cap.ceba.netty.ceba;

import java.util.List;
import java.util.regex.Pattern;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettySyncClient;
import com.fxbank.cip.base.netty.NettySyncSlot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

/** 
* @ClassName: CebaLenghtDecoder 
* @Description: 客户端接收程序 
* @作者 杜振铎
* @date 2019年5月7日 下午5:12:47 
* 
* @param <T> 
*/
public class CebaLenghtDecoder<T> extends ByteToMessageDecoder {

	private static Logger logger = LoggerFactory.getLogger(CebaLenghtDecoder.class);
	private MyLog myLog;
	private final Integer DATALENGTH = 6;
	Pattern pattern = Pattern.compile("^[\\d]*$");

	public CebaLenghtDecoder(MyLog myLog) {
		this.myLog = myLog;
	}

	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		if (in == null) {
			return null;
		}
		int readableLen = in.readableBytes();
		if ( readableLen < DATALENGTH ) {
			byte[] lenBytes = new byte[readableLen];
			ByteBuf buf = in.readBytes(readableLen);
			buf.readBytes(lenBytes);
			this.myLog.error(logger, "接收数据太短，不处理["+readableLen+"]["+new String(lenBytes, CebaClient.CODING)+"]");
			return null;
		}
		in.markReaderIndex();
		ByteBuf lenbuf = in.readBytes(DATALENGTH);
		byte[] lenbyte = new byte[DATALENGTH];
		lenbuf.readBytes(lenbyte); 
		ReferenceCountUtil.release(lenbuf);
		String lenStr = new String(lenbyte,CebaClient.CODING);
		if (!isInteger(lenStr)) {
			Exception e = new RuntimeException("报文长度不合法");
			this.myLog.error(logger, "报文长度不合法"+lenStr, e);
			throw e;
		}
		Integer len = new Integer(lenStr);
		// 判断是否分包,数据长度大于等于总长度或者本次读取数据长度与上次相同认为分包结束
		int readLength = in.readableBytes();
		if (readLength < len) {
			this.myLog.error(logger, "等待后续分包["+readLength+"]["+len+"]");
			in.resetReaderIndex();
			return null;
		}

		// 读取数据超时后关闭客户端连接，丢弃已读取的报文
		if (!ctx.channel().isActive()) {
			return null;
		}
		StringBuffer msgbuf = new StringBuffer(1024);
		// 一次做多只能读取1024个字节
		int onceLen = 1024;
		if (len > onceLen) {
			while (in.readableBytes() > 0) {
				int readLen = in.readableBytes() > onceLen ? onceLen: in.readableBytes();
				ByteBuf buf = in.readBytes(readLen);
				byte[] data = new byte[readLen];
				buf.readBytes(data);
				msgbuf.append(new String(data, CebaClient.CODING));
				ReferenceCountUtil.release(buf);
				this.myLog.info(logger, "剩余字节数量"+in.readableBytes());
			}
		} else {
			ByteBuf buf = in.readBytes(len);
			byte[] req = new byte[buf.readableBytes()];
			buf.readBytes(req);
			msgbuf.append(new String(req, CebaClient.CODING));
			ReferenceCountUtil.release(buf);
		}

		String body = msgbuf.toString();

		this.myLog.info(logger, "接收到服务端应答["+body+"]");

		return body;
	}

	public boolean isInteger(String str) {
		
		return pattern.matcher(str).matches();
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Object decoded = decode(ctx, in);
		if (decoded != null) {
			out.add(decoded);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Attribute<NettySyncSlot<T>> slotAttr = ctx.channel().attr(AttributeKey.valueOf(NettySyncClient.SLOTKEY));
		NettySyncSlot<T> slot = slotAttr.get();
		slot.setResponse(null);
		this.myLog.info(logger, "接收服务端应答异常",cause);
	}

}
