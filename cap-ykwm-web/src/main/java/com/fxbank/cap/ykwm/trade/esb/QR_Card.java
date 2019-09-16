package com.fxbank.cap.ykwm.trade.esb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.esb.REP_30063001502;
import com.fxbank.cap.ykwm.dto.esb.REP_30063001502.CardInfo;
import com.fxbank.cap.ykwm.dto.esb.REQ_30063001502;
import com.fxbank.cap.ykwm.model.REP_QueryByOther;
import com.fxbank.cap.ykwm.model.REP_QueryByOther.Info;
import com.fxbank.cap.ykwm.model.REQ_QueryByOther;
import com.fxbank.cap.ykwm.service.IForwardToYkwmService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/** 
* @ClassName: QR_Card 
* @Description: 柜面模糊查询交易 
* @作者 杜振铎
* @date 2019年4月29日 下午2:10:52 
*  
*/
@Service("REQ_30063001502")
public class QR_Card extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(QR_Card.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToYkwmService forwardToYkwmService;

	@Resource
	private MyJedis myJedis;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30063001502 reqDto = (REQ_30063001502) dto;
		REQ_30063001502.REQ_BODY reqBody = reqDto.getReqBody();

		REQ_QueryByOther reqQuery = new REQ_QueryByOther(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		reqQuery.setCompanyID(reqBody.getHeatCompanyIdT());
		reqQuery.setOwnerName(reqBody.getUsername());
		//信息类别：Phone：电话号码；IDCard：身份证号；AgreeCode：合同编号；Address：地址
		reqQuery.setFieldKind(reqBody.getQueryType());
		reqQuery.setFieldValue(reqBody.getQueryInfo());

		myLog.info(logger, "发送模糊查询请求至营口热电");
		REP_QueryByOther repQuery = forwardToYkwmService.sendToYkwm(reqQuery, REP_QueryByOther.class);

		REP_30063001502 rep = new REP_30063001502();
		List<CardInfo> infoList = new ArrayList<CardInfo>();
		for(Info info:repQuery.getInfoList())
		{
			CardInfo data = new CardInfo();
			data.setUsername(info.getOwnerName());
			data.setUserAddr(info.getAddress());
			data.setCardNo(info.getCardNum());
			infoList.add(data);
			
		}
		rep.getRepBody().setCardInfoArray(infoList);
		
		return rep;
	}

}
