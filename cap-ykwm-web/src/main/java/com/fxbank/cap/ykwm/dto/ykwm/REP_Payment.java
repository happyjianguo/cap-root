package com.fxbank.cap.ykwm.dto.ykwm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

public class REP_Payment extends REP_BASE {

	//private String result;
	//private String code;

	@FixedField(order = 2, len = 100, desc = "处理结果")
	private String result;

	@FixedField(order = 3, len = 100, desc = "取票号")
	private String code;
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}



}