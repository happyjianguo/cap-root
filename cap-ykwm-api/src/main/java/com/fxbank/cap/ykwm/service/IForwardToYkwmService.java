package com.fxbank.cap.ykwm.service;

import com.fxbank.cap.ykwm.model.REP_BASE;
import com.fxbank.cap.ykwm.model.REQ_BASE;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

public interface IForwardToYkwmService {
	
	public <T extends REP_BASE> T sendToYkwm(REQ_BASE reqBase, Class<T> clazz) throws SysTradeExecuteException; 

}
