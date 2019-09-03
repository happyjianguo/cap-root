package com.fxbank.cap.ceba.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.entity.CebaHostCheckLog;
import com.fxbank.cap.ceba.mapper.CebaHostCheckLogMapper;
import com.fxbank.cap.ceba.model.HostCheckLogInitModel;
import com.fxbank.cap.ceba.service.IHostCheckLogService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

@Service(validation = "true", version = "1.0.0")
public class HostCheckLogService implements IHostCheckLogService {
	
	@Resource
	private CebaHostCheckLogMapper mapper;

	@Override
	public void hostCheckLogInit(@Valid HostCheckLogInitModel model) throws SysTradeExecuteException {
		CebaHostCheckLog chkLog = new CebaHostCheckLog();
		
		chkLog.setAccountno(model.getAccountno());
		chkLog.setCcy(model.getCcy());
		chkLog.setHostDate(model.getHostDate());
		chkLog.setHostTraceno(model.getHostTraceno());
		chkLog.setTranType(model.getTranType());
		chkLog.setPlatDate(model.getSysDate());
		chkLog.setPlatTrace(model.getSysTraceno());
		chkLog.setReversal(model.getReversal());
		chkLog.setSettleBranch(model.getSettleBranch());
		chkLog.setTxAmt(model.getTxAmt());
		chkLog.setTxStatus(model.getTxStatus());
		chkLog.setSettleDate(model.getSettleDate());
		mapper.insertSelective(chkLog);
	}

	@Override
	public List<HostCheckLogInitModel> getHostCheckLog(MyLog myLog,Integer platDate) throws SysTradeExecuteException {
		CebaHostCheckLog chkLog = new CebaHostCheckLog();
		chkLog.setPlatDate(platDate);
		chkLog.setTxStatus("00");
		List<CebaHostCheckLog> chkLogList = mapper.select(chkLog);
		List<HostCheckLogInitModel> dayCheckLogInitModelList = new ArrayList<HostCheckLogInitModel>();
		for(CebaHostCheckLog log : chkLogList) {
			HostCheckLogInitModel model = new HostCheckLogInitModel(myLog,log.getPlatDate(),0,log.getPlatTrace());
			model.setAccountno(log.getAccountno());
			model.setCcy(log.getCcy());
			model.setHostDate(log.getHostDate());
			model.setHostTraceno(log.getHostTraceno());
			model.setTranType(log.getTranType());
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
