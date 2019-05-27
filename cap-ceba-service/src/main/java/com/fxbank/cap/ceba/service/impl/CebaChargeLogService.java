package com.fxbank.cap.ceba.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.entity.CebaChargeLog;
import com.fxbank.cap.ceba.mapper.CebaChargeLogMapper;
import com.fxbank.cap.ceba.model.CebaChargeLogModel;
import com.fxbank.cap.ceba.service.ICebaChargeLogService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: CebaChargeLogService 
* @Description: 缴费单销账日志服务
* @作者 杜振铎
* @date 2019年5月14日 下午2:06:30 
*  
*/
@Service(validation = "true", version = "1.0.0")
public class CebaChargeLogService implements ICebaChargeLogService{
	
	@Resource
	private CebaChargeLogMapper cebaChargeLogMapper;

	@Override
	public void hostSuccessInit(@Valid CebaChargeLogModel record) throws SysTradeExecuteException {
		CebaChargeLog cebaChargeLog = new CebaChargeLog();
		cebaChargeLog.setPlatDate(record.getSysDate());
		cebaChargeLog.setPlatTime(record.getSysTime());
		cebaChargeLog.setPlatTrace(record.getSysTraceno());
		cebaChargeLog.setSourceType(record.getSourceType());
		cebaChargeLog.setTxBranch(record.getTxBranch());
		cebaChargeLog.setTxTel(record.getTxTel());
		cebaChargeLog.setBillKey(record.getBillKey());
		cebaChargeLog.setCompanyId(record.getCompanyId());
		cebaChargeLog.setCustomerName(record.getCustomerName());
		cebaChargeLog.setPayAccount(record.getPayAccount());
		cebaChargeLog.setPayAmount(record.getPayAmount());
		cebaChargeLog.setAcType(record.getAcType());
		cebaChargeLog.setContractNo(record.getContractNo());
		cebaChargeLog.setPayState("0");
		cebaChargeLog.setCheckState("0");
		cebaChargeLog.setSeqNo(record.getSeqNo());
		cebaChargeLog.setHostState("0");
		cebaChargeLog.setHostDate(record.getHostDate());
		cebaChargeLog.setHostTraceno(record.getHostTraceNo());
		cebaChargeLog.setHostRetCode(record.getHostRetCode());
		cebaChargeLog.setHostRetMsg(record.getHostRetMsg());
		
		cebaChargeLogMapper.insertSelective(cebaChargeLog);
	}
	
	@Override
	public void hostTimeoutInit(@Valid CebaChargeLogModel record) throws SysTradeExecuteException {
		CebaChargeLog cebaChargeLog = new CebaChargeLog();
		cebaChargeLog.setPlatDate(record.getSysDate());
		cebaChargeLog.setPlatTime(record.getSysTime());
		cebaChargeLog.setPlatTrace(record.getSysTraceno());
		cebaChargeLog.setSourceType(record.getSourceType());
		cebaChargeLog.setTxBranch(record.getTxBranch());
		cebaChargeLog.setTxTel(record.getTxTel());
		cebaChargeLog.setBillKey(record.getBillKey());
		cebaChargeLog.setCompanyId(record.getCompanyId());
		cebaChargeLog.setCustomerName(record.getCustomerName());
		cebaChargeLog.setPayAccount(record.getPayAccount());
		cebaChargeLog.setPayAmount(record.getPayAmount());
		cebaChargeLog.setAcType(record.getAcType());
		cebaChargeLog.setContractNo(record.getContractNo());
		cebaChargeLog.setPayState("0");
		cebaChargeLog.setCheckState("0");
		cebaChargeLog.setSeqNo(record.getSeqNo());
		cebaChargeLog.setHostState("4");
		
		cebaChargeLogMapper.insertSelective(cebaChargeLog);
	}

	@Override
	public void othSuccessUpdate(@Valid CebaChargeLogModel record) throws SysTradeExecuteException {
		CebaChargeLog cebaChargeLog = new CebaChargeLog();
		cebaChargeLog.setPlatDate(record.getSysDate());
		cebaChargeLog.setPlatTrace(record.getSysTraceno());
		cebaChargeLog.setBankBillNo(record.getBankBillNo());
		cebaChargeLog.setReceiptNo(record.getReceiptNo());
		cebaChargeLog.setAcctDate(record.getAcctDate());
		cebaChargeLog.setPayState("2");
		cebaChargeLogMapper.updateByPrimaryKeySelective(cebaChargeLog);
	}
	@Override
	public void othTimeoutUpdate(@Valid CebaChargeLogModel record) throws SysTradeExecuteException {
		CebaChargeLog cebaChargeLog = new CebaChargeLog();
		cebaChargeLog.setPlatDate(record.getSysDate());
		cebaChargeLog.setPlatTrace(record.getSysTraceno());
		cebaChargeLog.setPayState("1");
		cebaChargeLogMapper.updateByPrimaryKeySelective(cebaChargeLog);
	}
	@Override
	public void othErrorUpdate(@Valid CebaChargeLogModel record) throws SysTradeExecuteException {
		CebaChargeLog cebaChargeLog = new CebaChargeLog();
		cebaChargeLog.setPlatDate(record.getSysDate());
		cebaChargeLog.setPlatTrace(record.getSysTraceno());
		cebaChargeLog.setErrorCode(record.getErrorCode());
		cebaChargeLog.setPayState("3");
		cebaChargeLogMapper.updateByPrimaryKeySelective(cebaChargeLog);
	}
	@Override
	public void othTimeoutSuccUpdate(@Valid CebaChargeLogModel record) throws SysTradeExecuteException {
		CebaChargeLog cebaChargeLog = new CebaChargeLog();
		cebaChargeLog.setPlatDate(record.getSysDate());
		cebaChargeLog.setPlatTrace(record.getSysTraceno());
		cebaChargeLog.setPayState("2");
		cebaChargeLogMapper.updateByPrimaryKeySelective(cebaChargeLog);
	}
	@Override
	public void hostUndoSuccess(@Valid CebaChargeLogModel record) throws SysTradeExecuteException {
		CebaChargeLog cebaChargeLog = new CebaChargeLog();
		cebaChargeLog.setPlatDate(record.getSysDate());
		cebaChargeLog.setPlatTrace(record.getSysTraceno());
		cebaChargeLog.setHostState("1");
		cebaChargeLog.setHostDate(record.getHostDate());
		cebaChargeLog.setHostTraceno(record.getHostTraceNo());
		cebaChargeLog.setHostRetCode(record.getHostRetCode());
		cebaChargeLog.setHostRetMsg(record.getHostRetMsg());
		cebaChargeLogMapper.updateByPrimaryKeySelective(cebaChargeLog);
	}
	@Override
	public void hostUndoTimeout(@Valid CebaChargeLogModel record) throws SysTradeExecuteException {
		CebaChargeLog cebaChargeLog = new CebaChargeLog();
		cebaChargeLog.setPlatDate(record.getSysDate());
		cebaChargeLog.setPlatTrace(record.getSysTraceno());
		cebaChargeLog.setHostState("3");
		cebaChargeLogMapper.updateByPrimaryKeySelective(cebaChargeLog);
	}
	@Override
	public void hostUndoError(@Valid CebaChargeLogModel record) throws SysTradeExecuteException {
		CebaChargeLog cebaChargeLog = new CebaChargeLog();
		cebaChargeLog.setPlatDate(record.getSysDate());
		cebaChargeLog.setPlatTrace(record.getSysTraceno());
		cebaChargeLog.setHostState("2");
		cebaChargeLog.setHostRetCode(record.getHostRetCode());
		cebaChargeLog.setHostRetMsg(record.getHostRetMsg());
		cebaChargeLogMapper.updateByPrimaryKeySelective(cebaChargeLog);
	}
	

	/** 
	* @Title: queryLogBySeqNo 
	* @Description: 通过来源渠道号查询缴费单销账日志 
	* @param @param record
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public CebaChargeLogModel queryLogBySeqNo(MyLog myLog,String seqNo) throws SysTradeExecuteException {
		CebaChargeLog log = new CebaChargeLog();
		log.setSeqNo(seqNo);
		List<CebaChargeLog> list = cebaChargeLogMapper.select(log);
		if(list.size()>0) {
			return new CebaChargeLogModel(myLog,list.get(0).getPlatDate(),list.get(0).getPlatTime(),list.get(0).getPlatTrace());
		}
		return null;
	}

}
