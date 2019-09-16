package com.fxbank.cap.ykwm.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;


public class REP_Cancel extends REP_BASE {

	private static final long serialVersionUID = -5008748819109644373L;

    @Deprecated
    public REP_Cancel() {
        super(null, 0, 0, 0);
    }

    public REP_Cancel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

}