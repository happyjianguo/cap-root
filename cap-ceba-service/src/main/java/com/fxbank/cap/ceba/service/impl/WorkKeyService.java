package com.fxbank.cap.ceba.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fxbank.cap.ceba.CebaServer;
import com.fxbank.cap.ceba.netty.ceba.CebaClient;
import com.fxbank.cap.ceba.service.IWorkKeyService;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: WorkKeyService
 * @Description: 工作密钥管理服务
 * @作者 杜振铎
 * @date 2019年5月7日 下午5:13:45
 * 
 */
@Service(version = "1.0.0")
public class WorkKeyService implements IWorkKeyService {

	private static Logger logger = LoggerFactory.getLogger(WorkKeyService.class);

	/** 
	* @Title: updateWorkKey 
	* @Description: 更新工作密钥 
	* @param @param keyValue
	* @param @param verifyValue
	* @param @param keyValue1
	* @param @param verifyValue1
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public void updateWorkKey(MyLog myLog,String macValue, String macVerifyValue, String pinValue, String pinVerifyValue)
			throws SysTradeExecuteException {
		try {
			CebaServer.softEnc.WriteMACK(macValue, macVerifyValue);
			CebaServer.softEnc.WritePINK(pinValue, pinVerifyValue);
			myLog.info(logger, "更新工作密钥成功");
		} catch (Exception e) {
			myLog.error(logger, "更新工作密钥失败", e);
		}
		
	}

	/** 
	* @Title: genMac 
	* @Description: 生成MAC 
	* @param @param pack
	* @param @return
	* @param @throws SysTradeExecuteException    设定文件 
	* @throws 
	*/
	@Override
	public String genMac(MyLog myLog,String pack) throws SysTradeExecuteException {
		try {
			String mac = CebaServer.softEnc.GenMac(pack.getBytes(CebaClient.CODING));
			myLog.info(logger, "生成MAC成功");
			return mac;
		} catch (Exception e) {
			myLog.error(logger, "生成MAC失败", e);
		}
		return null;
	}

	
}
