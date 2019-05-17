package com.fxbank.cap.ceba.trade.ceba;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.ceba.REP_BJCEBQBIRes;
import com.fxbank.cap.ceba.dto.ceba.REP_BJCEBQBIRes.Tout.Data;
import com.fxbank.cap.ceba.dto.ceba.REP_ERROR;
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
* @Description: 模拟光大银行查询缴费单信息
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
	
	private static final String ERR_BILLKEY = "12345";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_BJCEBQBIReq req = (REQ_BJCEBQBIReq) dto;
		
		if(ERR_BILLKEY.equals(req.getTin().getBillKey())) {
			REP_ERROR repError = new REP_ERROR();
			repError.getHead().setInstId(req.getHead().getInstId());
			repError.getHead().setAnsTranCode("Error");
			repError.getHead().setTrmSeqNum(req.getHead().getTrmSeqNum());
			repError.getTout().setErrorCode("DEF0002");
			return repError;
		}
		REP_BJCEBQBIRes rep = new REP_BJCEBQBIRes();
		rep.getHead().setInstId(req.getHead().getInstId());
		rep.getHead().setAnsTranCode("BJCEBQBIRes");
		rep.getHead().setTrmSeqNum(req.getHead().getTrmSeqNum());
		rep.getTout().setBillKey("123456");
		rep.getTout().setCompanyId("654321");
		rep.getTout().setTotalNum("2");
		List<Data> dataList = new ArrayList<Data>();
		for(int i=0;i<COUNT;i++) {
			Data data = new Data();
			data.setContractNo(i+"1");
			data.setCustomerName(i+"2");
			data.setBalance(new BigDecimal(55.55));
			data.setPayAmount(new BigDecimal(55.55));
			data.setBeginDate(i+"5");
			data.setEndDate(i+"6");
			dataList.add(data);
		}
		rep.getTout().setData(dataList);
		return rep;
	}
	
	
}