package com.fxbank.cap.ykwm.trade.ykwm;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.ykwm.REP_ERROR;
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
* @作者 杜振铎
* @date 2019年4月29日 下午2:11:41 
*  
*/
@Service("REQ_Query")
public class Query implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(Query.class);

	private static final String NUM_REG = "[1-9]{1}\\d*$|[0-9]{1}$";
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Resource
	private LogPool logPool;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_Query req = (REQ_Query) dto;
		if(!req.getCardNum().matches(NUM_REG)) {
			REP_ERROR repErr = new REP_ERROR();
			repErr.setResult("1");
			repErr.setRepMsg("用户卡号错误");
			return repErr;
		}
		REP_Query rep = new REP_Query();
		rep.setOwnerName("张三");
		rep.setAddress("北京");
		rep.setMinPayment(new Double(100));
		rep.setTotal(new Double(500));
		rep.setUnit("aa");
		rep.setArea(new Double("120"));
		List<AccountDetail> data = new ArrayList<AccountDetail>();
		AccountDetail accountDetail = new AccountDetail();
		accountDetail.setChargeYear("2015-2016");
		accountDetail.setItemName("ds");
		accountDetail.setArea(new Double(120));
		accountDetail.setPrice(new Double(1.2));
		accountDetail.setAccount(new Double(200));
		accountDetail.setAgio(new Double(0));
        accountDetail.setLateFee(new Double(23));
        accountDetail.setPayment(new Double(500));
        data.add(accountDetail);
        AccountDetail accountDetail1 = new AccountDetail();
		accountDetail1.setChargeYear("2016-2017");
		accountDetail1.setItemName("都是");
		accountDetail1.setArea(new Double(10));
		accountDetail1.setPrice(new Double(1.24));
		accountDetail1.setAccount(new Double(260));
		accountDetail1.setAgio(new Double(1));
        accountDetail1.setLateFee(new Double(243));
        accountDetail1.setPayment(new Double(700));
        data.add(accountDetail1);
        rep.setCyc(data.size());
		rep.setData(data);
		rep.setDescription("sda");
		rep.setCheckNum("232424");
		rep.setExtend("");
		List<REP_Query.Express> expressList = new ArrayList<REP_Query.Express>();
		Express express0 = new Express();
		express0.setExpressID(2343);
		express0.setExpress("asd");
		express0.setPrice(new Double(343));
		expressList.add(express0);
		Express express1 = new Express();
		express1.setExpressID(5555);
		express1.setExpress("bbbb");
		express1.setPrice(new Double(373));
		expressList.add(express1);
		rep.setCyc1(expressList.size());
		rep.setExpressList(expressList);
		
		return rep;
	}
}