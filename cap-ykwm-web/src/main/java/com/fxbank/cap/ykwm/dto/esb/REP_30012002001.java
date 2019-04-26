package com.fxbank.cap.ykwm.dto.esb;

import java.io.Serializable;
import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

public class REP_30012002001 extends REP_BASE {

	@JSONField(name = "APP_HEAD")
	private REP_APP_HEAD repAppHead = new REP_APP_HEAD();
	
	@JSONField(name = "SYS_HEAD")
	private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();
	
	@JSONField(name = "BODY")
	private REP_BODY repBody = new REP_BODY();
	
	public REP_APP_HEAD getRepAppHead() {
		return repAppHead;
	}


	public void setRepAppHead(REP_APP_HEAD repAppHead) {
		this.repAppHead = repAppHead;
	}

	public REP_SYS_HEAD getRepSysHead() {
		return repSysHead;
	}



	public void setRepSysHead(REP_SYS_HEAD repSysHead) {
		this.repSysHead = repSysHead;
	}



	public REP_BODY getRepBody() {
		return repBody;
	}



	public void setRepBody(REP_BODY repBody) {
		this.repBody = repBody;
	}



	public class REP_BODY {

		@JSONField(name = "OWNER_NAME")
		private String ownerName;	//用户姓名
		@JSONField(name = "ADDRESS")
		private String address;	//用户地址
		@JSONField(name = "MIN_PAYMENT")
		private String minPayment;	//最低交费金额
		@JSONField(name = "TOTAL")
		private String total;	//缴费金额
		@JSONField(name = "UNIT")
		private String unit;	//工作单位
		@JSONField(name = "AREA")
		private String area;	//面积
		@JSONField(name = "ACCOUNT_ARRAY")
		private List<Account> accountArray;//欠费明细数组
		@JSONField(name = "DESCRIPTION")
		private String Description;	//描述
		@JSONField(name = "CHECK_NUM")
		private String checkNum;	//查询流水号
		@JSONField(name = "EXPRESS_ARRAY")
		private List<Express> expressArray;//欠费明细数组
		public String getOwnerName() {
			return ownerName;
		}
		public void setOwnerName(String ownerName) {
			this.ownerName = ownerName;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getMinPayment() {
			return minPayment;
		}
		public void setMinPayment(String minPayment) {
			this.minPayment = minPayment;
		}
		public String getTotal() {
			return total;
		}
		public void setTotal(String total) {
			this.total = total;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public List<Account> getAccountArray() {
			return accountArray;
		}
		public void setAccountArray(List<Account> accountArray) {
			this.accountArray = accountArray;
		}
		public String getDescription() {
			return Description;
		}
		public void setDescription(String description) {
			Description = description;
		}
		public String getCheckNum() {
			return checkNum;
		}
		public void setCheckNum(String checkNum) {
			this.checkNum = checkNum;
		}
		public List<Express> getExpressArray() {
			return expressArray;
		}
		public void setExpressArray(List<Express> expressArray) {
			this.expressArray = expressArray;
		}
		

	}
	public static class Account implements Serializable {
		private static final long serialVersionUID = -3664489887921965974L;
			@JSONField(name = "CHARGE_YEAR")
			private String chargeYear;//供暖年度
			@JSONField(name = "ITEM_NAME")
			private String itemName;//供暖类型
			@JSONField(name = "AREA")
			private String area;//供暖面积
			@JSONField(name = "PRICE")
			private String price;//供暖单价
			@JSONField(name = "ACCOUNT")
			private String account;//欠费金额
			@JSONField(name = "AGIO")
			private String agio;//优惠金额
			@JSONField(name = "LATEFEE")
			private String lateFee;//滞纳金
			@JSONField(name = "PAYMENT")
			private String payment;//应交金额
			public String getChargeYear() {
				return chargeYear;
			}
			public void setChargeYear(String chargeYear) {
				this.chargeYear = chargeYear;
			}
			public String getItemName() {
				return itemName;
			}
			public void setItemName(String itemName) {
				this.itemName = itemName;
			}
			public String getArea() {
				return area;
			}
			public void setArea(String area) {
				this.area = area;
			}
			public String getPrice() {
				return price;
			}
			public void setPrice(String price) {
				this.price = price;
			}
			public String getAccount() {
				return account;
			}
			public void setAccount(String account) {
				this.account = account;
			}
			public String getAgio() {
				return agio;
			}
			public void setAgio(String agio) {
				this.agio = agio;
			}
			public String getLateFee() {
				return lateFee;
			}
			public void setLateFee(String lateFee) {
				this.lateFee = lateFee;
			}
			public String getPayment() {
				return payment;
			}
			public void setPayment(String payment) {
				this.payment = payment;
			}
			
			
			}
	public static class Express implements Serializable {
		private static final long serialVersionUID = -2103359753361559611L;
			@JSONField(name = "EXPRESS_ID")
			private String expressID;//快递公司ID
			@JSONField(name = "EXPRESS")
			private String express;//快递公司
			@JSONField(name = "PRICE")
			private String price;//快递费金额
			public String getExpressID() {
				return expressID;
			}
			public void setExpressID(String expressID) {
				this.expressID = expressID;
			}
			public String getExpress() {
				return express;
			}
			public void setExpress(String express) {
				this.express = express;
			}
			public String getPrice() {
				return price;
			}
			public void setPrice(String price) {
				this.price = price;
			}
			
			
			}
}
