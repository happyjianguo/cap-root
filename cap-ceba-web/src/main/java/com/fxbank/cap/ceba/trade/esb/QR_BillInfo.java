package com.fxbank.cap.ceba.trade.esb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.ceba.dto.ceba.DTO_BASE;
import com.fxbank.cap.ceba.dto.ceba.REP_BJCEBQBIRes;
import com.fxbank.cap.ceba.dto.ceba.REP_ERROR;
import com.fxbank.cap.ceba.dto.ceba.REP_BJCEBQBIRes.Tout.Data;
import com.fxbank.cap.ceba.dto.esb.REP_30063001401;
import com.fxbank.cap.ceba.dto.esb.REP_30063001401.DataInfo;
import com.fxbank.cap.ceba.dto.esb.REQ_30063001401;
import com.fxbank.cap.ceba.model.ErrorInfo;
import com.fxbank.cap.ceba.model.REQ_BJCEBQBIReq;
import com.fxbank.cap.ceba.model.REQ_BJCEBRWKReq;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.base.util.JsonUtil;
import com.fxbank.cip.pub.service.IPublicService;
import com.fxbank.cap.ceba.sync.SyncCom;
import com.fxbank.cap.ceba.util.JAXBUtils;

import redis.clients.jedis.Jedis;

/**
 * @ClassName: QueryBillInfo
 * @Description: 查询缴费单信息
 * @作者 杜振铎
 * @date 2019年5月7日 下午5:21:07
 * 
 */
@Service("REQ_30063001401")
public class QR_BillInfo extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(QR_BillInfo.class);

	@Resource
	private LogPool logPool;

	@Reference(version = "1.0.0")
	private IForwardToESBService forwardToESBService;

	@Reference(version = "1.0.0")
	private IForwardToCebaService forwardToCebaService;

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Resource
	private MyJedis myJedis;

	@Resource
	private SyncCom syncCom;

	private final static String COMMON_PREFIX = "ceba.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30063001401 reqDto = (REQ_30063001401) dto;
		REQ_30063001401.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30063001401 rep = new REP_30063001401();
		REQ_BJCEBQBIReq req = new REQ_BJCEBQBIReq(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		String instld = null;
		try (Jedis jedis = myJedis.connect()) {
			instld = jedis.get(COMMON_PREFIX + "ceba_instld");
		}
		req.getHead().setInstId(instld);
		req.getHead().setAnsTranCode("BJCEBQBIReq");
		req.getHead().setTrmSeqNum(publicService.getSysDate("CIP").toString() + publicService.getSysTraceno());
		req.getTin().setBillKey(reqBody.getBillKey());
		req.getTin().setCompanyId(reqBody.getPyCityCode() + reqBody.getPyCreditNo());
		req.getTin().setQueryNum(reqBody.getQueryNum());
		req.getTin().setBeginNum(reqBody.getStartNum());
		req = (REQ_BJCEBQBIReq) forwardToCebaService.sendToCeba(req);
		String channel = req.getHead().getTrmSeqNum();
		myLog.info(logger, "查询缴费单信息报文发送通道编号=[" + channel);
		DTO_BASE dtoBase = syncCom.get(myLog, channel, 55, TimeUnit.SECONDS);
		if (dtoBase.getHead().getAnsTranCode().equals("Error")) {
			REP_ERROR repError = (REP_ERROR) dtoBase;
			String errorCode = repError.getTout().getErrorCode();
			String jsonStr = null;
			try (Jedis jedis = myJedis.connect()) {
				jsonStr = jedis.get(COMMON_PREFIX + "ceba_error_list");
			}
			if (jsonStr == null || jsonStr.length() == 0) {
				logger.error("渠道未配置[" + COMMON_PREFIX + "ceba_error_list" + "]");
				String errorMsg = repError.getTout().getErrorMessage();
				SysTradeExecuteException e = new SysTradeExecuteException(errorCode, errorMsg);
				myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
				throw e;
			}
			Map<String, ErrorInfo> map = (Map) JSONObject.parse(jsonStr);
			ErrorInfo errorInfo = JsonUtil.toBean(JSON.toJSONString(map.get(errorCode)), ErrorInfo.class);
			String errorMsg = null;
			errorMsg = errorInfo.getQrErrorMsg();
			SysTradeExecuteException e = new SysTradeExecuteException(errorCode, errorMsg);
			myLog.error(logger, e.getRspCode() + " | " + e.getRspMsg());
			throw e;
		} else if (dtoBase.getHead().getAnsTranCode().equals("BJCEBQBIRes")) {
			REP_BJCEBQBIRes res = (REP_BJCEBQBIRes) dtoBase;
			rep.getRepBody().setTotalNum(res.getTout().getTotalNum());
			List<DataInfo> dataList = new ArrayList<DataInfo>();
			if (dataList != null) {
				for (Data data : res.getTout().getData()) {
					DataInfo temp = new DataInfo();
					temp.setContractNo(data.getContractNo());
					temp.setClientNnae(data.getCustomerName());
					temp.setBalance(data.getBalance().toString());
					temp.setUnpaidAmt(data.getPayAmount().toString());
					temp.setStartDate(data.getBeginDate());
					temp.setEndDate(data.getEndDate());
					dataList.add(temp);
				}
				rep.getRepBody().setPyInfoArray(dataList);
			}
		}

		myLog.info(logger, "查询缴费单信息成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
		return rep;
	}

}
