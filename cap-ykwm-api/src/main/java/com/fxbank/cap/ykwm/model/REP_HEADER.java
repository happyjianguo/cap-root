package com.fxbank.cap.ykwm.model;

import java.io.Serializable;

import com.fxbank.cip.base.model.FIXP_SERIAL;

/** 
* @ClassName: REP_HEADER 
* @Description: 响应报文头 
* @author Duzhenduo
* @date 2019年4月29日 下午3:01:56 
*  
*/
public class REP_HEADER  implements Serializable,FIXP_SERIAL{

	private static final long serialVersionUID = -4892376236545292458L;

	private static final String RESULT = "0";
	
	private String result;
	
    private String errorMsg;
	
	@Override
	public String creaFixPack() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.result==null?"":this.result).append("|");
		if(!RESULT.equals(this.result)) {
			sb.append(this.errorMsg==null?"":this.errorMsg).append("|");
		}
		return sb.toString();
	}

	@Override
	public void chanFixPack(String pack) {
		String[] array = pack.split("\\|");
		this.result = array[0].trim();
		if(!RESULT.equals(this.result)) {
			this.errorMsg = array[1].trim();
		}
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
