package com.fxbank.cap.ceba.dto.ceba;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.cip.base.model.FIXP_SERIAL;

/** 
* @ClassName: REQ_BASE2 
* @Description: 请求报文基类
* @作者 杜振铎
* @date 2019年5月7日 下午5:16:59 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class REQ_BASE2 extends DataTransObject implements FIXP_SERIAL{

	private BASE_HEAD head = new BASE_HEAD();

	public BASE_HEAD getHead() {
		return head;
	}

	public void setHead(BASE_HEAD head) {
		this.head = head;
	}
	
	

}