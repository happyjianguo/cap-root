package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.cap.ceba.util.CebaXmlUtil;
import com.fxbank.cip.base.log.MyLog;

/** 
* @ClassName: QBIRRes 
* @Description: 查询缴费单信息应答
* @作者 杜振铎
* @date 2019年5月7日 下午5:00:06 
*  
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "out")
public class QBIRRes extends REP_BASE {

	private static final long serialVersionUID = -5970071351047001526L;

	private Tout tout = new Tout();

	public Tout getTout() {
		return tout;
	}

	public void setTout(Tout tout) {
		this.tout = tout;
	}

	public QBIRRes() {
		super(null, 0, 0, 0);
	}

	public QBIRRes(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "billKey", "companyId", "item1", "item2", "item3", "item4", "item5", "item6", "item7",
			"totalNum", "Data" })
	public static class Tout implements Serializable {
		private static final long serialVersionUID = -581103924009687799L;
		private String billKey = null;
		private String companyId = null;
		private String item1 = "";
		private String item2 = "";
		private String item3 = "";
		private String item4 = "";
		private String item5 = "";
		private String item6 = "";
		private String item7 = "";
		private String totalNum = null;
		private List<Data> Data = null;

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

		public String getItem1() {
			return item1;
		}

		public void setItem1(String item1) {
			this.item1 = item1;
		}

		public String getItem2() {
			return item2;
		}

		public void setItem2(String item2) {
			this.item2 = item2;
		}

		public String getItem3() {
			return item3;
		}

		public void setItem3(String item3) {
			this.item3 = item3;
		}

		public String getItem4() {
			return item4;
		}

		public void setItem4(String item4) {
			this.item4 = item4;
		}

		public String getItem5() {
			return item5;
		}

		public void setItem5(String item5) {
			this.item5 = item5;
		}

		public String getItem6() {
			return item6;
		}

		public void setItem6(String item6) {
			this.item6 = item6;
		}

		public String getItem7() {
			return item7;
		}

		public void setItem7(String item7) {
			this.item7 = item7;
		}

		public String getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(String totalNum) {
			this.totalNum = totalNum;
		}

		public List<Data> getData() {
			return Data;
		}

		public void setData(List<Data> data) {
			Data = data;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(propOrder = { "contractNo", "customerName", "balance", "payAmount", "beginDate", "endDate", "filed1",
				"filed2", "filed3", "filed4", "filed5" })
		public static class Data implements Serializable {

			private static final long serialVersionUID = -8514100631244728466L;
			private String contractNo = null;
			private String customerName = null;
			private String balance = null;
			private String payAmount = null;
			private String beginDate = null;
			private String endDate = null;
			private String filed1 = "";
			private String filed2 = "";
			private String filed3 = "";
			private String filed4 = "";
			private String filed5 = "";

			public String getContractNo() {
				return contractNo;
			}

			public void setContractNo(String contractNo) {
				this.contractNo = contractNo;
			}

			public String getCustomerName() {
				return customerName;
			}

			public void setCustomerName(String customerName) {
				this.customerName = customerName;
			}

			public String getBalance() {
				return balance;
			}

			public void setBalance(String balance) {
				this.balance = balance;
			}

			public String getPayAmount() {
				return payAmount;
			}

			public void setPayAmount(String payAmount) {
				this.payAmount = payAmount;
			}

			public String getBeginDate() {
				return beginDate;
			}

			public void setBeginDate(String beginDate) {
				this.beginDate = beginDate;
			}

			public String getEndDate() {
				return endDate;
			}

			public void setEndDate(String endDate) {
				this.endDate = endDate;
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

			public String getFiled5() {
				return filed5;
			}

			public void setFiled5(String filed5) {
				this.filed5 = filed5;
			}

		}

	}

	/**
	 * @Title: chanFixPack @Description: TODO(这里用一句话描述这个方法的作用) @param @param pack
	 * 设定文件 @throws
	 */
	@Override
	public void chanFixPack(String pack) {
		QBIRRes res = (QBIRRes) CebaXmlUtil.xmlToObject(this.getClass(), pack);
		this.setHead(res.getHead());
		this.setTout(res.getTout());
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
