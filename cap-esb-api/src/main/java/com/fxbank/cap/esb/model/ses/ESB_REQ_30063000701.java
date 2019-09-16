package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30063000701 extends ESB_BASE {

	private static final long serialVersionUID = 2377359617721797980L;

	public ESB_REQ_30063000701(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300630007", "01");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = 1430992340964807626L;
		@JSONField(name = "ORIG_CHANNEL_SEQ_NO")
		private String origChannelSeqNo;// 原核心流水号
		@JSONField(name = "PY_FEE_TYPE")
		private String pyFeeType;// 缴费类型

		public String getOrigChannelSeqNo() {
			return origChannelSeqNo;
		}

		public void setOrigChannelSeqNo(String origChannelSeqNo) {
			this.origChannelSeqNo = origChannelSeqNo;
		}

		public String getPyFeeType() {
			return pyFeeType;
		}

		public void setPyFeeType(String pyFeeType) {
			this.pyFeeType = pyFeeType;
		}

	}

	public REQ_BODY getReqBody() {
		return reqBody;
	}

	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public ESB_REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}

	public void setReqAppHead(ESB_REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}

	public ESB_REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}

	public void setReqSysHead(ESB_REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}

}