package com.fxbank.cap.ceba.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.cap.ceba.util.CebaXmlUtil;
import com.fxbank.cip.base.log.MyLog;


/** 
* @ClassName: REP_BJCEBRWKRes 
* @Description: 工作密钥申请应答
* @作者 杜振铎
* @date 2019年5月16日 下午4:35:34 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "out")
public class REP_BJCEBRWKRes extends MODEL_BASE {

	private static final long serialVersionUID = -5970071351047001526L;

	private Tout tout = new Tout();

	public Tout getTout() {
		return tout;
	}

	public void setTout(Tout tout) {
		this.tout = tout;
	}

	public REP_BJCEBRWKRes() {
		super(null, 0, 0, 0);
	}

	public REP_BJCEBRWKRes(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "partnerCode", "returnCode","errorDescription","keyName","keyValue",
			"verifyValue","keyName1","keyValue1","verifyValue1"})
	public static class Tout implements Serializable {
		private static final long serialVersionUID = -581103924009687799L;
		private String partnerCode = null;
		private String returnCode = null;
		private String errorDescription = null;
		private String keyName = null;
		private String keyValue = null;
		private String verifyValue = null;
		private String keyName1 = null;
		private String keyValue1 = null;
		private String verifyValue1 = null;
		public String getPartnerCode() {
			return partnerCode;
		}
		public void setPartnerCode(String partnerCode) {
			this.partnerCode = partnerCode;
		}
		public String getReturnCode() {
			return returnCode;
		}
		public void setReturnCode(String returnCode) {
			this.returnCode = returnCode;
		}
		public String getErrorDescription() {
			return errorDescription;
		}
		public void setErrorDescription(String errorDescription) {
			this.errorDescription = errorDescription;
		}
		public String getKeyName() {
			return keyName;
		}
		public void setKeyName(String keyName) {
			this.keyName = keyName;
		}
		public String getKeyValue() {
			return keyValue;
		}
		public void setKeyValue(String keyValue) {
			this.keyValue = keyValue;
		}
		public String getVerifyValue() {
			return verifyValue;
		}
		public void setVerifyValue(String verifyValue) {
			this.verifyValue = verifyValue;
		}
		public String getKeyName1() {
			return keyName1;
		}
		public void setKeyName1(String keyName1) {
			this.keyName1 = keyName1;
		}
		public String getKeyValue1() {
			return keyValue1;
		}
		public void setKeyValue1(String keyValue1) {
			this.keyValue1 = keyValue1;
		}
		public String getVerifyValue1() {
			return verifyValue1;
		}
		public void setVerifyValue1(String verifyValue1) {
			this.verifyValue1 = verifyValue1;
		}
		

	}

	@Override
	public void chanFixPack(String pack) {
		REP_BJCEBRWKRes res = (REP_BJCEBRWKRes) CebaXmlUtil.xmlToObject(this.getClass(), pack);
		this.setHead(res.getHead());
		this.setTout(res.getTout());
	}

	@Override
	public String creaFixPack() {
		return CebaXmlUtil.objectToXml(this);
	}

}
