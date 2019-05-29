package com.fxbank.cap.ykwm.dto.ykwm;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

public class REQ_Cancel extends REQ_BASE {

	public REQ_Cancel() {
		super.setTtxnNm("Cancel");
	}

	@FixedField(order = 2, len = 20, desc = "查询渠道")
	private String source;

	@FixedField(order = 3, len = 8, desc = "批次号")
	private String batch;

	@FixedField(order = 4, len = 16, desc = "交易流水号")
	private String checkNum;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}

	

}