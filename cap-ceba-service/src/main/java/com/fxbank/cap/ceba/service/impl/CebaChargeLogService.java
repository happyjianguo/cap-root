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

	/** 
	* @Title: logInit 
	* @Description: 缴费单销账登记 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void logInit(@Valid CebaChargeLogModel record) throws SysTradeExecuteException {
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
		//核心记账状态，0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时
		if(record.getHostDate()==null) {
			cebaChargeLog.setHostState("4");
		}else {
			cebaChargeLog.setHostState("0");
		}
		cebaChargeLog.setHostDate(record.getHostDate());
		cebaChargeLog.setHostTraceno(record.getHostTraceNo());
		cebaChargeLog.setHostRetCode(record.getHostRetCode());
		cebaChargeLog.setHostRetMsg(record.getHostRetMsg());
		
		cebaChargeLogMapper.insert(cebaChargeLog);
	}

	/** 
	* @Title: logUpd 
	* @Description: 缴费单销账更新日志
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void logUpd(@Valid CebaChargeLogModel record) throws SysTradeExecuteException {
		CebaChargeLog cebaChargeLog = new CebaChargeLog();
		cebaChargeLog.setPlatDate(record.getSysDate());
		cebaChargeLog.setPlatTrace(record.getSysTraceno());
		cebaChargeLog.setBankBillNo(record.getBankBillNo());
		cebaChargeLog.setReceiptNo(record.getReceiptNo());
		cebaChargeLog.setAcctDate(record.getAcctDate());
		cebaChargeLog.setPayState(record.getPayState());
		cebaChargeLog.setErrorCode(record.getErrorCode());
		cebaChargeLog.setHostDate(record.getHostDate());
		cebaChargeLog.setHostTraceno(record.getHostTraceNo());
		cebaChargeLog.setHostState(record.getHostState());
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
