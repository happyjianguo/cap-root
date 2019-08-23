package com.fxbank.cap.ceba.trade.esb;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.ceba.dto.esb.REP_30063001401;
import com.fxbank.cap.ceba.dto.esb.REP_30063001401.DataInfo;
import com.fxbank.cap.ceba.exception.CebaTradeExecuteException;
import com.fxbank.cap.ceba.dto.esb.REQ_30063001401;
import com.fxbank.cap.ceba.model.CebaBillInfoLogModel;
import com.fxbank.cap.ceba.model.CebaOutageLogModel;
import com.fxbank.cap.ceba.model.REP_BJCEBQBIRes;
import com.fxbank.cap.ceba.model.REP_BJCEBQBIRes.Tout.Data;
import com.fxbank.cap.ceba.model.REQ_BJCEBQBIReq;
import com.fxbank.cap.ceba.service.ICebaBillInfoLogService;
import com.fxbank.cap.ceba.service.ICebaOutageLogService;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.esb.service.IForwardToESBService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
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
	private ICebaBillInfoLogService cebaBillInfoLogService;
	
	@Reference(version = "1.0.0")
	private ICebaOutageLogService cebaOutageLogService;

	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "ceba.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30063001401 reqDto = (REQ_30063001401) dto;
		REQ_30063001401.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30063001401 rep = new REP_30063001401();
		//查询是否停运
		String companyId = reqBody.getPyCityCode() + reqBody.getPyCreditNo();
		CebaOutageLogModel cebaOutageLogModel = cebaOutageLogService.isOutageByCompanyId(myLog, companyId, Long.parseLong(reqDto.getSysDate().toString()+reqDto.getSysTime().toString()));
		if(null!=cebaOutageLogModel) {
			myLog.error(logger, "缴费项目编号"+companyId+"已停运");
			CebaTradeExecuteException e = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10014,
					"该缴费项目已停运，停运起始时间："+cebaOutageLogModel.getOutageBeginTime()
					+",停运终止时间："+cebaOutageLogModel.getOutageEndTime());
		    throw e;
		}
		REQ_BJCEBQBIReq req = new REQ_BJCEBQBIReq(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		//商户机构号
		String instld = null;
		//备用字段filed3必填，传城市代码
		String companyListFiled3 = null;
		//备用字段filed3必填，传城市代码，铁岭特殊，城市代码和沈阳一样是024，但是传410
		String companyListFiled3s = null;
		try (Jedis jedis = myJedis.connect()) {
			instld = jedis.get(COMMON_PREFIX + "ceba_instld");
			companyListFiled3 = jedis.get(COMMON_PREFIX + "company_list_filed3");
			companyListFiled3s = jedis.get(COMMON_PREFIX + "company_list_filed3s");
			
		}
		req.getHead().setInstId(instld);
		req.getHead().setAnsTranCode("BJCEBQBIReq");
		req.getHead().setTrmSeqNum(dto.getSysDate().toString() + dto.getSysTraceno());
		req.getTin().setBillKey(reqBody.getBillKey());
		req.getTin().setCompanyId(companyId);
		req.getTin().setQueryNum("".equals(reqBody.getQueryNum())?"1":reqBody.getQueryNum());
		req.getTin().setBeginNum("".equals(reqBody.getStartNum())?"1":reqBody.getStartNum());
		JSONObject companyListFiled3Json= JSONObject.parseObject(companyListFiled3);
		JSONObject companyListFiled3sJson= JSONObject.parseObject(companyListFiled3s);
		if (companyListFiled3Json.get(companyId)!=null) {
			req.getTin().setFiled3(reqBody.getPyCityCode());
		}
		if (companyListFiled3sJson.get(companyId)!=null) {
			req.getTin().setFiled3("410");
		}
		REP_BJCEBQBIRes res = (REP_BJCEBQBIRes) forwardToCebaService.sendToCeba(req);

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
		querySuccessInit(myLog, reqDto, res);
		myLog.info(logger, "查询缴费单信息成功，渠道日期" + reqDto.getSysDate() + "渠道流水号" + reqDto.getSysTraceno());
		return rep;

	}

	public void querySuccessInit(MyLog myLog, REQ_30063001401 reqDto, REP_BJCEBQBIRes res)
			throws SysTradeExecuteException {
		CebaBillInfoLogModel record = new CebaBillInfoLogModel(myLog, reqDto.getSysDate(), reqDto.getSysTime(),
				reqDto.getSysTraceno());
		REQ_30063001401.REQ_BODY reqBody = reqDto.getReqBody();
		REP_BJCEBQBIRes.Tout tout = res.getTout();
		REP_BJCEBQBIRes.Tout.Data data = tout.getData().get(0);
		record.setSeqNo(reqDto.getReqSysHead().getSeqNo());
		record.setBillKey(reqBody.getBillKey());
		record.setCompanyId(reqBody.getPyCityCode() + reqBody.getPyCreditNo());
		record.setContractNo(data.getContractNo());
		record.setCustomerName(data.getCustomerName());
		record.setBalance(data.getBalance());
		record.setPayAmount(data.getPayAmount());
		record.setBeginDate(data.getBeginDate());
		record.setEndDate(data.getEndDate());
		record.setItem1(tout.getItem1());
		record.setItem2(tout.getItem2());
		record.setItem3(tout.getItem3());
		record.setItem4(tout.getItem4());
		record.setItem5(tout.getItem5());
		record.setItem6(tout.getItem6());
		record.setItem7(tout.getItem7());
		record.setFiled1(data.getFiled1());
		record.setFiled2(data.getFiled2());
		record.setFiled3(data.getFiled3());
		record.setFiled4(data.getFiled4());
		record.setFiled5(data.getFiled5());
		cebaBillInfoLogService.querySuccessInit(record);
	}
}
