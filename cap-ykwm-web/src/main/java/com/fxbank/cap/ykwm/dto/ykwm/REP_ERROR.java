package com.fxbank.cap.ykwm.dto.ykwm;

import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/** 
* @ClassName: REP_ERROR 
* @Description: 错误应答报文
* @作者 杜振铎
* @date 2019年4月29日 下午2:00:57 
*  
*/
public class REP_ERROR extends REP_BASE {
	
	@FixedField(order = 2, len = 100, desc = "响应信息")
	private String repMsg;

	public String getRepMsg() {
		return repMsg;
	}

	public void setRepMsg(String repMsg) {
		this.repMsg = repMsg;
	}

}