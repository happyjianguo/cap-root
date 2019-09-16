package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_30061000501 extends ESB_BASE {
	private static final long serialVersionUID = -3444269694697322871L;

	public ESB_REQ_30061000501(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("300610005", "01");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = 3962007451257916596L;
		@JSONField(name = "ORIG_CHANNEL_SEQ_NO")
		private String origChannelSeqNo;// 原核心流水号
		@JSONField(name = "DEBIT_BASE_ACCT_NO")
		private String debitBaseAcctNo;// 借方账号
		@JSONField(name = "DEBIT_NAME")
		private String debitName;// 借方户名
		@JSONField(name = "CREDIT_BASE_ACCT_NO")
		private String creditBaseAcctNo;// 贷方账号
		@JSONField(name = "CREDIT_NAME")
		private String creditName;// 贷方户名
		@JSONField(name = "TRAN_TYPE")
		private String tranType;// 交易类型
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;// 交易金额
		@JSONField(name = "PY_FEE_TYPE")
		private String pyFeeType;// 缴费类型
		@JSONField(name = "REVERSAL_REASON")
		private String reversalReason;// 冲正原因

		public String getOrigChannelSeqNo() {
			return origChannelSeqNo;
		}

		public void setOrigChannelSeqNo(String origChannelSeqNo) {
			this.origChannelSeqNo = origChannelSeqNo;
		}

		public String getDebitBaseAcctNo() {
			return debitBaseAcctNo;
		}

		public void setDebitBaseAcctNo(String debitBaseAcctNo) {
			this.debitBaseAcctNo = debitBaseAcctNo;
		}

		public String getDebitName() {
			return debitName;
		}

		public void setDebitName(String debitName) {
			this.debitName = debitName;
		}

		public String getCreditBaseAcctNo() {
			return creditBaseAcctNo;
		}

		public void setCreditBaseAcctNo(String creditBaseAcctNo) {
			this.creditBaseAcctNo = creditBaseAcctNo;
		}

		public String getCreditName() {
			return creditName;
		}

		public void setCreditName(String creditName) {
			this.creditName = creditName;
		}

		public String getTranType() {
			return tranType;
		}

		public void setTranType(String tranType) {
			this.tranType = tranType;
		}

		public String getTranAmt() {
			return tranAmt;
		}

		public void setTranAmt(String tranAmt) {
			this.tranAmt = tranAmt;
		}

		public String getPyFeeType() {
			return pyFeeType;
		}

		public void setPyFeeType(String pyFeeType) {
			this.pyFeeType = pyFeeType;
		}

		public String getReversalReason() {
			return reversalReason;
		}

		public void setReversalReason(String reversalReason) {
			this.reversalReason = reversalReason;
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