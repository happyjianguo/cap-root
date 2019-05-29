package com.fxbank.cap.ykwm.trade.ykwm;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cap.ykwm.dto.ykwm.REP_ERROR;
import com.fxbank.cap.ykwm.dto.ykwm.REP_QueryByOther;
import com.fxbank.cap.ykwm.dto.ykwm.REP_QueryByOther.Info;
import com.fxbank.cap.ykwm.dto.ykwm.REQ_QueryByOther;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("REQ_QueryByOther")
public class QueryByOther implements TradeExecutionStrategy {

	private static Logger logger = LoggerFactory.getLogger(QueryByOther.class);

	private static final String NUM_REG = "[1-9]{1}\\d*$|[0-9]{1}$";
	
	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Resource
	private LogPool logPool;

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_QueryByOther req = (REQ_QueryByOther) dto;
		if(!req.getCompanyID().matches(NUM_REG)) {
			REP_ERROR repErr = new REP_ERROR();
			repErr.setResult("1");
			repErr.setRepMsg("用户卡号错误");
			return repErr;
		}
		REP_QueryByOther rep = new REP_QueryByOther();
		List<Info> infoList = new ArrayList<Info>();
		Info info = new Info();
		info.setOwnerName("张三");
		info.setAddress("沈阳");
		info.setCardNum("11111");
		infoList.add(info);
		Info info1 = new Info();
		info1.setOwnerName("李四");
		info1.setAddress("大连");
		info1.setCardNum("22222");
		infoList.add(info1);
        rep.setCyc(infoList.size());
		rep.setInfoList(infoList);
		
		return rep;
	}
}