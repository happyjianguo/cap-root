package com.fxbank.cap.ykwm.dto.ykwm;

import com.fxbank.cip.base.model.FIXP_SERIAL;

/** 
* @ClassName: REQ_HEADER 
* @Description: 请求报文头
* @author Duzhenduo
* @date 2019年4月29日 下午2:03:13 
*  
*/
public class REQ_HEADER implements FIXP_SERIAL {

	private String tTxnNm;

	@Override
	public String creaFixPack() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.tTxnNm==null?"":this.tTxnNm).append("|");
		
		return sb.toString();
	}

	@Override
	public void chanFixPack(String pack) {
		String[] array = pack.split("\\|");
		this.tTxnNm = array[0].trim();
	}

	public String gettTxnNm() {
		return tTxnNm;
	}

	public void settTxnNm(String tTxnNm) {
		this.tTxnNm = tTxnNm;
	}

	
}
