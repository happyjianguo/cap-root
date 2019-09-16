package com.fxbank.cap.ykwm.dto.ykwm;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;


public class REP_Payment extends REP_BASE {

	@FixedField(order = 2, len = 100, desc = "取票码列表")
    private String code;

	public REP_Payment() {
		super.setResult("0");
	}

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

}