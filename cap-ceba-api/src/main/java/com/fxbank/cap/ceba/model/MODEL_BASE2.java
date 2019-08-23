package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.FIXP_SERIAL;

/** 
* @ClassName: REQ_BASE2 
* @Description: 请求报文基类 
* @作者 杜振铎
* @date 2019年5月7日 下午5:05:33 
*  
*/
@XmlRootElement(name = "out")
@XmlAccessorType(XmlAccessType.FIELD)
public class MODEL_BASE2 extends MODEL_BASE implements Serializable,FIXP_SERIAL{

	private static final long serialVersionUID = -6652288226005628489L;

	public MODEL_BASE2() {
		super(null, 0, 0, 0);
	}
	
	public MODEL_BASE2(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	/** 
	* @Title: chanFixPack 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param pack    设定文件 
	* @throws 
	*/
	@Override
	public void chanFixPack(String pack) {
		// TODO Auto-generated method stub
		
	}

	/** 
	* @Title: creaFixPack 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public String creaFixPack() {
		// TODO Auto-generated method stub
		return null;
	}

}