package com.fxbank.cap.ykwm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ykwm.entity.YkwmSettlelog;
import com.fxbank.cap.ykwm.mapper.YkwmSettlelogMapper;
import com.fxbank.cap.ykwm.model.YkwmSettleLogModel;
import com.fxbank.cap.ykwm.service.IYkwmSettleLogService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: YkwmSettleLogService 
* @Description: 清算流水表服务
* @作者 杜振铎
* @date 2019年9月11日 下午2:37:26 
*  
*/
@Service(version = "1.0.0")
public class YkwmSettleLogService implements IYkwmSettleLogService{
	
	@Resource
	private YkwmSettlelogMapper ykwmSettleLogMapper;


	/** 
	* @Title: initSettleLog 
	* @Description: 登记清算流水表
	* @param @param myLog
	* @param @param chkDate
	* @param @param txAmt
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void initSettleLog(MyLog myLog, Integer chkDate, BigDecimal txAmt) throws SysTradeExecuteException {
		//状态 （0:对账完成；1:等待重新汇款；2:转账成功；3:转账失败；4:转账超时）
		YkwmSettlelog ykwmSettleLog = new YkwmSettlelog();
		ykwmSettleLog.setChkDate(chkDate);
		ykwmSettleLog.setTxAmt(txAmt);
		ykwmSettleLog.setTxSts("0");
		ykwmSettleLogMapper.insertSelective(ykwmSettleLog);

		
	}

	/** 
	* @Title: querySettleLog 
	* @Description: 查询未清算流水 
	* @param @param myLog
	* @param @param chkDate
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public List<YkwmSettleLogModel> querySettleLog(MyLog myLog, Integer chkDate) throws SysTradeExecuteException {
		List<YkwmSettlelog> cebaSettleLogList = ykwmSettleLogMapper.selectSettleLog(chkDate);
		List<YkwmSettleLogModel> settlelList = new ArrayList<YkwmSettleLogModel>();
		for(YkwmSettlelog log : cebaSettleLogList) {
			YkwmSettleLogModel model = new YkwmSettleLogModel(myLog,0,0,0);
			model.setChkDate(log.getChkDate());
			model.setTxAmt(log.getTxAmt());
			model.setTxSts(log.getTxSts());
            model.setHostDate(log.getHostDate());
            model.setHostTraceno(log.getHostTraceno());
			settlelList.add(model);
		}
		
		return settlelList;

	}

	/** 
	* @Title: updateSettleTxSts 
	* @Description: 更新清算流水状态
	* @param @param myLog
	* @param @param chkDate
	* @param @param txSts
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateSettle(MyLog myLog, YkwmSettleLogModel model) throws SysTradeExecuteException {
		YkwmSettlelog ykwmSettleLog = new YkwmSettlelog();
		ykwmSettleLog.setChkDate(model.getChkDate());
		ykwmSettleLog.setTxSts(model.getTxSts());
		ykwmSettleLog.setHostDate(model.getHostDate());
		ykwmSettleLog.setHostTraceno(model.getHostTraceno());
		ykwmSettleLog.setHostCode(model.getHostCode());
		ykwmSettleLog.setHostMsg(model.getHostMsg());
		ykwmSettleLogMapper.updateByPrimaryKeySelective(ykwmSettleLog);

		
	}

	/** 
	* @Title: querySettleLogByPk 
	* @Description: 通过对账日期查询清算流水
	* @param @param myLog
	* @param @param chkDate
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public YkwmSettleLogModel querySettleLogByPk(MyLog myLog, Integer chkDate) throws SysTradeExecuteException {
		YkwmSettlelog log = new YkwmSettlelog();
		log.setChkDate(chkDate);
		YkwmSettlelog log1 = ykwmSettleLogMapper.selectOne(log);
		if(null==log1) {
			return null;
		}else {
			YkwmSettleLogModel result = new YkwmSettleLogModel(myLog, 0, 0, 0);
			result.setChkDate(log.getChkDate());
			result.setTxAmt(log.getTxAmt());
			result.setTxSts(log.getTxSts());
			result.setHostDate(log.getHostDate());
			result.setHostTraceno(log.getHostTraceno());
			return result;
		}
	}


}
