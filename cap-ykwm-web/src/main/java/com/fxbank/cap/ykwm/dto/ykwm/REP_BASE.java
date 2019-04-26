package com.fxbank.cap.ykwm.dto.ykwm;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.model.FIXP_SERIAL;

public abstract class REP_BASE extends DataTransObject implements FIXP_SERIAL{
	
	private REP_HEADER header = new REP_HEADER();

	public REP_HEADER getHeader() {
		return header;
	}

	public void setHeader(REP_HEADER header) {
		this.header = header;
	}
	
}
