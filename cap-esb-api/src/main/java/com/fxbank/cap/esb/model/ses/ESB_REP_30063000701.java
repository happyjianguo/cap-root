package com.fxbank.cap.esb.model.ses;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REP_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REP_SYS_HEAD;

public class ESB_REP_30063000701 extends ESB_BASE {

	private static final long serialVersionUID = 2762871589276227931L;

	@Deprecated
	public ESB_REP_30063000701() {
		super(null, 0, 0, 0);
	}

	public ESB_REP_30063000701(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REP_APP_HEAD repAppHead = new ESB_REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REP_SYS_HEAD repSysHead = new ESB_REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody;

	public class REP_BODY implements Serializable {

		private static final long serialVersionUID = -7874366322170183215L;
		@JSONField(name = "DEBIT_BASE_ACCT_NO")
		private String debitBaseAcctNo;// 借方账号
		@JSONField(name = "DEBIT_NAME")
		private String debitName;// 借方户名
		@JSONField(name = "CREDIT_BASE_ACCT_NO")
		private String creditBaseAcctNo;// 贷方账号
		@JSONField(name = "CREDIT_NAME")
		private String creditName;// 贷方户名
		@JSONField(name = "ACPT_TELLER")
		private String acptTeller;// 受理柜员
		@JSONField(name = "TRAN_AMT")
		private String tranAmt;// 交易金额
		@JSONField(name = "USER_ADDR")
		private String userAddr;// 用户地址
		@JSONField(name = "USER_NO")
		private String userNo;// 用户编号
		@JSONField(name = "TRAN_TYPE")
		private String tranType;// 交易类型

		public String getTranType() {
			return tranType;
		}

		public void setTranType(String tranType) {
			this.tranType = tranType;
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

		public String getAcptTeller() {
			return acptTeller;
		}

		public void setAcptTeller(String acptTeller) {
			this.acptTeller = acptTeller;
		}

		public String getTranAmt() {
			return tranAmt;
		}

		public void setTranAmt(String tranAmt) {
			this.tranAmt = tranAmt;
		}

		public String getUserAddr() {
			return userAddr;
		}

		public void setUserAddr(String userAddr) {
			this.userAddr = userAddr;
		}

		public String getUserNo() {
			return userNo;
		}

		public void setUserNo(String userNo) {
			this.userNo = userNo;
		}

	}

	public REP_BODY getRepBody() {
		return repBody;
	}

	public void setRepBody(REP_BODY repBody) {
		this.repBody = repBody;
	}

	public ESB_REP_APP_HEAD getRepAppHead() {
		return repAppHead;
	}

	public void setRepAppHead(ESB_REP_APP_HEAD repAppHead) {
		this.repAppHead = repAppHead;
	}

	public ESB_REP_SYS_HEAD getRepSysHead() {
		return repSysHead;
	}

	public void setRepSysHead(ESB_REP_SYS_HEAD repSysHead) {
		this.repSysHead = repSysHead;
	}

}
