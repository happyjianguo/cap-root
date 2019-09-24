package com.fxbank.cap.ceba.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;
import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.entity.CebaBillCheck;
import com.fxbank.cap.ceba.mapper.CebaBillCheckMapper;
import com.fxbank.cap.ceba.model.CebaBillCheckModel;
import com.fxbank.cap.ceba.service.ICebaBillCheckService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

/** 
* @ClassName: CebaBillCheckService 
* @Description: 对账文件导入服务
* @作者 杜振铎
* @date 2019年9月24日 下午2:38:10 
*  
*/
@Service(validation = "true", version = "1.0.0")
public class CebaBillCheckService implements ICebaBillCheckService{
	
	@Resource
	private CebaBillCheckMapper cebaBillCheckMapper;

	/** 
	* @Title: billCheckInit 
	* @Description: 对账文件登记 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void billCheckInit(@Valid CebaBillCheckModel record) throws SysTradeExecuteException {
		CebaBillCheck cebaBillCheck = new CebaBillCheck();
		cebaBillCheck.setSignDate(record.getSignDate());
		cebaBillCheck.setFileName(record.getFileName());
		cebaBillCheck.setUploadDate(record.getUploadDate());
		cebaBillCheck.setStatus(record.getStatus());
		cebaBillCheck.setRemark(record.getRemark());
		cebaBillCheckMapper.insertSelective(cebaBillCheck);
		
	}

	/** 
	* @Title: billCheckUpdate 
	* @Description: 更新对账文件流水表状态 
	* @param @param record
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void billCheckUpdate(@Valid CebaBillCheckModel record) throws SysTradeExecuteException {
		CebaBillCheck cebaBillCheck = new CebaBillCheck();
		cebaBillCheck.setSignDate(record.getSignDate());
		cebaBillCheck.setUploadDate(record.getUploadDate());
		cebaBillCheck.setStatus(record.getStatus());
		cebaBillCheckMapper.updateByPrimaryKeySelective(cebaBillCheck);
		
	}

	/** 
	* @Title: queryBillCheck 
	* @Description: 查询对账文件导入成功的对账流水
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public List<CebaBillCheckModel> queryBillCheck() throws SysTradeExecuteException {
		CebaBillCheck cebaBillCheck = new CebaBillCheck();
		cebaBillCheck.setStatus("1");
		List<CebaBillCheck> list = cebaBillCheckMapper.select(cebaBillCheck);
		List<CebaBillCheckModel> result = new ArrayList<CebaBillCheckModel>();
		for(CebaBillCheck billCheck:list) {
			CebaBillCheckModel model = new CebaBillCheckModel(null,0,0,0);
			model.setSignDate(billCheck.getSignDate());
			model.setFileName(billCheck.getFileName());
			model.setUploadDate(billCheck.getUploadDate());
			model.setStatus(billCheck.getStatus());
			model.setRemark(billCheck.getRemark());
			result.add(model);
		}
		return result;
	}

}
