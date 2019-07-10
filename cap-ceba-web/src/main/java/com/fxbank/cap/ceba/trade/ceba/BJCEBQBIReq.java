package com.fxbank.cap.ceba.trade.ceba;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.ceba.dto.ceba.REQ_BJCEBQBIReq;
import com.fxbank.cap.ceba.model.REP_BJCEBQBIRes;
import com.fxbank.cap.ceba.model.REP_BJCEBQBIRes.Tout.Data;
import com.fxbank.cap.ceba.model.REP_ERROR;
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
	
	@Reference(version = "1.0.0")
	private IForwardToCebaService forwardToCebaService;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_BJCEBQBIReq req = (REQ_BJCEBQBIReq) dto;
		
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(ERR_BILLKEY.equals(req.getTin().getBillKey())) {
			REP_ERROR repError = new REP_ERROR(myLog,null,null,null);
			repError.getHead().setInstId(req.getHead().getInstId());
			repError.getHead().setAnsTranCode("Error");
			repError.getHead().setTrmSeqNum(req.getHead().getTrmSeqNum());
			repError.getTout().setErrorCode("DEF0002");
			forwardToCebaService.sendToCeba(repError);
			return req;
		}
		REP_BJCEBQBIRes rep = new REP_BJCEBQBIRes(myLog,0,0,0);
		rep.getHead().setInstId(req.getHead().getInstId());
		rep.getHead().setAnsTranCode("BJCEBQBIRes");
		rep.getHead().setTrmSeqNum(req.getHead().getTrmSeqNum());
		rep.getTout().setBillKey("123456");
		rep.getTout().setCompanyId("654321");
		
		List<Data> dataList = new ArrayList<Data>();
		if("1234567891".equals(req.getTin().getBillKey())) {
			Data data = new Data();
			data.setContractNo("123456");
			data.setCustomerName("张三");
			data.setBalance(new BigDecimal(0));
			data.setPayAmount(new BigDecimal(23.14));
			data.setBeginDate("20190329");
			data.setEndDate("20190428");
			dataList.add(data);
			Data data1 = new Data();
			data1.setContractNo("654321");
			data1.setCustomerName("张三");
			data1.setBalance(new BigDecimal(0));
			data1.setPayAmount(new BigDecimal(55.55));
			data1.setBeginDate("20190429");
			data1.setEndDate("20190528");
			dataList.add(data1);
		}else if("1234567892".equals(req.getTin().getBillKey())) {
			Data data = new Data();
			data.setContractNo("123456");
			data.setCustomerName("张三");
			data.setBalance(new BigDecimal(0));
			data.setPayAmount(new BigDecimal(23.14));
			data.setBeginDate("201904");
			data.setEndDate("");
			dataList.add(data);
			Data data1 = new Data();
			data1.setContractNo("654321");
			data1.setCustomerName("张三");
			data1.setBalance(new BigDecimal(0));
			data1.setPayAmount(new BigDecimal(55.55));
			data1.setBeginDate("201905");
			data1.setEndDate("");
			dataList.add(data1);
		}else if("1234567893".equals(req.getTin().getBillKey())) {
			Data data = new Data();
			data.setContractNo("123456");
			data.setCustomerName("张三");
			data.setBalance(new BigDecimal(0));
			data.setPayAmount(new BigDecimal(23.14));
			data.setBeginDate("");
			data.setEndDate("");
			dataList.add(data);
		}
		rep.getTout().setTotalNum(String.valueOf(dataList.size()));
		rep.getTout().setData(dataList);
		forwardToCebaService.sendToCeba(rep);
		return req;
	}
	
	
}