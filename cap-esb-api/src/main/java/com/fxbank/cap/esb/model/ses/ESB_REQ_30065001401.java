package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30065001401 extends ESB_BASE {

	private static final long serialVersionUID = -6312122418567812901L;

	public ESB_REQ_30065001401 (MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.macEnable = false;
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300650014", "01");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = -4232123355870429525L;
		@JSONField(name = "SETTLEMENT_DATE")
		private String settlementDate;//对账日期
		@JSONField(name = "BUSI_TYPE")
		private String busiType;//业务类型
		public String getSettlementDate() {
			return settlementDate;
		}
		public void setSettlementDate(String settlementDate) {
			this.settlementDate = settlementDate;
		}
		public String getBusiType() {
			return busiType;
		}
		public void setBusiType(String busiType) {
			this.busiType = busiType;
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