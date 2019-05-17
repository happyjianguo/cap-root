package com.fxbank.cap.ceba.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.cap.ceba.util.CebaXmlUtil;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: REQ_BJCEBBRQReq 
* @Description: 销账结果查询请求 
* @作者 杜振铎
* @date 2019年5月10日 下午2:06:41 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "in")
public class REQ_BJCEBBRQReq extends REQ_BASE2 {

	private static final long serialVersionUID = -5970071351047001526L;

	private Tin tin = new Tin();

	public Tin getTin() {
		return tin;
	}

	public void setTin(Tin tin) {
		this.tin = tin;
	}

	public REQ_BJCEBBRQReq() {
		super(null, 0, 0, 0);
	}

	public REQ_BJCEBBRQReq(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "billNo", "payDate", "filed1", "filed2", "filed3", "filed4" })
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
		REQ_BJCEBBRQReq req = (REQ_BJCEBBRQReq) CebaXmlUtil.xmlToObject(this.getClass(), pack);
		this.setHead(req.getHead());
		this.setTin(req.getTin());
	}

	@Override
	public String creaFixPack() {
		return CebaXmlUtil.objectToXml(this);
	}

}
