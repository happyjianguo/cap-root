package com.fxbank.cap.ceba.netty;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.ceba.CebaApp;
import com.fxbank.cap.ceba.dto.ceba.DTO_BASE;
import com.fxbank.cap.ceba.dto.ceba.REP_BASE;
import com.fxbank.cap.ceba.dto.ceba.REP_ERROR;
import com.fxbank.cap.ceba.dto.ceba.REQ_BASE;
import com.fxbank.cap.ceba.exception.CebaTradeExecuteException;
import com.fxbank.cap.ceba.model.ErrorInfo;
import com.fxbank.cap.ceba.util.JAXBUtils;
import com.fxbank.cap.ceba.util.SerializeUtil;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.util.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import redis.clients.jedis.Jedis;

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

	@Resource
	private MyJedis myJedis;

	private static final String COMMON_PREFIX = "ceba.";

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MyLog myLog = logPool.get();
		try {
			String strMsg = (String) msg;
			if(!strMsg.toString().contains("BJCEBRWKRes")&&!strMsg.toString().contains("BJCEBRWKReq")) {
				String mac = strMsg.substring(strMsg.length() - 16);
				strMsg = strMsg.substring(0, strMsg.length() - 16);
				myLog.info(logger, "校验MAC  mac=[" + mac + "]");
				// 校验MAC		
				if(!CebaApp.softEnc.GenMac(strMsg.getBytes(ServerInitializer.CODING)).equals(mac)) {
					myLog.error(logger, "MAC校验失败");
					throw new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10008);
				}
			}
			
			if (strMsg.contains("<in>")) {
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
				DTO_BASE reqBase = null;
				Class<?> cebaClass = null;
				String className = "com.fxbank.cap.ceba.dto.ceba.REQ_" + txCode;
				try {
					cebaClass = Class.forName(className);
				} catch (ClassNotFoundException e) {
					myLog.error(logger, "类文件[" + className + "]未定义", e);
					throw e;
				}
				reqBase = (DTO_BASE) cebaClass.newInstance();
				reqBase.chanFixPack(strMsg);
				reqBase.setTxCode(txCode);
				reqBase.setSourceType("CEBA");
				reqBase.setOthDate(0);
				reqBase.setOthTraceno("");
				ctx.fireChannelRead(reqBase);
			} else if (strMsg.contains("<out>")) {
				REP_BASE baseBean = null;
				try {
					myLog.error(logger, "strMsg:" + strMsg);
					baseBean = (REP_BASE) JAXBUtils.unmarshal(strMsg.getBytes(), REP_BASE.class);
				} catch (JAXBException e) {
					myLog.error(logger, "报文格式不合法", e);
					throw e;
				}
				String txCode = baseBean.getHead().getAnsTranCode();
				myLog.info(logger, "交易代码=[" + txCode + "]");
				if (txCode.equals("Error")) {
					DTO_BASE dtoBase = null;
					Class<?> cebaClass = null;
					String className = "com.fxbank.cap.ceba.dto.ceba.REP_ERROR";
					try {
						cebaClass = Class.forName(className);
					} catch (ClassNotFoundException e) {
						myLog.error(logger, "类文件[" + className + "]未定义", e);
						throw e;
					}
					REP_ERROR repError = (REP_ERROR) cebaClass.newInstance();
					repError.chanFixPack(strMsg);
					byte[] dtoVal = SerializeUtil.serialize(repError);
					String channel = repError.getHead().getTrmSeqNum();
					myLog.info(logger, "报文同步通道编号=[" + channel + "]");
					jedisPublish(myLog, channel.getBytes(), dtoVal);
				} else {
					DTO_BASE dtoBase = null;
					Class<?> cebaClass = null;
					String className = "com.fxbank.cap.ceba.dto.ceba.REP_" + txCode;
					try {
						cebaClass = Class.forName(className);
					} catch (ClassNotFoundException e) {
						myLog.error(logger, "类文件[" + className + "]未定义", e);
						throw e;
					}
					dtoBase = (DTO_BASE) cebaClass.newInstance();
					dtoBase.chanFixPack(strMsg);
					byte[] dtoVal = SerializeUtil.serialize(dtoBase);
					String channel = dtoBase.getHead().getTrmSeqNum();
					myLog.info(logger, "报文同步通道编号=[" + channel + "]");
					jedisPublish(myLog, channel.getBytes(), dtoVal);
				}
			}

		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	public void jedisPublish(MyLog myLog, byte[] key, byte[] value) {
		Integer timeout = 55;
		try (Jedis jedis = myJedis.connect()) {

			myLog.info(logger, "异步回执总超时时间=[" + timeout + "]");

			String sKey = new String(key);
			myLog.info(logger, "发布的key=[" + sKey + "]");
			while (true) {
				// 查看通道是否备订阅，如果没有，则等待一段时间后，重新检查
				List<String> channels = jedis.pubsubChannels(sKey);
				if (channels.size() > 0 || timeout == 0) {
					break;
				} else {
					timeout--;
					myLog.info(logger, "未找到订阅通道,等待1s后再试,一共还剩[" + timeout + "]");
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						myLog.error(logger, "发布等待异常", e);
					}
				}
			}
			jedis.publish(key, value);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		MyLog myLog = logPool.get();
		myLog.info(logger, "解析客户端请求异常", cause);
		ctx.close();
	}

}
