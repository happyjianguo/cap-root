package com.fxbank.cap.ceba.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.entity.CebaSettleLog;
import com.fxbank.cap.ceba.mapper.CebaSettleLogMapper;
import com.fxbank.cap.ceba.model.CebaSettleLogModel;
import com.fxbank.cap.ceba.service.ICebaSettleLogService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: CebaSettleLogService 
* @Description: 缴费清算日志服务
* @作者 杜振铎
* @date 2019年5月14日 下午2:06:30 
*  
*/
@Service(validation = "true", version = "1.0.0")
public class CebaSettleLogService implements ICebaSettleLogService{
	
	@Resource
	private CebaSettleLogMapper cebaSettleLogMapper;

	@Override
	public List<CebaSettleLogModel> querySettleLog(MyLog myLog,Integer chkDate) throws SysTradeExecuteException {
		List<CebaSettleLog> cebaSettleLogList = cebaSettleLogMapper.selectSettleLog(chkDate);
		List<CebaSettleLogModel> settlelList = new ArrayList<CebaSettleLogModel>();
		for(CebaSettleLog log : cebaSettleLogList) {
			CebaSettleLogModel model = new CebaSettleLogModel(myLog,0,0,0);
			model.setChkDate(log.getChkDate());
			model.setTxAmt(log.getTxAmt());
			model.setTxSts(log.getTxSts());
            model.setCnapsDate(log.getCnapsDate());
            model.setCnapsTraceno(log.getCnapsTraceno());
            model.setCnapsCode(log.getCnapsCode());
            model.setCnapsMsg(log.getCnapsMsg());
			settlelList.add(model);
		}
		
		return settlelList;
	}

	/** 
	* @Title: updateSettleTxSts 
	* @Description: 更新清算日志状态
	* @param @param myLog
	* @param @param chkDate
	* @param @param txSts
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateSettleTxSts(MyLog myLog, Integer chkDate, String txSts) throws SysTradeExecuteException {
		//0:对账完成；1:等待重新汇款；2:汇款完成；3:汇款成功；4:汇款失败
		CebaSettleLog cebaSettleLog = new CebaSettleLog();
		cebaSettleLog.setChkDate(chkDate);
		cebaSettleLog.setTxSts(txSts);
		cebaSettleLogMapper.updateByPrimaryKeySelective(cebaSettleLog);
		
	}

	/** 
	* @Title: updateTranComplete 
	* @Description: 跨行付款记账成功更新日志 
	* @param @param myLog
	* @param @param chkDate
	* @param @param cnapsCode
	* @param @param cnapsMsg
	* @param @param cnapsDate
	* @param @param cnapsTraceno
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateTranComplete(MyLog myLog, Integer chkDate, String cnapsCode, String cnapsMsg, Integer cnapsDate,
			String cnapsTraceno) throws SysTradeExecuteException {
		//0:对账完成；1:等待重新汇款；2:汇款完成；3:汇款成功；4:汇款失败
		CebaSettleLog cebaSettleLog = new CebaSettleLog();
		cebaSettleLog.setChkDate(chkDate);
		cebaSettleLog.setTxSts("2");
		cebaSettleLog.setCnapsCode(cnapsCode);
		cebaSettleLog.setCnapsMsg(cnapsMsg);
		cebaSettleLog.setCnapsDate(cnapsDate);
		cebaSettleLog.setCnapsTraceno(cnapsTraceno);
		
		cebaSettleLogMapper.updateByPrimaryKeySelective(cebaSettleLog);
		
	}

	/** 
	* @Title: initSettleLog 
	* @Description: 缴费清算日志登记
	* @param @param myLog
	* @param @param chkDate
	* @param @param txAmt
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void initSettleLog(MyLog myLog, Integer chkDate, BigDecimal txAmt) throws SysTradeExecuteException {
		//0:对账完成；1:等待重新汇款；2:汇款完成；3:汇款成功；4:汇款失败
		CebaSettleLog cebaSettleLog = new CebaSettleLog();
		cebaSettleLog.setChkDate(chkDate);
		cebaSettleLog.setTxAmt(txAmt);
		cebaSettleLog.setTxSts("0");
		cebaSettleLogMapper.insertSelective(cebaSettleLog);
		
	}

	/** 
	* @Title: isSettleSucc 
	* @Description: 根据对账日期查询是否清算成功 
	* @param @param myLog
	* @param @param chkDate
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public Boolean isSettleSucc(MyLog myLog, Integer chkDate) throws SysTradeExecuteException {
		Boolean result = false;
		CebaSettleLog log = new CebaSettleLog();
		log.setChkDate(chkDate);
		int count = cebaSettleLogMapper.selectCount(log);
		if(count>0){
			result = true;
		}
		return result;
	}

	/** 
	* @Title: querySettleLogByPk 
	* @Description: 根据对账日期查询清算流水
	* @param @param myLog
	* @param @param chkDate
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public CebaSettleLogModel querySettleLogByPk(MyLog myLog, Integer chkDate) throws SysTradeExecuteException {
		CebaSettleLog log = new CebaSettleLog();
		log.setChkDate(chkDate);
		CebaSettleLog log1 = cebaSettleLogMapper.selectOne(log);
		if(null==log1) {
			return null;
		}else {
			CebaSettleLogModel result = new CebaSettleLogModel(myLog, 0, 0, 0);
			result.setChkDate(log.getChkDate());
			result.setTxAmt(log.getTxAmt());
			result.setTxSts(log.getTxSts());
			result.setCnapsDate(log.getCnapsDate());
			result.setCnapsTraceno(log.getCnapsTraceno());
			result.setCnapsCode(log.getCnapsCode());
			result.setCnapsMsg(log.getCnapsMsg());
			return result;
		}
	}

}
