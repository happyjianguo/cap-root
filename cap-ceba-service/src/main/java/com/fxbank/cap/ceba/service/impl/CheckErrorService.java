package com.fxbank.cap.ceba.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.entity.CebaErrorLog;
import com.fxbank.cap.ceba.mapper.CebaErrorLogMapper;
import com.fxbank.cap.ceba.model.CheckErrorModel;
import com.fxbank.cap.ceba.service.ICheckErrorService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

@Service(validation = "true", version = "1.0.0")
public class CheckErrorService implements ICheckErrorService {
	
	@Resource
	private CebaErrorLogMapper mapper;

	@Override
	public List<CheckErrorModel> getListByDate(MyLog myLog,Integer sysTime, Integer sysDate,Integer sysTraceno,String date) throws SysTradeExecuteException {
		List<CebaErrorLog> list = mapper.selectByDate(date);
		List<CheckErrorModel> modelList = new ArrayList<>();
		for(CebaErrorLog log : list) {
			CheckErrorModel model = new CheckErrorModel(myLog, log.getPlatDate(), log.getPlatTime(), log.getPlatTrace());
			model.setPrePayState(log.getPrePayState());
			model.setRePayState(log.getRePayState());
			model.setCheckFlag(log.getCheckFlag());
			model.setTxAmt(log.getTxAmt());
			model.setRemark(log.getRemark());
			modelList.add(model);
		}
		return modelList;
	}

	@Override
	public void insert(CheckErrorModel model) throws SysTradeExecuteException {
		CebaErrorLog log = new CebaErrorLog();
		log.setPlatDate(model.getSysDate());
		log.setPlatTrace(model.getSysTraceno());
		log.setPlatTime(model.getSysTime());
		log.setPrePayState(model.getPrePayState());
		log.setRePayState(model.getRePayState());
		log.setCheckFlag(model.getCheckFlag());
		log.setTxAmt(model.getTxAmt());
		log.setRemark(model.getRemark());
		mapper.insertSelective(log);
	}

	@Override
	public void delete(String date) throws SysTradeExecuteException {
		mapper.deleteByDate(date);
	}

}
