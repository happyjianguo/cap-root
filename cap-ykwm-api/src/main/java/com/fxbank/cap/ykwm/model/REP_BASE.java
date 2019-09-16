package com.fxbank.cap.ykwm.model;


import java.io.Serializable;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REP_BASE 
* @Description: 响应报文基类 
* @作者 杜振铎
* @date 2019年4月29日 下午3:01:38 
*  
*/
public abstract class REP_BASE extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = 4316639802298647688L;

	public REP_BASE(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	@FixedField(order = 1, len = 1, desc = "响应码")
	private String result;
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
}
