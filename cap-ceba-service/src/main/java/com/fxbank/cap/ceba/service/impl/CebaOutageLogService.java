package com.fxbank.cap.ceba.service.impl;

import javax.annotation.Resource;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.entity.CebaOutageLog;
import com.fxbank.cap.ceba.mapper.CebaOutageLogMapper;
import com.fxbank.cap.ceba.model.CebaOutageLogModel;
import com.fxbank.cap.ceba.service.ICebaOutageLogService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: CebaOutageLogService 
* @Description: 停运通知流水日志服务
* @作者 杜振铎
* @date 2019年5月14日 下午2:06:30 
*  
*/
@Service(validation = "true", version = "1.0.0")
public class CebaOutageLogService implements ICebaOutageLogService{
	
	@Resource
	private CebaOutageLogMapper cebaOutageLogMapper;

	/** 
	* @Title: initOutageLog 
	* @Description: 停运通知流水日志登记
	* @param @param myLog
	* @param @param model
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void initOutageLog(MyLog myLog, CebaOutageLogModel model) throws SysTradeExecuteException {
		CebaOutageLog log = new CebaOutageLog();
		log.setNoticeTime(model.getNoticeTime());
		log.setOutageNoticeId(model.getOutageNoticeId());
		log.setCompanyId(model.getCompanyId());
		log.setCompanyName(model.getCompanyName());
		log.setOutageReason(model.getOutageReason());
		log.setOutageDesc(model.getOutageDesc());
		log.setOutageBeginTime(model.getOutageBeginTime());
		log.setOutageEndTime(model.getOutageEndTime());
		log.setFixDate(model.getFixDate());
		log.setFixTime(model.getFixTime());
		log.setRemark(model.getRemark());
		cebaOutageLogMapper.insert(log);
		
	}

	/** 
	* @Title: isOutageByCompanyId 
	* @Description: 通过渠道日期和缴费项目编号查询是否停运
	* @param @param myLog
	* @param @param companyId
	* @param @param sysDate
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public CebaOutageLogModel isOutageByCompanyId(MyLog myLog, String companyId, Long sysDate) throws SysTradeExecuteException {
		CebaOutageLog log = new CebaOutageLog();
		CebaOutageLogModel model = new CebaOutageLogModel(myLog,null,null,null);
		log = cebaOutageLogMapper.queryOutageLog(companyId, sysDate);
		if(null!=log) {
			model.setNoticeTime(log.getNoticeTime());
			model.setOutageNoticeId(log.getOutageNoticeId());
			model.setCompanyId(log.getCompanyId());
			model.setCompanyName(log.getCompanyName());
			model.setOutageReason(log.getOutageReason());
			model.setOutageDesc(log.getOutageDesc());
			model.setOutageBeginTime(log.getOutageBeginTime());
			model.setOutageEndTime(log.getOutageEndTime());
			model.setFixDate(log.getFixDate());
			model.setFixTime(log.getFixTime());
			model.setRemark(log.getRemark());
		}else {
			return null;
		}
		return model;
	}


	
}
