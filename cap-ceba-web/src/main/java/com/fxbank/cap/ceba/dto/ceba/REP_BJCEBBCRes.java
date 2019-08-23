package com.fxbank.cap.ceba.dto.ceba;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.fxbank.cap.ceba.util.CebaXmlUtil;


/** 
* @ClassName: REP_BJCEBBCRes 
* @Description: 缴费单销账
* @作者 杜振铎
* @date 2019年5月9日 下午5:03:43 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "out")
public class REP_BJCEBBCRes extends DTO_BASE {

	private static final long serialVersionUID = 6045069341585295661L;
	private Tout tout = new Tout();

	public Tout getTout() {
		return tout;
	}

	public void setTout(Tout tout) {
		this.tout = tout;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "billKey", "companyId", "billNo", "payDate", "payAmount", "bankBillNo", "receiptNo", "acctDate" })
	public static class Tout implements Serializable {
		private static final long serialVersionUID = -581103924009687799L;
		private String billKey = null;
		private String companyId = null;
		private String billNo = null;
		private String payDate = null;
		private BigDecimal payAmount = null;
		private String bankBillNo = null;
		private String receiptNo = null;
		private String acctDate = null;
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
		public BigDecimal getPayAmount() {
			return payAmount;
		}
		public void setPayAmount(BigDecimal payAmount) {
			this.payAmount = payAmount;
		}
		public String getBankBillNo() {
			return bankBillNo;
		}
		public void setBankBillNo(String bankBillNo) {
			this.bankBillNo = bankBillNo;
		}
		public String getReceiptNo() {
			return receiptNo;
		}
		public void setReceiptNo(String receiptNo) {
			this.receiptNo = receiptNo;
		}
		public String getAcctDate() {
			return acctDate;
		}
		public void setAcctDate(String acctDate) {
			this.acctDate = acctDate;
		}
	

	}

	@Override
	public void chanFixPack(String pack) {
		REP_BJCEBBCRes res = (REP_BJCEBBCRes) CebaXmlUtil.xmlToObject(this.getClass(), pack);
		this.setHead(res.getHead());
		res.getTout().setPayAmount(res.getTout().getPayAmount().movePointLeft(2).setScale(2,BigDecimal.ROUND_HALF_UP ));
		this.setTout(res.getTout());
	}

	@Override
	public String creaFixPack() {
		this.getTout().setPayAmount(this.getTout().getPayAmount().movePointRight(2).setScale(2,BigDecimal.ROUND_HALF_UP ));
		return CebaXmlUtil.objectToXml(this);
	}

}
