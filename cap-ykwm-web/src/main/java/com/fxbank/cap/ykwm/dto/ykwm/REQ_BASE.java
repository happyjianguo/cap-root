package com.fxbank.cap.ykwm.dto.ykwm;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REQ_BASE 
* @Description: 请求报文基类 
* @作者 杜振铎
* @date 2019年4月29日 下午3:02:25 
*  
*/
public abstract class REQ_BASE extends DataTransObject{
	
	@FixedField(order = 1, len = 7, desc = "业务类型")
	private String ttxnNm;

	public String getTtxnNm() {
		return ttxnNm;
	}

	public void setTtxnNm(String ttxnNm) {
		this.ttxnNm = ttxnNm;
	}


}
