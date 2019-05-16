package com.fxbank.cap.ceba.trade.esb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.ceba.dto.esb.REP_30063001403;
import com.fxbank.cap.ceba.dto.esb.REP_30063001403.DataInfo;
import com.fxbank.cap.ceba.exception.CebaTradeExecuteException;
import com.fxbank.cap.ceba.dto.esb.REQ_30063001403;
import com.fxbank.cap.ceba.model.CityInfo;
import com.fxbank.cap.ceba.model.CityInfo.CompanyInfo;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.route.trade.TradeExecutionStrategy;
import com.fxbank.cip.base.util.JsonUtil;

import redis.clients.jedis.Jedis;



/** 
* @ClassName: QR_CompInfo 
* @Description:收费单位查询 
* @作者 杜振铎
* @date 2019年5月16日 下午2:37:08 
*  
*/
@Service("REQ_30063001403")
public class QR_CompInfo extends TradeBase implements TradeExecutionStrategy {
	private static Logger logger = LoggerFactory.getLogger(QR_CompInfo.class);

	@Resource
	private LogPool logPool;
	
	@Resource
	private MyJedis myJedis;
	
	private final static String COMMON_PREFIX = "ceba.";

	@Override
	public DataTransObject execute(DataTransObject dto) throws SysTradeExecuteException {
		MyLog myLog = logPool.get();
		REQ_30063001403 reqDto = (REQ_30063001403) dto;
		REQ_30063001403.REQ_BODY reqBody = reqDto.getReqBody();
		REP_30063001403 rep = new REP_30063001403();	
		REP_30063001403.REP_BODY repBody = rep.getRepBody();
		String jsonStr = null;
		try(Jedis jedis = myJedis.connect()){
			jsonStr = jedis.get(COMMON_PREFIX+"ceba_city_list");
        }
		if(jsonStr==null||jsonStr.length()==0){
			logger.error("渠道未配置["+COMMON_PREFIX + "ceba_city_list"+"]");
			throw new RuntimeException("渠道未配置["+COMMON_PREFIX + "ceba_city_list"+"]");
		}
		Map<String,CityInfo> map = (Map)JSONObject.parse(jsonStr);
		CityInfo cityInfo = JsonUtil.toBean(JSON.toJSONString(map.get(reqBody.getPyCityCode())),CityInfo.class);
		if(cityInfo==null) {
			CebaTradeExecuteException e = new CebaTradeExecuteException(CebaTradeExecuteException.CEBA_E_10004);
			throw e;
		}
	    Map<String,List<CityInfo>> map1 = (Map)JSONObject.parse(JsonUtil.toJson(cityInfo));
	    List<CompanyInfo> list = JsonUtil.toBean(JSON.toJSONString(map1.get("ARRAY"+reqBody.getPyCreditType())),List.class);
	    if(list == null) {
	    	list = new ArrayList<CompanyInfo>();
	    }
	    repBody.setRecCompanyNum(String.valueOf(list.size()));
	    List<DataInfo> dataList = new ArrayList<DataInfo>();
	    for(int i=0,size=list.size();i<size;i++) {
	    	CompanyInfo temp = JsonUtil.toBean(JSON.toJSONString(list.get(i)),CompanyInfo.class);
	    	DataInfo data = new DataInfo();
	    	data.setRecCompanyName(temp.getCompanyName());
	    	data.setPyCreditNo(temp.getProjCode());
	    	data.setPyCreditName(temp.getProjName());
	    	dataList.add(data);
	    }
	    repBody.setRecCompanyArray(dataList);
		myLog.info(logger, "查询收费单位成功，渠道日期"+reqDto.getSysDate()+"渠道流水号"+reqDto.getSysTraceno());
		return rep;
	}
}
