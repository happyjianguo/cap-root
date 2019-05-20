package com.fxbank.cap.ykwm.model;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REP_ERROR 
* @Description: 错误应答报文
* @作者 杜振铎
* @date 2019年4月29日 下午2:00:57 
*  
*/
public class REP_ERROR extends REP_BASE {
	
	private static final long serialVersionUID = -3342369222922083811L;

	@Deprecated
	public REP_ERROR() {
		super(null, 0, 0, 0);
	}

    public REP_ERROR(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }
	
	@FixedField(order = 2, len = 100, desc = "响应信息")
	private String repMsg;

	public String getRepMsg() {
		return repMsg;
	}

	public void setRepMsg(String repMsg) {
		this.repMsg = repMsg;
	}

}