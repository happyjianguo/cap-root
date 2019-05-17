package com.fxbank.cap.ceba.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.cap.ceba.util.CebaXmlUtil;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: REQ_BJCEBRWKReq 
* @Description: 工作密钥申请请求 
* @作者 杜振铎
* @date 2019年5月16日 下午4:33:18 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "in")
public class REQ_BJCEBRWKReq extends REQ_BASE2 {

	private static final long serialVersionUID = -5970071351047001526L;

	private Tin tin = new Tin();

	public Tin getTin() {
		return tin;
	}

	public void setTin(Tin tin) {
		this.tin = tin;
	}

	public REQ_BJCEBRWKReq() {
		super(null, 0, 0, 0);
	}

	public REQ_BJCEBRWKReq(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "partnerCode", "operationDate" })
	public static class Tin implements Serializable {
		private static final long serialVersionUID = -581103924009687799L;
		private String partnerCode = null;
		private String operationDate = null;
		public String getPartnerCode() {
			return partnerCode;
		}
		public void setPartnerCode(String partnerCode) {
			this.partnerCode = partnerCode;
		}
		public String getOperationDate() {
			return operationDate;
		}
		public void setOperationDate(String operationDate) {
			this.operationDate = operationDate;
		}
		
	}

	@Override
	public void chanFixPack(String pack) {
		REQ_BJCEBRWKReq req = (REQ_BJCEBRWKReq) CebaXmlUtil.xmlToObject(this.getClass(), pack);
		this.setHead(req.getHead());
		this.setTin(req.getTin());
	}

	@Override
	public String creaFixPack() {
		return CebaXmlUtil.objectToXml(this);
	}

}
