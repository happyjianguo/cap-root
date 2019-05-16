package com.fxbank.cap.ceba.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;


/** 
* @ClassName: REQ_30062001001 
* @Description: 缴费单销账请求
* @作者 杜振铎
* @date 2019年5月10日 下午1:29:04 
*  
*/
public class REQ_30062001001 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30062001001(){
		super.txDesc = "缴费单销账";
	}

	public REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}

	public void setReqAppHead(REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}

    @Override
	public REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}

    @Override
	public void setReqSysHead(REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}


	public REQ_BODY getReqBody() {
		return reqBody;
	}


	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public class REQ_BODY {

		@JSONField(name = "BILL_KEY")
		private String billKey;		

		@JSONField(name = "PY_CITY_CODE")
		private String pyCityCode;
		
		@JSONField(name = "PY_CREDIT_NO")
		private String pyCreditNo;
		
		@JSONField(name = "CLIENT_NNAE")
		private String clientNnae;

		@JSONField(name = "PAY_ACCT_NO")
		private String payAcctNo;
		
		@JSONField(name = "PASSWORD")
		private String password;
		
		@JSONField(name = "UNPAID_AMT")
		private String unpaidAmt;
		
		@JSONField(name = "ACCT_TYPE")
		private String acctType;
		
		@JSONField(name = "CASH_TRNSFR_FLAG")
		private String cashTrnsfrFlag;
		
		@JSONField(name = "CONTRACT_NO")
		private String contractNo;

		public String getBillKey() {
			return billKey;
		}

		public void setBillKey(String billKey) {
			this.billKey = billKey;
		}

		public String getPyCityCode() {
			return pyCityCode;
		}

		public void setPyCityCode(String pyCityCode) {
			this.pyCityCode = pyCityCode;
		}

		public String getPyCreditNo() {
			return pyCreditNo;
		}

		public void setPyCreditNo(String pyCreditNo) {
			this.pyCreditNo = pyCreditNo;
		}

		public String getClientNnae() {
			return clientNnae;
		}

		public void setClientNnae(String clientNnae) {
			this.clientNnae = clientNnae;
		}

		public String getPayAcctNo() {
			return payAcctNo;
		}

		public void setPayAcctNo(String payAcctNo) {
			this.payAcctNo = payAcctNo;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUnpaidAmt() {
			return unpaidAmt;
		}

		public void setUnpaidAmt(String unpaidAmt) {
			this.unpaidAmt = unpaidAmt;
		}

		public String getAcctType() {
			return acctType;
		}

		public void setAcctType(String acctType) {
			this.acctType = acctType;
		}

		public String getContractNo() {
			return contractNo;
		}

		public void setContractNo(String contractNo) {
			this.contractNo = contractNo;
		}

		public String getCashTrnsfrFlag() {
			return cashTrnsfrFlag;
		}

		public void setCashTrnsfrFlag(String cashTrnsfrFlag) {
			this.cashTrnsfrFlag = cashTrnsfrFlag;
		}

	}
}
