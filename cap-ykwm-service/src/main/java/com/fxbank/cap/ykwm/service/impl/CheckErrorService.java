package com.fxbank.cap.ykwm.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ykwm.entity.YkwmErrorlog;
import com.fxbank.cap.ykwm.mapper.YkwmErrorlogMapper;
import com.fxbank.cap.ykwm.model.CheckErrorModel;
import com.fxbank.cap.ykwm.service.ICheckErrorService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

@Service(validation = "true", version = "1.0.0")
public class CheckErrorService implements ICheckErrorService {
	
	@Resource
	private YkwmErrorlogMapper mapper;

	@Override
	public List<CheckErrorModel> getListByDate(MyLog myLog,Integer sysTime, Integer sysDate,Integer sysTraceno,String date) throws SysTradeExecuteException {
		List<YkwmErrorlog> list = mapper.selectByDate(date);
		List<CheckErrorModel> modelList = new ArrayList<>();
		for(YkwmErrorlog log : list) {
			CheckErrorModel model = new CheckErrorModel(myLog, sysDate, sysTime, sysTraceno);
			model.setPlatDate(log.getPlatDate());
			model.setPlatTrace(log.getPlatTrace());
			model.setPreHostState(log.getPreHostState());
			model.setReHostState(log.getReHostState());
			model.setCheckFlag(log.getCheckFlag());
			model.setTxAmt(log.getTxAmt());
			model.setUserName(log.getUserName());
			model.setAcctNo(log.getAcctNo());
			model.setUserCard(log.getUserCard());
			modelList.add(model);
		}
		return modelList;
	}

	@Override
	public void insert(CheckErrorModel model) throws SysTradeExecuteException {
		YkwmErrorlog log = new YkwmErrorlog();
		log.setPlatDate(model.getPlatDate());
		log.setPlatTrace(model.getPlatTrace());
		log.setPreHostState(model.getPreHostState());
		log.setReHostState(model.getReHostState());
		log.setCheckFlag(model.getCheckFlag());
		log.setTxAmt(model.getTxAmt());
		log.setUserName(model.getUserName());
		log.setUserCard(model.getUserCard());
		log.setAcctNo(model.getAcctNo());
		mapper.insertSelective(log);
	}

	@Override
	public void delete(String date) throws SysTradeExecuteException {
		mapper.deleteByDate(date);
	}

}
