package com.fxbank.cap.ykwm.dto.ykwm;

import java.util.List;
import java.io.Serializable;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

public class REP_QueryByOther extends REP_BASE {

	@FixedField(order = 2, len = 4, cyc = "infoList", desc = "循环次数")
	private Integer cyc;

	@FixedField(order = 3, desc = "欠费明细")
	private List<Info> infoList;

	public Integer getCyc() {
		return cyc;
	}

	public void setCyc(Integer cyc) {
		this.cyc = cyc;
	}

	public List<Info> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}

	public REP_QueryByOther() {
		super.setResult("0");
	}
	
	public static class Info implements Serializable{
		private static final long serialVersionUID = -4929236840465084758L;
		@FixedField(order = 101, len = 20, desc = "姓名")
		private String ownerName;
		@FixedField(order = 102, len = 200, desc = "地址")
		private String address;
		@FixedField(order = 103, len = 20,scale = 2, desc = "卡号")
		private String cardNum;
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
		public String getCardNum() {
			return cardNum;
		}
		public void setCardNum(String cardNum) {
			this.cardNum = cardNum;
		}

	}
}