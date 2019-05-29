package com.fxbank.cap.ykwm.dto.ykwm;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

public class REQ_QueryByOther extends REQ_BASE {

	public REQ_QueryByOther() {
		super.setTtxnNm("Query ByOther");
	}

	@FixedField(order = 2, len = 8, desc = "公司ID")
	private String companyID;

	@FixedField(order = 3, len = 100, desc = "用户姓名")
	private String ownerName;

	@FixedField(order = 4, len = 20, desc = "信息类别")
	private String fieldKind;
	
	@FixedField(order = 5, len = 200, desc = "信息内容")
	private String fieldValue;

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getFieldKind() {
		return fieldKind;
	}

	public void setFieldKind(String fieldKind) {
		this.fieldKind = fieldKind;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}


}