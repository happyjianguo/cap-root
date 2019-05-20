package com.fxbank.cap.ykwm.model;

import java.io.Serializable;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REQ_BASE 
* @Description: 请求报文基类 
* @作者 杜振铎
* @date 2019年4月29日 下午3:02:25 
*  
*/
public abstract class REQ_BASE extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = -5131816695318862118L;

	public REQ_BASE(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	@FixedField(order = 1, len = 7, desc = "业务类型")
	private String ttxnNm;

	public String getTtxnNm() {
		return ttxnNm;
	}

	public void setTtxnNm(String ttxnNm) {
		this.ttxnNm = ttxnNm;
	}


}
