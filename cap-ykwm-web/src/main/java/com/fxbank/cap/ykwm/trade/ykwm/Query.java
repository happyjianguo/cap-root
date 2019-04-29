package com.fxbank.cap.ykwm.trade.ykwm;


import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.ykwm.REP_Query;
import com.fxbank.cap.ykwm.dto.ykwm.REP_Query.AccountDetail;
import com.fxbank.cap.ykwm.dto.ykwm.REP_Query.Express;
import com.fxbank.cap.ykwm.dto.ykwm.REQ_Query;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** 
* @ClassName: Query 
* @Description: 欠费查询仿真
* @author Duzhenduo
* @date 2019年4月29日 下午2:11:41 
*  
*/
@Service("REQ_Query")
public class Query implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(Query.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Resource
	private LogPool logPool;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_Query req = (REQ_Query) dto;
		REP_Query rep = new REP_Query();
		rep.getHeader().setResult("0");
		rep.setOwnerName("张三");
		rep.setAddress("北京");
		rep.setMinPayment(new BigDecimal(100));
		rep.setTotal(new BigDecimal(500));
		rep.setUnit("aa");
		rep.setArea(new BigDecimal("120"));
		List<REP_Query.AccountDetail> data = new ArrayList<REP_Query.AccountDetail>();
		AccountDetail accountDetail = new AccountDetail();
		accountDetail.setChargeYear("2015-2016");
		accountDetail.setItemName("ds");
		accountDetail.setArea(new BigDecimal(120));
		accountDetail.setPrice(new BigDecimal(1.2).setScale(2, BigDecimal.ROUND_HALF_UP));
		accountDetail.setAccount(new BigDecimal(200));
		accountDetail.setAgio(new BigDecimal(0));
        accountDetail.setLateFee(new BigDecimal(23));
        accountDetail.setPayment(new BigDecimal(500));
        data.add(accountDetail);
		rep.setData(data);
		rep.setDescription("sda");
		rep.setCheckNum("232424");
		rep.setExtend("");
		List<REP_Query.Express> expressList = new ArrayList<REP_Query.Express>();
		Express express = new Express();
		express.setExpressID(2343);
		express.setExpress("asd");
		express.setPrice(new BigDecimal(343));
		expressList.add(express);
		rep.setExpressList(expressList);
		
		return rep;
	}
}