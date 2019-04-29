package com.fxbank.cap.ykwm.dto.ykwm;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.model.FIXP_SERIAL;

/** 
* @ClassName: REP_BASE 
* @Description: 应答报文基类
* @作者 杜振铎
* @date 2019年4月29日 下午1:58:25 
*  
*/
public abstract class REP_BASE extends DataTransObject implements FIXP_SERIAL{
	
	private REP_HEADER header = new REP_HEADER();

	public REP_HEADER getHeader() {
		return header;
	}

	public void setHeader(REP_HEADER header) {
		this.header = header;
	}
	
}
