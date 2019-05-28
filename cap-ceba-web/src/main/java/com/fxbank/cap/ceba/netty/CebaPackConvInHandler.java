package com.fxbank.cap.ceba.netty;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import com.fxbank.cap.ceba.dto.ceba.REQ_BASE;
import com.fxbank.cap.ceba.dto.ceba.REQ_BASE2;
import com.fxbank.cap.ceba.util.JAXBUtils;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/** 
* @ClassName: CebaPackConvInHandler 
* @Description: 光大银行来账请求拆包
* @作者 杜振铎
* @date 2019年5月7日 下午5:19:29 
*  
*/
@Component("cebaPackConvInHandler")
@Sharable
public class CebaPackConvInHandler extends ChannelInboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(CebaPackConvInHandler.class);

	@Resource
	private LogPool logPool;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MyLog myLog = logPool.get();
		try {
			String strMsg = (String) msg;
            strMsg = strMsg.substring(0,strMsg.length()-16 );
			REQ_BASE baseBean = null;
			try {
				myLog.error(logger, "strMsg:" + strMsg);
				baseBean = (REQ_BASE) JAXBUtils.unmarshal(strMsg.getBytes(), REQ_BASE.class);
			} catch (JAXBException e) {
				myLog.error(logger, "报文格式不合法", e);
				throw e;
			}
			String txCode = baseBean.getHead().getAnsTranCode();
			myLog.info(logger, "交易代码=[" + txCode + "]");
			REQ_BASE2 reqBase = null;
			Class<?> cebaClass = null;
			String className = "com.fxbank.cap.ceba.dto.ceba.REQ_" + txCode;
			try {
				cebaClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				myLog.error(logger, "类文件[" + className + "]未定义", e);
				throw e;
			}
			reqBase = (REQ_BASE2) cebaClass.newInstance();
			reqBase.chanFixPack(strMsg);
			reqBase.setTxCode(txCode);
			reqBase.setSourceType("CEBA");
			reqBase.setOthDate(20190527);
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
