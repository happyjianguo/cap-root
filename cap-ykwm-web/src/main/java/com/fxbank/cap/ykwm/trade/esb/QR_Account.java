package com.fxbank.cap.ykwm.trade.esb;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.esb.REP_30063001501;
import com.fxbank.cap.ykwm.dto.esb.REP_30063001501.CourierCmpny;
import com.fxbank.cap.ykwm.dto.esb.REP_30063001501.DbtInfo;
import com.fxbank.cap.ykwm.dto.esb.REQ_30063001501;
import com.fxbank.cap.ykwm.model.REP_Query;
import com.fxbank.cap.ykwm.model.REP_Query.AccountDetail;
import com.fxbank.cap.ykwm.model.REQ_Query;
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
* @ClassName: QR_Account 
* @Description: 柜面欠费查询交易 
* @作者 杜振铎
* @date 2019年4月29日 下午2:10:52 
*  
*/
@Service("REQ_30063001501")
public class QR_Account extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(QR_Account.class);

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
		REQ_30063001501 reqDto = (REQ_30063001501) dto;
		REQ_30063001501.REQ_BODY reqBody = reqDto.getReqBody();

		REQ_Query reqQuery = new REQ_Query(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		reqQuery.setCompanyID(reqBody.getHeatCompanyIdT());
		reqQuery.setCardNum(reqBody.getUserCardNoT());
		reqQuery.setBatchNum(reqDto.getSysDate().toString());

		myLog.info(logger, "发送欠费查询请求至营口热电");
		REP_Query repQuery = forwardToYkwmService.sendToYkwm(reqQuery, REP_Query.class);

		REP_30063001501 rep = new REP_30063001501();
		rep.getRepBody().setUsername(repQuery.getOwnerName());
		rep.getRepBody().setUserAddr(repQuery.getAddress());
		rep.getRepBody().setMinPayFeeAmt(repQuery.getMinPayment().toString());
		rep.getRepBody().setPyFeeAmtT(repQuery.getTotal().toString());
		rep.getRepBody().setEmployerName(repQuery.getUnit());
		rep.getRepBody().setHeatAreaT(repQuery.getArea().toString());
		List<DbtInfo> accountArray = new ArrayList<DbtInfo>();
		for(AccountDetail detail:repQuery.getData())
		{
			DbtInfo dbtInfo = new DbtInfo();
			dbtInfo.setHeatYearT(detail.getChargeYear());
			dbtInfo.setHeatTpT(detail.getItemName());
			dbtInfo.setHeatAreaT(detail.getArea().toString());
			dbtInfo.setHeatPriceT(detail.getPrice().toString());
			dbtInfo.setDbtAmtT2(detail.getAccount().toString());
			dbtInfo.setOfferAmtT(detail.getAgio().toString());
			dbtInfo.setLateFeeT(detail.getLateFee().toString());
			dbtInfo.setAccrAmtT(detail.getPayment().toString());
			accountArray.add(dbtInfo);
			
		}
		rep.getRepBody().setDbtInfoArray(accountArray);
		rep.getRepBody().setReserveField1(repQuery.getDescription());
		rep.getRepBody().setChannelRefNo(repQuery.getCheckNum());
		List<CourierCmpny> expressArray = new ArrayList<CourierCmpny>();
		for(com.fxbank.cap.ykwm.model.REP_Query.Express temp:repQuery.getExpressList())
		{
			CourierCmpny express = new CourierCmpny();
			express.setCourierCmpnyIdT(temp.getExpressID().toString());
			express.setCourierCmpyAddsT(temp.getExpress());
			express.setCourierFeeT(temp.getPrice().toString());
			expressArray.add(express);
			
		}
		rep.getRepBody().setCourierCmpnyArray(expressArray);
		
		return rep;
	}

}
