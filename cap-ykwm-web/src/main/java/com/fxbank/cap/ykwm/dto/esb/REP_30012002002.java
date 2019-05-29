package com.fxbank.cap.ykwm.dto.esb;

import java.io.Serializable;
import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

public class REP_30012002002 extends REP_BASE {

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

		@JSONField(name = "CHANNEL_DATE")
		private String channelDate; // 渠道日期

		@JSONField(name = "CHANNEL_SEQ_NO")
		private String channelSeqNo; // 渠道日期

		@JSONField(name = "CODE_ARRAY")
		private List<Code> codeArray;

		public String getChannelDate() {
			return channelDate;
		}

		public void setChannelDate(String channelDate) {
			this.channelDate = channelDate;
		}

		public String getChannelSeqNo() {
			return channelSeqNo;
		}

		public void setChannelSeqNo(String channelSeqNo) {
			this.channelSeqNo = channelSeqNo;
		}

		public List<Code> getCodeArray() {
			return codeArray;
		}

		public void setCodeArray(List<Code> codeArray) {
			this.codeArray = codeArray;
		} // 取票码列表

		

	}
	public static class Code implements Serializable {
		private static final long serialVersionUID = -1165934873393382556L;
		@JSONField(name = "CODE")
		private String code;// 取票码
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		

	}
}
