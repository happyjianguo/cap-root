package com.fxbank.cap.ykwm.dto.ykwm;

import com.fxbank.cip.base.model.FIXP_SERIAL;

/** 
* @ClassName: REP_HEADER 
* @Description: 应答报文头
* @作者 杜振铎
* @date 2019年4月29日 下午2:01:27 
*  
*/
public class REP_HEADER implements FIXP_SERIAL {

	private String result;

	@Override
	public String creaFixPack() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.result==null?"":this.result).append("|");
		
		return sb.toString();
	}

	@Override
	public void chanFixPack(String pack) {
		String[] array = pack.split("\\|");
		this.result = array[0].trim();
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
