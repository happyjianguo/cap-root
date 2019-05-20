package com.fxbank.cap.ykwm.dto.ykwm;

import java.io.Serializable;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.FIXP_SERIAL;
import com.fxbank.cip.base.model.ModelBase;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REP_BASE 
* @Description: 响应报文基类 
* @作者 杜振铎
* @date 2019年4月29日 下午3:01:38 
*  
*/
public abstract class REP_BASE extends DataTransObject{
	
	@FixedField(order = 1, len = 1, desc = "响应码")
	private String result;
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
}
