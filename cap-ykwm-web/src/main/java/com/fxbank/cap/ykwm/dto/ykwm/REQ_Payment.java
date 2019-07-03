package com.fxbank.cap.ykwm.dto.ykwm;

import java.util.List;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

public class REQ_Payment extends REQ_BASE {

	public REQ_Payment() {
		super.setTtxnNm("Payment");
	}
    
	@FixedField(order = 2, len = 20, desc = "缴费渠道")
	private String source;// 缴费渠道（柜面、手机银行、自助、个人网银、支付宝、微信）
	@FixedField(order = 3, len = 10, desc = "网点编号")
	private String branchNum;// 网点编号 我行的固定值
	@FixedField(order = 4, len = 8, desc = "批次号")
	private String batchNum;// 批次号，银行方提供的对账批次号 需和查询一致
	@FixedField(order = 5, len = 16, desc = "交易流水号")
	private String checkNum;// 交易流水号,此流水号为查询时返回的查询流水号
	@FixedField(order = 6, len = 15,scale = 2, desc = "用户缴费金额")
	private double payment;// 账号
	@FixedField(order = 7, len = 1, desc = "发票处理方式")
	private Integer invoiceStyle;
	@FixedField(order = 8, len = 8, desc = "快递公司id")
	private Integer expressID;
	@FixedField(order = 9, len = 200, desc = "邮寄地址")
	private String address;
	@FixedField(order = 10, len = 20, desc = "联系电话")
	private String phone;
	@FixedField(order = 11, len = 20, desc = "联系人")
	private String userName;
	@FixedField(order = 12, len = 6, desc = "邮编")
	private String postCode;
	@FixedField(order = 13, len = 1, cyc = "invoiceList", desc = "循环次数")
	private Integer invoiceCount;
	@FixedField(order = 14, desc = "发票明细")
	private List<Invoice> invoiceList;

	// 发票信息
	public static class Invoice{

		@FixedField(order = 101,len = 200, desc = "发票抬头")
		private String invoiceTitle;

		@FixedField(order = 102,len = 15, desc = "发票面积")
		private double area;
		
		@FixedField(order = 103,len = 100, desc = "发票姓名")
		private String invoiceName;

		@FixedField(order = 104,len = 50, desc = "纳税人识别号")
		private String invoiceNum;

		@FixedField(order = 105,len = 50, desc = "开户行账号")
		private String bankNum;

		@FixedField(order = 106,len = 50, desc = "地址电话")
		private String invoiceAddress;

		public String getInvoiceTitle() {
			return invoiceTitle;
		}

		public void setInvoiceTitle(String invoiceTitle) {
			this.invoiceTitle = invoiceTitle;
		}

		public double getArea() {
			return area;
		}

		public void setArea(double area) {
			this.area = area;
		}

		public String getInvoiceName() {
			return invoiceName;
		}

		public void setInvoiceName(String invoiceName) {
			this.invoiceName = invoiceName;
		}

		public String getInvoiceNum() {
			return invoiceNum;
		}

		public void setInvoiceNum(String invoiceNum) {
			this.invoiceNum = invoiceNum;
		}

		public String getBankNum() {
			return bankNum;
		}

		public void setBankNum(String bankNum) {
			this.bankNum = bankNum;
		}

		public String getInvoiceAddress() {
			return invoiceAddress;
		}

		public void setInvoiceAddress(String invoiceAddress) {
			this.invoiceAddress = invoiceAddress;
		}


	}

	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getBranchNum() {
		return branchNum;
	}
	public void setBranchNum(String branchNum) {
		this.branchNum = branchNum;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}
	public double getPayment() {
		return payment;
	}
	public void setPayment(double payment) {
		this.payment = payment;
	}
	public Integer getInvoiceStyle() {
		return invoiceStyle;
	}
	public void setInvoiceStyle(Integer invoiceStyle) {
		this.invoiceStyle = invoiceStyle;
	}
	public Integer getExpressID() {
		return expressID;
	}
	public void setExpressID(Integer expressID) {
		this.expressID = expressID;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public Integer getInvoiceCount() {
		return invoiceCount;
	}
	public void setInvoiceCount(Integer invoiceCount) {
		this.invoiceCount = invoiceCount;
	}
	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}
	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}



}