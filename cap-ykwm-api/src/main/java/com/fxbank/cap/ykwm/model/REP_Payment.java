package com.fxbank.cap.ykwm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fxbank.cip.base.log.MyLog;


public class REP_Payment extends REP_BASE {

	private static final long serialVersionUID = -5008748819109644373L;

		private String result;

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

    public REP_Payment(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

}