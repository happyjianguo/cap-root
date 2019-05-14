package com.fxbank.cap.ceba.service.impl;

import javax.annotation.Resource;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.entity.BillChargeLog;
import com.fxbank.cap.ceba.mapper.BillChargeLogMapper;
import com.fxbank.cap.ceba.model.BillChargeLogModel;
import com.fxbank.cap.ceba.service.IBillChargeLogService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

/** 
* @ClassName: BillChargeLogService 
* @Description: 缴费单销账日志服务
* @作者 杜振铎
* @date 2019年5月14日 下午2:06:30 
*  
*/
@Service(validation = "true", version = "1.0.0")
public class BillChargeLogService implements IBillChargeLogService{
	
	@Resource
	private BillChargeLogMapper billChargeLogMapper;

	/** 
	* @Title: logInit 
	* @Description: 缴费单销账登记 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void logInit(@Valid BillChargeLogModel record) throws SysTradeExecuteException {
		BillChargeLog billChargeLog = new BillChargeLog();
		billChargeLog.setPlatDate(record.getSysDate());
		billChargeLog.setPlatTime(record.getSysTime());
		billChargeLog.setPlatTrace(record.getSysTraceno());
		billChargeLog.setSourceType(record.getSourceType());
		billChargeLog.setTxBranch(record.getTxBranch());
		billChargeLog.setBillKey(record.getBillKey());
		billChargeLog.setCompanyId(record.getCompanyId());
		billChargeLog.setCustomerName(record.getCustomerName());
		billChargeLog.setPayAccount(record.getPayAccount());
		billChargeLog.setPayAmount(record.getPayAmount());
		billChargeLog.setAcType(record.getAcType());
		billChargeLog.setContractNo(record.getContractNo());
		billChargeLog.setPayState("0");
		billChargeLog.setCheckState("0");
		billChargeLog.setBillNo(record.getBillNo());
		billChargeLog.setPayDate(record.getPayDate());
		billChargeLog.setHostState("0");
		billChargeLog.setHostDate(record.getHostDate());
		billChargeLog.setHostTraceno(record.getHostTraceNo());
		billChargeLog.setHostRetCode(record.getHostRetCode());
		billChargeLog.setHostRetMsg(record.getHostRetMsg());
		
		billChargeLogMapper.insert(billChargeLog);
	}

	/** 
	* @Title: logUpd 
	* @Description: 缴费单销账更新日志
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void logUpd(@Valid BillChargeLogModel record) throws SysTradeExecuteException {
		BillChargeLog billChargeLog = new BillChargeLog();
		billChargeLog.setPlatDate(record.getSysDate());
		billChargeLog.setPlatTrace(record.getSysTraceno());
		billChargeLog.setBankBillNo(record.getBankBillNo());
		billChargeLog.setReceiptNo(record.getReceiptNo());
		billChargeLog.setAcctDate(record.getAcctDate());
		billChargeLog.setPayState(record.getPayState());
		billChargeLog.setErrorCode(record.getErrorCode());
		billChargeLog.setHostDate(record.getHostDate());
		billChargeLog.setHostTraceno(record.getHostTraceNo());
		billChargeLog.setHostState(record.getHostState());
		billChargeLog.setHostRetCode(record.getHostRetCode());
		billChargeLog.setHostRetMsg(record.getHostRetMsg());
		billChargeLogMapper.updateByPrimaryKeySelective(billChargeLog);
		
	}

}
