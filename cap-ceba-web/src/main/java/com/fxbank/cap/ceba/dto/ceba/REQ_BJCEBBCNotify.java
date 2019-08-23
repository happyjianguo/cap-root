package com.fxbank.cap.ceba.dto.ceba;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.fxbank.cap.ceba.util.CebaXmlUtil;

/** 
* @ClassName: REQ_BJCEBBCNotify 
* @Description: 对账通知报文请求 
* @作者 杜振铎
* @date 2019年6月4日 上午8:35:07 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "in")
public class REQ_BJCEBBCNotify extends DTO_BASE {

	private static final long serialVersionUID = 2143423104986091174L;
	private Tin tin = new Tin();

	public Tin getTin() {
		return tin;
	}

	public void setTin(Tin tin) {
		this.tin = tin;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "date", "fileName", "signDate", "filed1"})
	public static class Tin implements Serializable {
		private static final long serialVersionUID = -581103924009687799L;
		private String date = null;
		private String fileName = null;
		private String signDate = null;
		private String filed1 = null;
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getSignDate() {
			return signDate;
		}
		public void setSignDate(String signDate) {
			this.signDate = signDate;
		}
		public String getFiled1() {
			return filed1;
		}
		public void setFiled1(String filed1) {
			this.filed1 = filed1;
		}
		

	}
	
	@Override
	public void chanFixPack(String pack) {
		REQ_BJCEBBCNotify res = (REQ_BJCEBBCNotify) CebaXmlUtil.xmlToObject(this.getClass(), pack);
		this.setHead(res.getHead());
		this.setTin(res.getTin());
		
	}

	@Override
	public String creaFixPack() {
		return CebaXmlUtil.objectToXml(this);
	}

}
