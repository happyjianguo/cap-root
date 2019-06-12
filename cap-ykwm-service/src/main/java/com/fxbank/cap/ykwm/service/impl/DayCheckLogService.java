package com.fxbank.cap.ykwm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ykwm.entity.YkwmChecklog;
import com.fxbank.cap.ykwm.mapper.YkwmChecklogMapper;
import com.fxbank.cap.ykwm.model.DayCheckLogInitModel;
import com.fxbank.cap.ykwm.service.IDayCheckLogService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

@Service(validation = "true", version = "1.0.0")
public class DayCheckLogService implements IDayCheckLogService {
	
	@Resource
	private YkwmChecklogMapper mapper;

	@Override
	public void dayCheckLogInit(@Valid DayCheckLogInitModel model) throws SysTradeExecuteException {
		YkwmChecklog tcexChkLog = new YkwmChecklog();
		
		tcexChkLog.setAccountno(model.getAccountno());
		tcexChkLog.setCcy(model.getCcy());
		tcexChkLog.setHostDate(model.getHostDate());
		tcexChkLog.setHostTraceno(model.getHostTraceno());
		tcexChkLog.setPlatDate(model.getSysDate());
		tcexChkLog.setPlatTrace(model.getSysTraceno());
		tcexChkLog.setReversal(model.getReversal());
		tcexChkLog.setSettleBranch(model.getSettleBranch());
		tcexChkLog.setTxAmt(model.getTxAmt());
		tcexChkLog.setTxStatus(model.getTxStatus());
		tcexChkLog.setSettleDate(model.getSettleDate());
		mapper.insertSelective(tcexChkLog);
	}

	@Override
	public List<DayCheckLogInitModel> getDayCheckLog(MyLog myLog,Integer sysTime, Integer sysTraceno,Integer platDate) throws SysTradeExecuteException {
		YkwmChecklog ykwmChkLog = new YkwmChecklog();
		ykwmChkLog.setPlatDate(platDate);
		
		List<YkwmChecklog> ykwmChkLogList = mapper.select(ykwmChkLog);
		List<DayCheckLogInitModel> dayCheckLogInitModelList = new ArrayList<DayCheckLogInitModel>();
		for(YkwmChecklog log : ykwmChkLogList) {
			DayCheckLogInitModel model = new DayCheckLogInitModel(myLog,platDate,sysTime,log.getPlatTrace());
			model.setAccountno(log.getAccountno());
			model.setCcy(log.getCcy());
			model.setHostDate(log.getHostDate());
			model.setHostTraceno(log.getHostTraceno());
			model.setReversal(log.getReversal());
			model.setSettleBranch(log.getSettleBranch());
			model.setSettleDate(log.getSettleDate());
			model.setTxAmt(log.getTxAmt());
			model.setTxStatus(log.getTxStatus());
			dayCheckLogInitModelList.add(model);
		}
		
		return dayCheckLogInitModelList;
	}

	@Override
	public void delete(String platDate) throws SysTradeExecuteException {
		mapper.deleteAll(platDate);
	}

}
