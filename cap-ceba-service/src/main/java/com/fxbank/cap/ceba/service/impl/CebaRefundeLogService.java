package com.fxbank.cap.ceba.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.entity.CebaRefundeLog;
import com.fxbank.cap.ceba.mapper.CebaRefundeLogMapper;
import com.fxbank.cap.ceba.model.CebaRefundeLogModel;
import com.fxbank.cap.ceba.service.ICebaRefundeLogService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: CebaRefundeLogService 
* @Description: 退款日志服务
* @作者 杜振铎
* @date 2019年5月14日 下午2:06:30 
*  
*/
@Service(validation = "true", version = "1.0.0")
public class CebaRefundeLogService implements ICebaRefundeLogService{
	
	@Resource
	private CebaRefundeLogMapper cebaRefundeLogMapper;

	/** 
	* @Title: initRefundeLog 
	* @Description: 退款日志登记
	* @param @param myLog
	* @param @param platDate
	* @param @param platTraceno
	* @param @param txAmt
	* @param @param flag
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void initRefundeLog(MyLog myLog, CebaRefundeLogModel model)
			throws SysTradeExecuteException {
		CebaRefundeLog cebaRefundeLog = new CebaRefundeLog();
		cebaRefundeLog.setPlatDate(model.getSysDate());
		cebaRefundeLog.setPlatTraceno(model.getSysTraceno());
		cebaRefundeLog.setFlag(model.getFlag());
		cebaRefundeLog.setStatus("0");
		cebaRefundeLog.setHostCode(model.getHostCode());
		cebaRefundeLog.setHostMsg(model.getHostMsg());
		cebaRefundeLogMapper.insertSelective(cebaRefundeLog);
	}

	/** 
	* @Title: queryRefundeLog 
	* @Description: 查询待退款的流水日志 
	* @param @param myLog
	* @param @param platDate
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public List<CebaRefundeLogModel> queryRefundeLog(MyLog myLog, Integer platDate) throws SysTradeExecuteException {
		List<CebaRefundeLog> list = cebaRefundeLogMapper.selectRefundeFileLog(platDate.toString());
		List<CebaRefundeLogModel> result = new ArrayList<CebaRefundeLogModel>();
		for(CebaRefundeLog log:list) {
			CebaRefundeLogModel model = new CebaRefundeLogModel(null,log.getPlatDate(),0,log.getPlatTraceno());
			model.setFlag(log.getFlag());
			model.setStatus(log.getStatus());
			result.add(model);
		}
		return result;
	}

	/** 
	* @Title: updateRefundeLog 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param myLog
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateRefundeLog(MyLog myLog, CebaRefundeLogModel model) throws SysTradeExecuteException {
		CebaRefundeLog cebaRefundeLog = new CebaRefundeLog();
		cebaRefundeLog.setPlatDate(model.getSysDate());
		cebaRefundeLog.setPlatTraceno(model.getSysTraceno());
		cebaRefundeLog.setStatus(model.getStatus());
		if(model.getHostCode()!=null) {
			cebaRefundeLog.setHostCode(model.getHostCode());
		}
		if(model.getHostMsg()!=null) {
			cebaRefundeLog.setHostMsg(model.getHostMsg());
		}
		cebaRefundeLogMapper.updateByPrimaryKeySelective(cebaRefundeLog);
	}

	/** 
	* @Title: isInitRefundeLog 
	* @Description: 退款文件是否登记过
	* @param @param myLog
	* @param @param platDate
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public Boolean isInitRefundeLog(MyLog myLog, Integer platDate) throws SysTradeExecuteException {
		Boolean result = false;
		CebaRefundeLog log = new CebaRefundeLog();
		log.setReqDate(platDate);
		int count = cebaRefundeLogMapper.selectCount(log);
		if(count>0){
			result = true;
		}
		return result;
	}

}
