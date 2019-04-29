package com.fxbank.cap.ykwm.trade.esb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.esb.REP_30012002001;
import com.fxbank.cap.ykwm.dto.esb.REP_30012002001.Account;
import com.fxbank.cap.ykwm.dto.esb.REP_30012002001.Express;
import com.fxbank.cap.ykwm.dto.esb.REQ_30012002001;
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
@Service("REQ_30012002001")
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
		REQ_30012002001 reqDto = (REQ_30012002001) dto;
		REQ_30012002001.REQ_BODY reqBody = reqDto.getReqBody();

		REQ_Query reqQuery = new REQ_Query(myLog, dto.getSysDate(), dto.getSysTime(), dto.getSysTraceno());
		reqQuery.setCompanyID(reqBody.getCompanyID());
		reqQuery.setCardNum(reqBody.getCardNum());
		reqQuery.setBatchNum(reqBody.getBatchNum());

		myLog.info(logger, "发送欠费查询请求至营口热电");
		REP_Query repQuery = forwardToYkwmService.sendToYkwm(reqQuery, REP_Query.class);

		REP_30012002001 rep = new REP_30012002001();
		rep.getRepBody().setOwnerName(repQuery.getOwnerName());
		rep.getRepBody().setAddress(repQuery.getAddress());
		rep.getRepBody().setMinPayment(repQuery.getMinPayment().toString());
		rep.getRepBody().setTotal(repQuery.getTotal().toString());
		rep.getRepBody().setUnit(repQuery.getUnit());
		rep.getRepBody().setArea(repQuery.getArea().toString());
		List<Account> accountArray = new ArrayList<Account>();
		for(AccountDetail detail:repQuery.getData())
		{
			Account account = new Account();
			account.setChargeYear(detail.getChargeYear());
			account.setItemName(detail.getItemName());
			account.setArea(detail.getArea().toString());
			account.setPrice(detail.getPrice().toString());
			account.setAccount(detail.getAccount().toString());
			account.setAgio(detail.getAgio().toString());
			account.setLateFee(detail.getLateFee().toString());
			account.setPayment(detail.getPayment().toString());
			accountArray.add(account);
			
		}
		rep.getRepBody().setAccountArray(accountArray);
		rep.getRepBody().setDescription(repQuery.getDescription());
		rep.getRepBody().setCheckNum(repQuery.getCheckNum());
		List<Express> expressArray = new ArrayList<Express>();
		for(com.fxbank.cap.ykwm.model.REP_Query.Express temp:repQuery.getExpressList())
		{
			Express express = new Express();
			express.setExpressID(temp.getExpressID().toString());
			express.setExpress(temp.getExpress());
			express.setPrice(temp.getPrice().toString());
			expressArray.add(express);
			
		}
		rep.getRepBody().setExpressArray(expressArray);
		
		return rep;
	}

}
