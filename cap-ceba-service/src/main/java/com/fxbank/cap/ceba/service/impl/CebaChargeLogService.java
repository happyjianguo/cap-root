package com.fxbank.cap.ceba.service.impl;

import java.util.ArrayList;
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
		cebaChargeLog.setHostCheckState("1");
		cebaChargeLog.setCebaCheckState("1");
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
		cebaChargeLog.setHostCheckState("1");
		cebaChargeLog.setCebaCheckState("1");
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
			CebaChargeLogModel result = new CebaChargeLogModel(myLog,list.get(0).getPlatDate(),list.get(0).getPlatTime(),list.get(0).getPlatTrace());
			result.setPayState(list.get(0).getPayState());
			result.setHostState(list.get(0).getHostState());
			result.setPayAmount(list.get(0).getPayAmount());
			result.setBillKey(list.get(0).getBillKey());
			return result;
		}
		return null;
	}
	@Override
	public List<CebaChargeLogModel> getCheckTrace(MyLog myLog, String date,String hostCheckState,String cebaCheckState) throws SysTradeExecuteException {
		CebaChargeLog log = new CebaChargeLog();
		log.setPlatDate(Integer.parseInt(date));
		if(!"".equals(hostCheckState)) {
		log.setHostCheckState(hostCheckState);
		}
		if(!"".equals(cebaCheckState)) {
		log.setCebaCheckState(cebaCheckState);
		}
		List<CebaChargeLog> dataList = cebaChargeLogMapper.select(log);
		List<CebaChargeLogModel> modelList = new ArrayList<>();
		for(CebaChargeLog data : dataList) {
			CebaChargeLogModel model = new CebaChargeLogModel(myLog, data.getPlatDate(), 0, data.getPlatTrace());
			model.setSourceType(data.getSourceType());
			model.setTxBranch(data.getTxBranch());
			model.setTxTel(data.getTxTel());
			model.setBillKey(data.getBillKey());
			model.setCompanyId(data.getCompanyId());
			model.setCustomerName(data.getCustomerName());
			model.setPayAccount(data.getPayAccount());
			model.setPayAmount(data.getPayAmount());
			model.setAcType(data.getAcType());
			model.setContractNo(data.getContractNo());
			model.setBankBillNo(data.getBankBillNo());
			model.setReceiptNo(data.getReceiptNo());
			model.setAcctDate(data.getAcctDate());
			model.setPayState(data.getPayState());
			model.setErrorCode(data.getErrorCode());
			model.setHostCheckState(data.getHostCheckState());
			model.setCebaCheckState(data.getCebaCheckState());
			model.setSeqNo(data.getSeqNo());
			model.setHostDate(data.getHostDate());
			model.setHostTraceNo(data.getHostTraceno());
			model.setHostState(data.getHostState());
			model.setHostRetCode(data.getHostRetCode());
			model.setHostRetMsg(data.getHostRetMsg());
			modelList.add(model);
		}
		
		return modelList;
	}
	
	@Override
	public void updateCheck(@Valid CebaChargeLogModel record) throws SysTradeExecuteException {
		CebaChargeLog log = new CebaChargeLog();
		log.setPlatDate(record.getSysDate());
		log.setPlatTrace(record.getSysTraceno());
		if(null!=record.getHostState()) {
			log.setHostState(record.getHostState());
		}
		if(null!=record.getHostCheckState()) {
			log.setHostCheckState(record.getHostCheckState());
		}
		if(null!=record.getCebaCheckState()) {
			log.setCebaCheckState(record.getCebaCheckState());
		}
		cebaChargeLogMapper.updateByPrimaryKeySelective(log);
		
	}
	
	/** 
	* @Title: getTotalNum 
	* @Description: 查询对账完成的交易总笔数 
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public String getTotalNum(String date) throws SysTradeExecuteException {
		return cebaChargeLogMapper.selectTotalNum(date);
	}

	/** 
	* @Title: getTotalAmt 
	* @Description: 查询对账完成的交易总金额  
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public String getTotalAmt(String date) throws SysTradeExecuteException {
		return cebaChargeLogMapper.selectTotalSum(date);
	}
	@Override
	public List<CebaChargeLogModel> getUploadCheckSndTrace(MyLog myLog, Integer sysDate, Integer sysTime,
			Integer sysTraceno, String date) throws SysTradeExecuteException {
		List<CebaChargeLog> dataList = cebaChargeLogMapper.selectCheckedTrace(date);
		List<CebaChargeLogModel> modelList = new ArrayList<>();
		for(CebaChargeLog data : dataList) {
			CebaChargeLogModel model = new CebaChargeLogModel(myLog, data.getPlatDate(), sysTime, data.getPlatTrace());
			model.setSourceType(data.getSourceType());
			model.setTxBranch(data.getTxBranch());
			model.setTxTel(data.getTxTel());
			model.setBillKey(data.getBillKey());
			model.setCompanyId(data.getCompanyId());
			model.setCustomerName(data.getCustomerName());
			model.setPayAccount(data.getPayAccount());
			model.setPayAmount(data.getPayAmount());
			model.setAcType(data.getAcType());
			model.setContractNo(data.getContractNo());
			model.setBankBillNo(data.getBankBillNo());
			model.setReceiptNo(data.getReceiptNo());
			model.setAcctDate(data.getAcctDate());
			model.setPayState(data.getPayState());
			model.setErrorCode(data.getErrorCode());
			model.setHostCheckState(data.getHostCheckState());
			model.setCebaCheckState(data.getCebaCheckState());
			model.setSeqNo(data.getSeqNo());
			model.setHostDate(data.getHostDate());
			model.setHostTraceNo(data.getHostTraceno());
			model.setHostState(data.getHostState());
			model.setHostRetCode(data.getHostRetCode());
			model.setHostRetMsg(data.getHostRetMsg());
			modelList.add(model);
		}
		
		return modelList;
	}
	/** 
	* @Title: queryLogByPk 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param myLog
	* @param @param sysDate
	* @param @param sysTime
	* @param @param sysTraceno
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public CebaChargeLogModel queryLogByPk(MyLog myLog, Integer sysDate, Integer sysTraceno)
			throws SysTradeExecuteException {
		CebaChargeLogModel record = new CebaChargeLogModel(myLog, sysDate, null, sysTraceno);
		CebaChargeLog log = new CebaChargeLog();
		log.setPlatDate(sysDate);
		log.setPlatTrace(sysTraceno);
		CebaChargeLog data = cebaChargeLogMapper.selectOne(log);
		if(null!=data) {
			record.setSourceType(data.getSourceType());
			record.setTxBranch(data.getTxBranch());
			record.setTxTel(data.getTxTel());
			record.setBillKey(data.getBillKey());
			record.setCompanyId(data.getCompanyId());
			record.setCustomerName(data.getCustomerName());
			record.setPayAccount(data.getPayAccount());
			record.setPayAmount(data.getPayAmount());
			record.setAcType(data.getAcType());
			record.setContractNo(data.getContractNo());
			record.setBankBillNo(data.getBankBillNo());
			record.setReceiptNo(data.getReceiptNo());
			record.setAcctDate(data.getAcctDate());
			record.setPayState(data.getPayState());
			record.setErrorCode(data.getErrorCode());
			record.setHostCheckState(data.getHostCheckState());
			record.setCebaCheckState(data.getCebaCheckState());
			record.setSeqNo(data.getSeqNo());
			record.setHostDate(data.getHostDate());
			record.setHostTraceNo(data.getHostTraceno());
			record.setHostState(data.getHostState());
			record.setHostRetCode(data.getHostRetCode());
			record.setHostRetMsg(data.getHostRetMsg());
		}else {
			return null;
		}
		return record;
	}

	/** 
	* @Title: updateHostRefunde 
	* @Description: 更新核心退款
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateHostRefunde(@Valid CebaChargeLogModel record) throws SysTradeExecuteException {
		CebaChargeLog log = new CebaChargeLog();
		log.setPlatDate(record.getSysDate());
		log.setPlatTrace(record.getSysTraceno());
		log.setHostState(record.getHostState());
		if(null!=record.getHostRetCode()) {
			log.setHostRetCode(record.getHostRetCode());
		}
		if(null!=record.getHostRetMsg()) {
			log.setHostRetMsg(record.getHostRetMsg());
		}
		cebaChargeLogMapper.updateByPrimaryKeySelective(log);
		
	}

	/** 
	* @Title: getHostCheckNum 
	* @Description: 获取核心对账笔数
	* @param @param date
	* @param @param capResult
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public String getHostCheckNum(String date, String capResult) throws SysTradeExecuteException {
		return cebaChargeLogMapper.selectHostCheckNum(date, capResult);
	}

	/** 
	* @Title: getCebaCheckNum 
	* @Description: 获取光大银行对账笔数
	* @param @param date
	* @param @param capResult
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public String getCebaCheckNum(String date, String capResult) throws SysTradeExecuteException {
		return cebaChargeLogMapper.selectCebaCheckNum(date, capResult);
	}

	/** 
	* @Title: getCheckSuccNum 
	* @Description: 获取对账成功笔数
	* @param @param date
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public String getCheckSuccNum(String date) throws SysTradeExecuteException {
		return cebaChargeLogMapper.selectCheckSuccNum(date);
	}

	/** 
	* @Title: getTotalCheckNum 
	* @Description: 获取对账总笔数
	* @param @param date
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public String getTotalCheckNum(String date) throws SysTradeExecuteException {
		return cebaChargeLogMapper.selectTotalCheckNum(date);
	}

	/** 
	* @Title: getCheckSuccAmt 
	* @Description: 对账成功总金额
	* @param @param date
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public String getCheckSuccAmt(String date) throws SysTradeExecuteException {
		return cebaChargeLogMapper.selectCheckSuccAmt(date);
	}

}
