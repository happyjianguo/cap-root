package com.fxbank.cap.ceba.dto.ceba;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.fxbank.cap.ceba.util.CebaXmlUtil;


/** 
* @ClassName: REQ_BJCEBBRQReq 
* @Description: 销账结果查询请求
* @作者 杜振铎
* @date 2019年5月10日 下午4:27:17 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "in")
public class REQ_BJCEBBRQReq extends DTO_BASE {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 323206491315998233L;
	private Tin tin = new Tin();

	public Tin getTin() {
		return tin;
	}

	public void setTin(Tin tin) {
		this.tin = tin;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "billNo", "payDate","filed1","filed2","filed3","filed4"})
	public static class Tin implements Serializable {
		private static final long serialVersionUID = -581103924009687799L;
		private String billNo = null;
		private String payDate = null;
		private String filed1 = "";
		private String filed2 = "";
		private String filed3 = "";
		private String filed4 = "";
		public String getBillNo() {
			return billNo;
		}
		public void setBillNo(String billNo) {
			this.billNo = billNo;
		}
		public String getPayDate() {
			return payDate;
		}
		public void setPayDate(String payDate) {
			this.payDate = payDate;
		}
		public String getFiled1() {
			return filed1;
		}
		public void setFiled1(String filed1) {
			this.filed1 = filed1;
		}
		public String getFiled2() {
			return filed2;
		}
		public void setFiled2(String filed2) {
			this.filed2 = filed2;
		}
		public String getFiled3() {
			return filed3;
		}
		public void setFiled3(String filed3) {
			this.filed3 = filed3;
		}
		public String getFiled4() {
			return filed4;
		}
		public void setFiled4(String filed4) {
			this.filed4 = filed4;
		}

	}

	@Override
	public void chanFixPack(String pack) {
		REQ_BJCEBBRQReq res = (REQ_BJCEBBRQReq) CebaXmlUtil.xmlToObject(this.getClass(), pack);
		this.setHead(res.getHead());
		this.setTin(res.getTin());
		
	}

	@Override
	public String creaFixPack() {
		return CebaXmlUtil.objectToXml(this);
	}

}
