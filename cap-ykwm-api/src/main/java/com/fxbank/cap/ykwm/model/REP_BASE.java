package com.fxbank.cap.ykwm.model;

import java.io.Serializable;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.FIXP_SERIAL;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: REP_BASE 
* @Description: 响应报文基类 
* @作者 杜振铎
* @date 2019年4月29日 下午3:01:38 
*  
*/
public abstract class REP_BASE extends ModelBase implements Serializable,FIXP_SERIAL{
	
	private static final long serialVersionUID = 6311109021156971900L;

	public REP_BASE(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	private REP_HEADER header = new REP_HEADER();

	public REP_HEADER getHeader() {
		return header;
	}

	public void setHeader(REP_HEADER header) {
		this.header = header;
	}
	
}
