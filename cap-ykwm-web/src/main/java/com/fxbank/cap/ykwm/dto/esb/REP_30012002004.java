package com.fxbank.cap.ykwm.dto.esb;

import java.io.Serializable;
import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

public class REP_30012002004 extends REP_BASE {

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
		@JSONField(name = "INFO_ARRAY")
		private List<Info> infoArray;

		public List<Info> getInfoArray() {
			return infoArray;
		}

		public void setInfoArray(List<Info> infoArray) {
			this.infoArray = infoArray;
		}
		
	}
	public static class Info implements Serializable{

		private static final long serialVersionUID = 2060548820745464992L;
		
		@JSONField(name = "OWNER_NAME")
		private String ownerName;
		
		@JSONField(name = "ADDRESS")
		private String address;
		
		@JSONField(name = "CARD_NUM")
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
