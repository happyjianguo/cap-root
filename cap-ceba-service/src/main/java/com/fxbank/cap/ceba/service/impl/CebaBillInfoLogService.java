package com.fxbank.cap.ceba.service.impl;

import javax.annotation.Resource;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.entity.CebaBillInfoLog;
import com.fxbank.cap.ceba.mapper.CebaBillInfoLogMapper;
import com.fxbank.cap.ceba.model.CebaBillInfoLogModel;
import com.fxbank.cap.ceba.service.ICebaBillInfoLogService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: CebaBillInfoLogService 
* @Description: 查询缴费单信息日志服务
* @作者 杜振铎
* @date 2019年5月14日 下午2:06:30 
*  
*/
@Service(validation = "true", version = "1.0.0")
public class CebaBillInfoLogService implements ICebaBillInfoLogService{
	
	@Resource
	private CebaBillInfoLogMapper cebaBillInfoLogMapper;

	@Override
	public void querySuccessInit(@Valid CebaBillInfoLogModel record) throws SysTradeExecuteException {
		CebaBillInfoLog cebaBillInfoLog = new CebaBillInfoLog();
		cebaBillInfoLog.setSeqNo(record.getSeqNo());
		cebaBillInfoLog.setBillKey(record.getBillKey());
		cebaBillInfoLog.setCompanyId(record.getCompanyId());
		cebaBillInfoLog.setContractNo(record.getContractNo());
		cebaBillInfoLog.setCustomerName(record.getCustomerName());
		cebaBillInfoLog.setBalance(record.getBalance());
		cebaBillInfoLog.setPayAmount(record.getPayAmount());
		cebaBillInfoLog.setBeginDate(record.getBeginDate());
		cebaBillInfoLog.setEndDate(record.getEndDate());
		cebaBillInfoLog.setItem1(record.getItem1());
		cebaBillInfoLog.setItem2(record.getItem2());
		cebaBillInfoLog.setItem3(record.getItem3());
		cebaBillInfoLog.setItem4(record.getItem4());
		cebaBillInfoLog.setItem5(record.getItem5());
		cebaBillInfoLog.setItem6(record.getItem6());
		cebaBillInfoLog.setItem7(record.getItem7());
		cebaBillInfoLog.setFiled1(record.getFiled1());
		cebaBillInfoLog.setFiled2(record.getFiled2());
		cebaBillInfoLog.setFiled3(record.getFiled3());
		cebaBillInfoLog.setFiled4(record.getFiled4());
		cebaBillInfoLog.setFiled5(record.getFiled5());
		
		cebaBillInfoLogMapper.insertSelective(cebaBillInfoLog);
	}

	/** 
	* @Title: getBillInfoBySeqNo 
	* @Description: 通过查询流水号获取缴费单信息日志
	* @param @param seqNo
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public CebaBillInfoLogModel getBillInfoBySeqNo(MyLog myLog,String seqNo) throws SysTradeExecuteException {
		CebaBillInfoLogModel model = new CebaBillInfoLogModel(myLog,null,null,null);
		CebaBillInfoLog info = new CebaBillInfoLog();
		info.setSeqNo(seqNo);
		CebaBillInfoLog result = cebaBillInfoLogMapper.selectByPrimaryKey(info);
		model.setSeqNo(seqNo);
		model.setBillKey(result.getBillKey());
		model.setCompanyId(result.getCompanyId());
		model.setContractNo(result.getContractNo());
		model.setCustomerName(result.getCustomerName());
		model.setBalance(result.getBalance());
		model.setPayAmount(result.getPayAmount());
		model.setBeginDate(result.getBeginDate());
		model.setEndDate(result.getEndDate());
		model.setItem1(result.getItem1());
	    model.setItem2(result.getItem2());
	    model.setItem3(result.getItem3());
	    model.setItem4(result.getItem4());
	    model.setItem5(result.getItem5());
	    model.setItem6(result.getItem6());
	    model.setItem7(result.getItem7());
	    model.setFiled1(result.getFiled1());
	    model.setFiled2(result.getFiled2());
	    model.setFiled3(result.getFiled3());
	    model.setFiled4(result.getFiled4());
	    model.setFiled5(result.getFiled5());
		return model;
	}
	
}
