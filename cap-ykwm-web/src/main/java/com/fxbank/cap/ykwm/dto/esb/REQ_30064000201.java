package com.fxbank.cap.ykwm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30064000201 
* @Description: 营口供热冲正请求
* @作者 杜振铎
* @date 2019年5月31日 下午2:30:13 
*  
*/
public class REQ_30064000201 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;

	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;

	@JSONField(name = "BODY")
	private REQ_BODY reqBody;

	public REQ_30064000201() {
		super.txDesc = "营口供热冲正";
	}

	public class REQ_BODY {

		@JSONField(name = "CHANNEL_DATE")
		private String channelDate;// 渠道日期
		@JSONField(name = "CHANNEL_SEQ_NO")
		private String channelSeqNo;// 渠道流水号
		@JSONField(name = "REVOKE_REASON")
		private String revokeReason;// 撤销原因

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

		public String getRevokeReason() {
			return revokeReason;
		}

		public void setRevokeReason(String revokeReason) {
			this.revokeReason = revokeReason;
		}

	}

	public REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}

	public void setReqAppHead(REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}

	public REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}

	public void setReqSysHead(REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}

	public REQ_BODY getReqBody() {
		return reqBody;
	}

	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

}