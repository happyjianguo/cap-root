package com.fxbank.cap.ceba.trade.esb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.esb.REP_30042000901;
import com.fxbank.cap.ceba.dto.esb.REP_30042000901.DataInfo;
import com.fxbank.cap.ceba.dto.esb.REQ_30042000901;
import com.fxbank.cap.ceba.model.REP_BJCEBQBIRes;
import com.fxbank.cap.ceba.model.REP_BJCEBQBIRes.Tout.Data;
import com.fxbank.cap.ceba.model.REQ_BJCEBQBIReq;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;


/** 
* @ClassName: QueryBillInfo 
* @Description: 柜面查询缴费单信息 
* @作者 杜振铎
* @date 2019年5月7日 下午5:21:07 
*  
*/
@Service("REQ_30042000901")
public class QR_BillInfo extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(QR_BillInfo.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToCebaService forwardToCebaService;

	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30042000901 reqDto = (REQ_30042000901) dto;
		REQ_30042000901.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30042000901 rep = new REP_30042000901();		
		REQ_BJCEBQBIReq req = new REQ_BJCEBQBIReq(myLog, reqDto.getSysDate(), reqDto.getSysTime(), reqDto.getSysTraceno());
		req.getHead().setInstId("100000000000001");
		req.getHead().setAnsTranCode("BJCEBQBIReq");
		req.getHead().setTrmSeqNum("2010051000013010");
		req.getTin().setBillKey(reqBody.getBillKey());
		req.getTin().setCompanyId(reqBody.getProjCode()+reqBody.getProjCode());
		req.getTin().setQueryNum(reqBody.getQueryNum());
		REP_BJCEBQBIRes res = forwardToCebaService.sendToCeba(req, 
				REP_BJCEBQBIRes.class);
		rep.getRepBody().setBillKey(res.getTout().getBillKey());
		rep.getRepBody().setCompanyId(res.getTout().getCompanyId());
		rep.getRepBody().setTotalNum(res.getTout().getTotalNum());
		List<DataInfo> dataList = new ArrayList<DataInfo>();
		for(Data data:res.getTout().getData()) {
			DataInfo temp = new DataInfo();
			temp.setContractNo(data.getContractNo());
			temp.setCustomerName(data.getCustomerName());
			temp.setBalance(data.getBalance());
			temp.setPayAmount(data.getPayAmount());
			temp.setBeginDate(data.getBeginDate());
			temp.setEndDate(data.getEndDate());
			dataList.add(temp);
		}
		rep.getRepBody().setDataArray(dataList);
		return rep;
	}


	
	
	
	
}
