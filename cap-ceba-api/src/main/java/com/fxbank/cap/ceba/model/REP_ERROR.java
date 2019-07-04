package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.cap.ceba.util.CebaXmlUtil;
import com.fxbank.cip.base.log.MyLog;


/** 
* @ClassName: REP_ERROR 
* @Description: 错误应答报文
* @作者 杜振铎
* @date 2019年5月7日 下午5:04:13 
*  
*/
@XmlRootElement(name = "out")
@XmlAccessorType(XmlAccessType.FIELD)
public class REP_ERROR extends MODEL_BASE{

	private static final long serialVersionUID = -6652288226005628489L;

	private Tout tout = new Tout();

	public Tout getTout() {
		return tout;
	}

	public void setTout(Tout tout) {
		this.tout = tout;
	}
	
	public REP_ERROR() {
		super(null, 0, 0, 0);
	}
	
	public REP_ERROR(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "errorCode", "errorMessage","errorDetail" })
	public static class Tout implements Serializable {

		private static final long serialVersionUID = -5869723667927581751L;
		private String errorCode;
		private String errorMessage;
		private String errorDetail;
		public String getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		public String getErrorDetail() {
			return errorDetail;
		}
		public void setErrorDetail(String errorDetail) {
			this.errorDetail = errorDetail;
		}
		
		
	}

	/** 
	* @Title: chanFixPack 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param pack    设定文件 
	* @throws 
	*/
	@Override
	public void chanFixPack(String pack) {
		REP_ERROR res = (REP_ERROR) CebaXmlUtil.xmlToObject(this.getClass(), pack);
		this.setHead(res.getHead());
		this.setTout(res.getTout());
		
	}

	/** 
	* @Title: creaFixPack 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public String creaFixPack() {
		return CebaXmlUtil.objectToXml(this);
	}

}