package com.fxbank.cap.ceba.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.entity.CebaCheckLog;
import com.fxbank.cap.ceba.mapper.CebaCheckLogMapper;
import com.fxbank.cap.ceba.model.DayCheckLogInitModel;
import com.fxbank.cap.ceba.service.IDayCheckLogService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

@Service(validation = "true", version = "1.0.0")
public class DayCheckLogService implements IDayCheckLogService {
	
	@Resource
	private CebaCheckLogMapper mapper;

	@Override
	public void dayCheckLogInit(@Valid DayCheckLogInitModel model) throws SysTradeExecuteException {
		CebaCheckLog cebaChkLog = new CebaCheckLog();
		
		cebaChkLog.setPlatDate(model.getSysDate());
		cebaChkLog.setPlatTrace(model.getSysTraceno());
		cebaChkLog.setPlatTime(model.getSysTime());
		cebaChkLog.setTxAmt(model.getTxAmt());
		cebaChkLog.setBankBillNo(model.getBankBillNo());
		mapper.insertSelective(cebaChkLog);
	}

	@Override
	public List<DayCheckLogInitModel> getDayCheckLog(MyLog myLog,Integer sysTime, Integer sysTraceno,Integer platDate) throws SysTradeExecuteException {
		CebaCheckLog cebaChkLog = new CebaCheckLog();
		cebaChkLog.setPlatDate(platDate);
		
		List<CebaCheckLog> cebaChkLogList = mapper.select(cebaChkLog);
		List<DayCheckLogInitModel> dayCheckLogInitModelList = new ArrayList<DayCheckLogInitModel>();
		for(CebaCheckLog log : cebaChkLogList) {
			DayCheckLogInitModel model = new DayCheckLogInitModel(myLog,log.getPlatDate(),log.getPlatTime(),log.getPlatTrace());
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
