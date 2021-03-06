package com.fxbank.cap.ceba.netty;

import java.util.Map;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.ceba.DTO_BASE;
import com.fxbank.cap.ceba.dto.ceba.REP_ERROR;
import com.fxbank.cap.ceba.service.IWorkKeyService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.netty.channel.ChannelHandler.Sharable;
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
	
	@Reference(version = "1.0.0")
	private IWorkKeyService workKeyService;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";

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
				myLog.info(logger, "生成成功应答报文");
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
			mac = workKeyService.genMac(myLog, fixPack.toString());
		}
		fixPack.append(mac);
		ctx.writeAndFlush(fixPack.toString(),promise);
		ctx.close();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		MyLog myLog = this.logPool.get();
		myLog.error(logger, "生成应答报文异常",cause);
		ctx.close();
	}

}
