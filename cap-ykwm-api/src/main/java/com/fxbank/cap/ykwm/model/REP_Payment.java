package com.fxbank.cap.ykwm.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;


public class REP_Payment extends REP_BASE {

	private static final long serialVersionUID = -5008748819109644373L;

	@FixedField(order = 2, len = 100, desc = "取票码列表")
    private String code;

    @Deprecated
    public REP_Payment() {
        super(null, 0, 0, 0);
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

    public REP_Payment(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

}