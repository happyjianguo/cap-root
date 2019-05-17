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
* @ClassName: REP_BJCEBBRQRRes 
* @Description: 销账结果查询应答
* @作者 杜振铎
* @date 2019年5月10日 下午2:06:27 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "out")
public class REP_BJCEBBRQRes extends REP_BASE {

	private static final long serialVersionUID = -5970071351047001526L;

	private Tout tout = new Tout();

	public Tout getTout() {
		return tout;
	}

	public void setTout(Tout tout) {
		this.tout = tout;
	}

	public REP_BJCEBBRQRes() {
		super(null, 0, 0, 0);
	}

	public REP_BJCEBBRQRes(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "billKey", "billNo", "payDate", "bankBillNo", "payAmount", "payState", 
			"filed1" ,"filed2","filed3","filed4"})
	public static class Tout implements Serializable {
		private static final long serialVersionUID = -581103924009687799L;
		private String billKey = null;
		private String billNo = null;
		private String payDate = null;
		private String bankBillNo = null;
		private BigDecimal payAmount = null;
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
		public BigDecimal getPayAmount() {
			return payAmount;
		}
		public void setPayAmount(BigDecimal payAmount) {
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

	@Override
	public void chanFixPack(String pack) {
		REP_BJCEBBRQRes res = (REP_BJCEBBRQRes) CebaXmlUtil.xmlToObject(this.getClass(), pack);
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
