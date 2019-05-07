package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.FIXP_SERIAL;
import com.fxbank.cip.base.model.ModelBase;


/** 
* @ClassName: REQ_BASE 
* @Description: 请求报文基类 
* @作者 杜振铎
* @date 2019年5月7日 下午5:05:06 
*  
*/
@XmlRootElement(name = "in")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class REQ_BASE extends ModelBase implements Serializable,FIXP_SERIAL{

	private static final long serialVersionUID = -6652288226005628489L;

	private BASE_HEAD head = new BASE_HEAD();
	
	public REQ_BASE() {
		super(null, 0, 0, 0);
	}
	
	public REQ_BASE(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	public BASE_HEAD getHead() {
		return head;
	}

	public void setHead(BASE_HEAD head) {
		this.head = head;
	}

}