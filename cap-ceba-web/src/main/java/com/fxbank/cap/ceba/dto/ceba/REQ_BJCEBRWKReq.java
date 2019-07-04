package com.fxbank.cap.ceba.dto.ceba;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.fxbank.cap.ceba.util.CebaXmlUtil;


/** 
* @ClassName: REQ_BJCEBRWKReq 
* @Description: 申请工作密钥请求
* @作者 杜振铎
* @date 2019年5月16日 下午4:39:09 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "in")
public class REQ_BJCEBRWKReq extends DTO_BASE {

	private Tin tin = new Tin();

	public Tin getTin() {
		return tin;
	}

	public void setTin(Tin tin) {
		this.tin = tin;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "partnerCode", "operationDate"})
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
		REQ_BJCEBRWKReq res = (REQ_BJCEBRWKReq) CebaXmlUtil.xmlToObject(this.getClass(), pack);
		this.setHead(res.getHead());
		this.setTin(res.getTin());
		
	}

	@Override
	public String creaFixPack() {
		return CebaXmlUtil.objectToXml(this);
	}

}
