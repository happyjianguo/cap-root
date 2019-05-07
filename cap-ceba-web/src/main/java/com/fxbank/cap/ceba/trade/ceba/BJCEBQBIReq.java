package com.fxbank.cap.ceba.trade.ceba;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.ceba.REP_BJCEBQBIRes;
import com.fxbank.cap.ceba.dto.ceba.REP_BJCEBQBIRes.Tout.Data;
import com.fxbank.cap.ceba.dto.ceba.REQ_BJCEBQBIReq;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;


/** 
* @ClassName: BJCEBQBIReq 
* @Description: 模拟光大银行查询缴费单信息应答
* @作者 杜振铎
* @date 2019年5月7日 下午5:20:17 
*  
*/
@Service("BJCEBQBIReq")
public class BJCEBQBIReq implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(BJCEBQBIReq.class);

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";
	
	private static final Integer COUNT = 2;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_BJCEBQBIReq req = (REQ_BJCEBQBIReq) dto;
		REP_BJCEBQBIRes rep = new REP_BJCEBQBIRes();
		
		rep.getHead().setInstId("100000000000001");
		rep.getHead().setAnsTranCode("BJCEBQBIRes");
		rep.getHead().setTrmSeqNum("2010051000013010");
		rep.getTout().setBillKey("123456");
		rep.getTout().setCompanyId("654321");
		rep.getTout().setTotalNum("2");
		List<Data> dataList = new ArrayList<Data>();
		for(int i=0;i<COUNT;i++) {
			Data data = new Data();
			data.setContractNo(i+"1");
			data.setCustomerName(i+"2");
			data.setBalance(i+"3");
			data.setPayAmount(i+"4");
			data.setBeginDate(i+"5");
			data.setEndDate(i+"6");
			dataList.add(data);
		}
		rep.getTout().setData(dataList);
		return rep;
	}
	
	
}