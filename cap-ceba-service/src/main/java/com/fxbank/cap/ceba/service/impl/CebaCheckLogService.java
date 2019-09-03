package com.fxbank.cap.ceba.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.entity.CebaCheckLog;
import com.fxbank.cap.ceba.mapper.CebaCheckLogMapper;
import com.fxbank.cap.ceba.model.CebaCheckLogInitModel;
import com.fxbank.cap.ceba.service.ICebaCheckLogService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

@Service(validation = "true", version = "1.0.0")
public class CebaCheckLogService implements ICebaCheckLogService {
	
	@Resource
	private CebaCheckLogMapper mapper;

	@Override
	public void cebaCheckLogInit(@Valid CebaCheckLogInitModel model) throws SysTradeExecuteException {
		CebaCheckLog cebaChkLog = new CebaCheckLog();
		
		cebaChkLog.setPlatDate(model.getSysDate());
		cebaChkLog.setPlatTrace(model.getSysTraceno());
		cebaChkLog.setPlatTime(model.getSysTime());
		cebaChkLog.setTxAmt(model.getTxAmt());
		cebaChkLog.setBankBillNo(model.getBankBillNo());
		mapper.insertSelective(cebaChkLog);
	}

	@Override
	public List<CebaCheckLogInitModel> getCebaCheckLog(MyLog myLog,Integer sysTime, Integer sysTraceno,Integer platDate) throws SysTradeExecuteException {
		CebaCheckLog cebaChkLog = new CebaCheckLog();
		cebaChkLog.setPlatDate(platDate);
		
		List<CebaCheckLog> cebaChkLogList = mapper.select(cebaChkLog);
		List<CebaCheckLogInitModel> dayCheckLogInitModelList = new ArrayList<CebaCheckLogInitModel>();
		for(CebaCheckLog log : cebaChkLogList) {
			CebaCheckLogInitModel model = new CebaCheckLogInitModel(myLog,log.getPlatDate(),log.getPlatTime(),log.getPlatTrace());
			model.setBankBillNo(log.getBankBillNo());
			model.setTxAmt(log.getTxAmt());
			dayCheckLogInitModelList.add(model);
		}
		
		return dayCheckLogInitModelList;
	}

	@Override
	public void delete(String platDate) throws SysTradeExecuteException {
		mapper.deleteAll(platDate);
	}

}
