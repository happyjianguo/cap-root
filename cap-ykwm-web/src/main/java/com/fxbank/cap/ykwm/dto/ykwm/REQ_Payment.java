package com.fxbank.cap.ykwm.dto.ykwm;

public class REQ_Payment extends REQ_BASE {

	private String source;
	private String branchNum;
	private String batchNum;
	private String checkNum;
	private String payment;
	private String invoiceStyle;
	private String expressID;
	private String address;
	private String phone;
	private String userName;
	private String postCode;
	private String invoiceCount;
	private String invoiceTitle1;
	private String area1;
	private String invoiceName1;
	private String invoiceNum1;
	private String bankNum1;
	private String invoiceAddress1;

	public REQ_Payment() {
		super.txDesc = "热电缴费";
		super.log = false;
	}

	/**
	 * @return the invoiceAddress1
	 */
	public String getInvoiceAddress1() {
		return invoiceAddress1;
	}

	/**
	 * @param invoiceAddress1 the invoiceAddress1 to set
	 */
	public void setInvoiceAddress1(String invoiceAddress1) {
		this.invoiceAddress1 = invoiceAddress1;
	}

	/**
	 * @return the bankNum1
	 */
	public String getBankNum1() {
		return bankNum1;
	}

	/**
	 * @param bankNum1 the bankNum1 to set
	 */
	public void setBankNum1(String bankNum1) {
		this.bankNum1 = bankNum1;
	}

	/**
	 * @return the invoiceNum1
	 */
	public String getInvoiceNum1() {
		return invoiceNum1;
	}

	/**
	 * @param invoiceNum1 the invoiceNum1 to set
	 */
	public void setInvoiceNum1(String invoiceNum1) {
		this.invoiceNum1 = invoiceNum1;
	}

	/**
	 * @return the invoiceName1
	 */
	public String getInvoiceName1() {
		return invoiceName1;
	}

	/**
	 * @param invoiceName1 the invoiceName1 to set
	 */
	public void setInvoiceName1(String invoiceName1) {
		this.invoiceName1 = invoiceName1;
	}

	/**
	 * @return the area1
	 */
	public String getArea1() {
		return area1;
	}

	/**
	 * @param area1 the area1 to set
	 */
	public void setArea1(String area1) {
		this.area1 = area1;
	}

	/**
	 * @return the invoiceTitle1
	 */
	public String getInvoiceTitle1() {
		return invoiceTitle1;
	}

	/**
	 * @param invoiceTitle1 the invoiceTitle1 to set
	 */
	public void setInvoiceTitle1(String invoiceTitle1) {
		this.invoiceTitle1 = invoiceTitle1;
	}

	/**
	 * @return the invoiceCount
	 */
	public String getInvoiceCount() {
		return invoiceCount;
	}

	/**
	 * @param invoiceCount the invoiceCount to set
	 */
	public void setInvoiceCount(String invoiceCount) {
		this.invoiceCount = invoiceCount;
	}

	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the expressID
	 */
	public String getExpressID() {
		return expressID;
	}

	/**
	 * @param expressID the expressID to set
	 */
	public void setExpressID(String expressID) {
		this.expressID = expressID;
	}

	/**
	 * @return the invoiceStyle
	 */
	public String getInvoiceStyle() {
		return invoiceStyle;
	}

	/**
	 * @param invoiceStyle the invoiceStyle to set
	 */
	public void setInvoiceStyle(String invoiceStyle) {
		this.invoiceStyle = invoiceStyle;
	}

	/**
	 * @return the payment
	 */
	public String getPayment() {
		return payment;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}

	/**
	 * @return the checkNum
	 */
	public String getCheckNum() {
		return checkNum;
	}

	/**
	 * @param checkNum the checkNum to set
	 */
	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}

	/**
	 * @return the batchNum
	 */
	public String getBatchNum() {
		return batchNum;
	}

	/**
	 * @param batchNum the batchNum to set
	 */
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	/**
	 * @return the branchNum
	 */
	public String getBranchNum() {
		return branchNum;
	}

	/**
	 * @param branchNum the branchNum to set
	 */
	public void setBranchNum(String branchNum) {
		this.branchNum = branchNum;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

}