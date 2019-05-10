package com.fxbank.cap.ceba.dto.ceba;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.fxbank.cap.ceba.util.CebaXmlUtil;


/** 
* @ClassName: REP_BJCEBBRQRes 
* @Description: 销账结果查询应答
* @作者 杜振铎
* @date 2019年5月10日 下午4:29:28 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "out")
public class REP_BJCEBBRQRes extends REP_BASE {

	private Tout tout = new Tout();

	public Tout getTout() {
		return tout;
	}

	public void setTout(Tout tout) {
		this.tout = tout;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "billKey", "billNo", "payDate", "bankBillNo", "payAmount", "payState",
			"filed1", "filed2","filed3","filed4" })
	public static class Tout implements Serializable {
		private static final long serialVersionUID = -581103924009687799L;
		private String billKey = null;
		private String billNo = null;
		private String payDate = null;
		private String bankBillNo = null;
		private String payAmount = null;
		private String payState = null;
		private String filed1 = "";
		private String filed2 = "";
		private String filed3 = "";
		private String filed4 = "";
		public String getBillKey() {
			return billKey;
		}
		public void setBillKey(String billKey) {
			this.billKey = billKey;
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
		public String getBankBillNo() {
			return bankBillNo;
		}
		public void setBankBillNo(String bankBillNo) {
			this.bankBillNo = bankBillNo;
		}
		public String getPayAmount() {
			return payAmount;
		}
		public void setPayAmount(String payAmount) {
			this.payAmount = payAmount;
		}
		public String getPayState() {
			return payState;
		}
		public void setPayState(String payState) {
			this.payState = payState;
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

	/**
	 * @Title: chanFixPack @Description: TODO(这里用一句话描述这个方法的作用) @param @param pack
	 * 设定文件 @throws
	 */
	@Override
	public void chanFixPack(String pack) {
		REP_BJCEBBRQRes rep = (REP_BJCEBBRQRes) CebaXmlUtil.xmlToObject(this.getClass(), pack);
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
