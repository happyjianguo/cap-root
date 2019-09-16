package com.fxbank.cap.ykwm.netty.ykwm;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ykwm.common.ScrtUtil;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettySyncSlot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;

/** 
* @ClassName: YkwmLenghtDecoder 
* @Description: 客户端接收程序
* @作者 杜振铎
* @date 2019年4月29日 下午2:20:13 
* 
* @param <T> 
*/
public class YkwmLenghtDecoder<T> extends ByteToMessageDecoder {

	private static Logger logger = LoggerFactory.getLogger(YkwmLenghtDecoder.class);
	private MyLog myLog;
	private final Integer DATALENGTH = 4;
	private NettySyncSlot<T> slot;
	private ScrtUtil scrtUtil;
	Pattern pattern = Pattern.compile("^[\\d]*$");

	public YkwmLenghtDecoder(MyLog myLog, NettySyncSlot<T> slot,ScrtUtil scrtUtil) {
		this.myLog = myLog;
		this.slot = slot;
		this.scrtUtil = scrtUtil;
	}

	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		this.myLog = new MyLog(UUID.randomUUID().toString(), InetAddress.getLocalHost().getHostAddress().toString());
		if (in == null) {
			return null;
		}
		int readLength = in.readableBytes();

		// 读取数据超时后关闭客户端连接，丢弃已读取的报文
		if (!ctx.channel().isActive()) {
			return null;
		}
		StringBuffer msgbuf = new StringBuffer(1024);
		// 一次做多只能读取1024个字节
		int onceLen = 1024;
		if (readLength > onceLen) {
			while (in.readableBytes() > 0) {
				int readLen = in.readableBytes() > onceLen ? onceLen: in.readableBytes();
				ByteBuf buf = in.readBytes(readLen);
				byte[] data = new byte[readLen];
				buf.readBytes(data);
				msgbuf.append(new String(data, YkwmClient.CODING));
				ReferenceCountUtil.release(buf);
				this.myLog.info(logger, "剩余字节数量"+in.readableBytes());
			}
		} else {
			ByteBuf buf = in.readBytes(readLength);
			byte[] req = new byte[buf.readableBytes()];
			buf.readBytes(req);
			msgbuf.append(new String(req, YkwmClient.CODING));
			ReferenceCountUtil.release(buf);
		}
		String body = msgbuf.toString();
		
		this.myLog.info(logger, "接收到客户端请求["+body+"]");

		return body;
		
		/**
		if ( readableLen < DATALENGTH ) {
			byte[] lenBytes = new byte[readableLen];
			ByteBuf buf = in.readBytes(readableLen);
			buf.readBytes(lenBytes);
			this.myLog.error(logger, "接收数据太短，不处理["+readableLen+"]["+new String(lenBytes, YkwmClient.CODING)+"]");
			return null;
		}
		in.markReaderIndex();
		
		ByteBuf lenbuf = in.readBytes(DATALENGTH);
		byte[] lenbyte = new byte[DATALENGTH];
		lenbuf.readBytes(lenbyte); 
		ReferenceCountUtil.release(lenbuf);
		String lenStr = new String(lenbyte,YkwmClient.CODING);
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
				msgbuf.append(new String(data, YkwmClient.CODING));
				ReferenceCountUtil.release(buf);
				this.myLog.info(logger, "剩余字节数量"+in.readableBytes());
			}
		} else {
			ByteBuf buf = in.readBytes(len);
			byte[] req = new byte[buf.readableBytes()];
			buf.readBytes(req);
			msgbuf.append(new String(req, YkwmClient.CODING));
			ReferenceCountUtil.release(buf);
		}

		//String body = scrtUtil.decrypt3DES(ScrtUtil.hexStringToBytes(msgbuf.toString()));
		String body = msgbuf.toString();
		
		this.myLog.info(logger, "接收到客户端请求["+body+"]");

		return body;
		**/
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
		this.myLog.info(logger, "接收服务端应答异常",cause);
		this.slot.setResponse(null);
	}

}
