package com.fxbank.cap.ykwm.dto.esb;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30063001502 
* @Description: 营口供热模糊查询应答
* @作者 杜振铎
* @date 2019年5月31日 下午2:25:47 
*  
*/
public class REP_30063001502 extends REP_BASE {

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

		@JSONField(name = "CARD_INFO_ARRAY")
		private List<CardInfo> cardInfoArray;// 卡号信息数组

		public List<CardInfo> getCardInfoArray() {
			return cardInfoArray;
		}

		public void setCardInfoArray(List<CardInfo> cardInfoArray) {
			this.cardInfoArray = cardInfoArray;
		}

	}

	public static class CardInfo {
		@JSONField(name = "USERNAME")
		private String username;// 用户姓名
		@JSONField(name = "USER_ADDR")
		private String userAddr;// 用户地址
		@JSONField(name = "CARD_NO")
		private String cardNo;// 卡号

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getUserAddr() {
			return userAddr;
		}

		public void setUserAddr(String userAddr) {
			this.userAddr = userAddr;
		}

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

	}
}
