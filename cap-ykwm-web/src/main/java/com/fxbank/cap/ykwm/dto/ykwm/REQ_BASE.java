package com.fxbank.cap.ykwm.dto.ykwm;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.model.FIXP_SERIAL;


public abstract class REQ_BASE extends DataTransObject implements FIXP_SERIAL{
	
	private REQ_HEADER header = new REQ_HEADER();

	public REQ_HEADER getHeader() {
		return header;
	}

	public void setHeader(REQ_HEADER header) {
		this.header = header;
	}

}
