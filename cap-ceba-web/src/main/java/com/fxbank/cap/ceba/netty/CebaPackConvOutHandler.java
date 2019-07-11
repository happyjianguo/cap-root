package com.fxbank.cap.ceba.netty;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.fxbank.cap.ceba.CebaApp;
import com.fxbank.cap.ceba.dto.ceba.DTO_BASE;
import com.fxbank.cap.ceba.dto.ceba.REP_ERROR;
import com.fxbank.cap.ceba.util.SerializeUtil;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.netty.channel.ChannelHandler.Sharable;
import redis.clients.jedis.Jedis;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/** 
* @ClassName: CebaPackConvOutHandler 
* @Description: 光大银行来账应答组包 
* @作者 杜振铎
* @date 2019年5月7日 下午5:19:45 
*  
*/
@Component("cebaPackConvOutHandler")
@Sharable
public class CebaPackConvOutHandler extends ChannelOutboundHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(CebaPackConvOutHandler.class);

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		MyLog myLog = this.logPool.get();
		@SuppressWarnings("unchecked")
		Map<String, DataTransObject> dtoMap = (Map<String, DataTransObject>) msg;
		DataTransObject dto = dtoMap.get("repDto");
		DataTransObject reqDto = dtoMap.get("reqDto");
		if(reqDto.getTxCode().equals("BJCEBBCNotify")||reqDto.getTxCode().equals("BJCEBOANotice")) {
			ctx.close();
			return;
		}
		String rspTyp = null;
		String rspCode = null;
		String rspMsg = null;
		DTO_BASE repDto = null;
		REP_ERROR repErr = null;
		if (dto.getStatus().equals(DataTransObject.SUCCESS)) { 
			if (dto instanceof REP_ERROR) {
				repErr = (REP_ERROR) dto;
				rspTyp = "E";
				rspCode = repErr.getTout().getErrorCode();
				rspMsg = repErr.getTout().getErrorMessage();
			} else {
				myLog.error(logger, "生成成功应答报文");
				repDto = (DTO_BASE) dtoMap.get("repDto");
				rspTyp = "N";
				rspCode = "FX0000";
				rspMsg = "交易成功";
			}
		} else { 
			myLog.error(logger, "生成错误应答报文");
			if (dto instanceof REP_ERROR) {
				repErr = (REP_ERROR) dto;
			}
			rspTyp = "E";	
			rspCode = (dto.getRspCode().equals("000000")||dto.getRspCode().equals(SysTradeExecuteException.CIP_E_999999)) ? "FX9999" : dto.getRspCode();
			rspMsg = dto.getRspMsg();
		}


		//生成MAC TODO
		String mac = "";
		StringBuffer fixPack = new StringBuffer(repDto.creaFixPack());
		if(!repDto.getHead().getAnsTranCode().equals("BJCEBRWKRes")&&!repDto.getHead().getAnsTranCode().equals("BJCEBRWKReq")) {
			mac = CebaApp.softEnc.GenMac(fixPack.toString().getBytes(ServerInitializer.CODING));
		}
		fixPack.append(mac);
		jedisPublish(myLog, repDto.getHead().getTrmSeqNum().getBytes(),  SerializeUtil.serialize(repDto));
		ctx.writeAndFlush(fixPack.toString(),promise);
		ctx.close();
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
		MyLog myLog = this.logPool.get();
		myLog.error(logger, "生成应答报文异常",cause);
		ctx.close();
	}

}
