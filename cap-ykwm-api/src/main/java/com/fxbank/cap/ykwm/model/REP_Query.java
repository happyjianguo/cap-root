package com.fxbank.cap.ykwm.model;

import java.util.List;
import java.io.Serializable;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/**
 * @ClassName: REP_Query
 * @Description: 欠费查询响应报文
 * @作者 杜振铎
 * @date 2019年4月29日 下午3:02:09
 * 
 */
public class REP_Query extends REP_BASE {

	private static final long serialVersionUID = -7372138980234360764L;

	@FixedField(order = 2, len = 100, desc = "用户姓名")
	private String ownerName;

	@FixedField(order = 3, len = 100, desc = "用户地址")
	private String address;

	@FixedField(order = 4, len = 15,scale = 2, desc = "最低交费金额")
	private Double minPayment;

	@FixedField(order = 5, len = 15,scale = 2, desc = "缴费金额")
	private Double total;

	@FixedField(order = 6, len = 4, cyc = "data", desc = "循环次数")
	private Integer cyc;

	@FixedField(order = 7, desc = "欠费明细")
	private List<AccountDetail> data;

	@FixedField(order = 8, len = 100, desc = "描述")
	private String description;

	@FixedField(order = 9, len = 16, desc = "查询流水号")
	private String checkNum;
	
	@FixedField(order = 10, len = 100, desc = "缴费时需要填写的其他信息，如发票信息等")
	private String ohterParams;

	@FixedField(order = 11, len = 20, desc = "扩展信息")
	private String extend;
	
	@FixedField(order = 12, len = 4, cyc = "expressList", desc = "循环次数")
	private Integer cyc1;

	@FixedField(order = 13, desc = "快递公司列表")
	private List<Express> expressList;
	
	
	public Integer getCyc() {
		return cyc;
	}

	public void setCyc(Integer cyc) {
		this.cyc = cyc;
	}

	public Integer getCyc1() {
		return cyc1;
	}

	public void setCyc1(Integer cyc1) {
		this.cyc1 = cyc1;
	}

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

	public Double getMinPayment() {
		return minPayment;
	}

	public void setMinPayment(Double minPayment) {
		this.minPayment = minPayment;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getOhterParams() {
		return ohterParams;
	}

	public void setOhterParams(String ohterParams) {
		this.ohterParams = ohterParams;
	}

	public List<AccountDetail> getData() {
		return data;
	}

	public void setData(List<AccountDetail> data) {
		this.data = data;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public List<Express> getExpressList() {
		return expressList;
	}

	public void setExpressList(List<Express> expressList) {
		this.expressList = expressList;
	}

	@Deprecated
	public REP_Query() {
		super(null, 0, 0, 0);
	}

    public REP_Query(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
    }

	public static class AccountDetail implements Serializable{
		private static final long serialVersionUID = -4929236840465084758L;
		@FixedField(order = 101, len = 9, desc = "供暖年度")
		private String chargeYear;
		@FixedField(order = 102, len = 20, desc = "供暖类型")
		private String itemName;
		@FixedField(order = 103, len = 15,scale = 2, desc = "供暖面积")
		private Double area;
		@FixedField(order = 104, len = 15,scale = 2, desc = "供暖单价")
		private Double price;
		@FixedField(order = 105, len = 15,scale = 2, desc = "欠费金额")
		private Double account;
		@FixedField(order = 106, len = 15,scale = 2, desc = "优惠金额")
		private Double agio;
		@FixedField(order = 107, len = 15,scale = 2, desc = "滞纳金")
		private Double lateFee;
		@FixedField(order = 108, len = 15,scale = 2, desc = "应交金额")
		private Double payment;

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

		public Double getArea() {
			return area;
		}

		public void setArea(Double area) {
			this.area = area;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public Double getAccount() {
			return account;
		}

		public void setAccount(Double account) {
			this.account = account;
		}

		public Double getAgio() {
			return agio;
		}

		public void setAgio(Double agio) {
			this.agio = agio;
		}

		public Double getLateFee() {
			return lateFee;
		}

		public void setLateFee(Double lateFee) {
			this.lateFee = lateFee;
		}

		public Double getPayment() {
			return payment;
		}

		public void setPayment(Double payment) {
			this.payment = payment;
		}

	}

	public static class Express implements Serializable{
		private static final long serialVersionUID = -8528453695627371265L;
		@FixedField(order = 201, len = 8, desc = "快递公司ID")
		private Integer expressID;
		@FixedField(order = 202, len = 20, desc = "快递公司")
		private String express;
		@FixedField(order = 203, len = 15,scale = 2, desc = "快递费金额")
		private Double price;

		public Integer getExpressID() {
			return expressID;
		}

		public void setExpressID(Integer expressID) {
			this.expressID = expressID;
		}

		public String getExpress() {
			return express;
		}

		public void setExpress(String express) {
			this.express = express;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

	}

}