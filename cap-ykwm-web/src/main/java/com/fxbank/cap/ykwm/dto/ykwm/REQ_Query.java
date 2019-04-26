package com.fxbank.cap.ykwm.dto.ykwm;

public class REQ_Query extends REQ_BASE {

	private String companyID;

	private String cardNum;

	private String batchNum;

	public REQ_Query() {
		super.txDesc = "查询欠费";
		super.log = false;
	}

	@Override
	public String creaFixPack() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.getHeader().creaFixPack());
		sb.append(this.companyID == null ? "" : this.companyID).append("|");
		sb.append(this.cardNum == null ? "" : this.cardNum).append("|");
		sb.append(this.batchNum == null ? "" : this.batchNum).append("|");

		return sb.toString();
	}

	@Override
	public void chanFixPack(String pack) {
		String[] array = pack.split("\\|");
		super.getHeader().chanFixPack(array[0]);
		this.companyID = array[1].trim();
		this.cardNum = array[2].trim();
		this.batchNum = array[3].trim();
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

}