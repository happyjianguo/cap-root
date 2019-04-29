package com.fxbank.cap.ykwm.model;

import java.io.Serializable;

import com.fxbank.cip.base.model.FIXP_SERIAL;

/** 
* @ClassName: REQ_HEADER 
* @Description: 请求报文头 
* @作者 杜振铎
* @date 2019年4月29日 下午3:02:43 
*  
*/
public class REQ_HEADER implements Serializable,FIXP_SERIAL{

	private static final long serialVersionUID = -4254351009472258829L;

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
