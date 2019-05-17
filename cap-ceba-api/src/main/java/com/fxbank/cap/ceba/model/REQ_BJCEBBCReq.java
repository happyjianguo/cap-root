package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.cap.ceba.util.CebaXmlUtil;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: REQ_BJCEBBCReq 
* @Description: 缴费单销账请求 
* @作者 杜振铎
* @date 2019年5月10日 下午2:06:41 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "in")
public class REQ_BJCEBBCReq extends REQ_BASE2 {

	private static final long serialVersionUID = -5970071351047001526L;

	private Tin tin = new Tin();

	public Tin getTin() {
		return tin;
	}

	public void setTin(Tin tin) {
		this.tin = tin;
	}

	public REQ_BJCEBBCReq() {
		super(null, 0, 0, 0);
	}

	public REQ_BJCEBBCReq(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "billKey", "companyId", "billNo", "payDate", "filed1", "filed2", 
			"filed3", "filed4","customerName","payAccount","pin","payAmount","acType","contractNo" })
	public static class Tin implements Serializable {
		private static final long serialVersionUID = -581103924009687799L;
		private String billKey = null;
		private String companyId = null;
		private String billNo = null;
		private String payDate = null;
		private String filed1 = "";
		private String filed2 = "";
		private String filed3 = "";
		private String filed4 = "";
		private String customerName = "";
		private String payAccount = "";
		private String pin = "";
		private BigDecimal payAmount;
		private String acType = "";
		private String contractNo = "";
		public String getBillKey() {
			return billKey;
		}
		public void setBillKey(String billKey) {
			this.billKey = billKey;
		}
		public String getCompanyId() {
			return companyId;
		}
		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}
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
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getPayAccount() {
			return payAccount;
		}
		public void setPayAccount(String payAccount) {
			this.payAccount = payAccount;
		}
		public String getPin() {
			return pin;
		}
		public void setPin(String pin) {
			this.pin = pin;
		}
		public BigDecimal getPayAmount() {
			return payAmount;
		}
		public void setPayAmount(BigDecimal payAmount) {
			this.payAmount = payAmount;
		}
		public String getAcType() {
			return acType;
		}
		public void setAcType(String acType) {
			this.acType = acType;
		}
		public String getContractNo() {
			return contractNo;
		}
		public void setContractNo(String contractNo) {
			this.contractNo = contractNo;
		}

		
	}

	@Override
	public void chanFixPack(String pack) {
		REQ_BJCEBBCReq req = (REQ_BJCEBBCReq) CebaXmlUtil.xmlToObject(this.getClass(), pack);
		this.setHead(req.getHead());
		req.getTin().setPayAmount(req.getTin().getPayAmount().movePointLeft(2).setScale(2,BigDecimal.ROUND_HALF_UP ));
		this.setTin(req.getTin());
	}

	@Override
	public String creaFixPack() {
		this.getTin().setPayAmount(this.getTin().getPayAmount().movePointRight(2).setScale(2,BigDecimal.ROUND_HALF_UP ));
		return CebaXmlUtil.objectToXml(this);
	}

}
