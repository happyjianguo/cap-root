package com.fxbank.cap.ceba.dto.ceba;

import java.io.Serializable;
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
public class REP_BJCEBBCRes extends REP_BASE {

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
		private String payAmount = null;
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
		public String getPayAmount() {
			return payAmount;
		}
		public void setPayAmount(String payAmount) {
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

	/**
	 * @Title: chanFixPack @Description: TODO(这里用一句话描述这个方法的作用) @param @param pack
	 * 设定文件 @throws
	 */
	@Override
	public void chanFixPack(String pack) {
		REP_BJCEBBCRes rep = (REP_BJCEBBCRes) CebaXmlUtil.xmlToObject(this.getClass(), pack);
		this.setHead(rep.getHead());
		this.setTout(rep.getTout());
	}

	/**
	 * @Title: creaFixPack @Description: TODO(这里用一句话描述这个方法的作用) @param @return
	 * 设定文件 @throws
	 */
	@Override
	public String creaFixPack() {
		return CebaXmlUtil.objectToXml(this);
	}

}
