package com.fxbank.cap.ceba.dto.ceba;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fxbank.cap.ceba.model.BASE_HEAD;
import com.fxbank.cip.base.dto.DataTransObject;

/** 
* @ClassName: REQ_BASE 
* @Description: 请求报文基类
* @作者 杜振铎
* @date 2019年5月7日 下午5:16:48 
*  
*/
@XmlRootElement(name = "out")
@XmlAccessorType(XmlAccessType.FIELD)
public class REP_BASE extends DataTransObject implements Serializable{

	private static final long serialVersionUID = -5561637499408248141L;
	private BASE_HEAD head = new BASE_HEAD();

	public BASE_HEAD getHead() {
		return head;
	}

	public void setHead(BASE_HEAD head) {
		this.head = head;
	}
	
	

}