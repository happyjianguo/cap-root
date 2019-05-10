package com.fxbank.cap.ceba.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;


/** 
* @ClassName: REQ_30042000902 
* @Description: 缴费单销账请求
* @作者 杜振铎
* @date 2019年5月10日 下午1:29:04 
*  
*/
public class REQ_30042000902 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30042000902(){
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

		@JSONField(name = "CITY_CODE")
		private String cityCode;
		
		@JSONField(name = "PROJ_CODE")
		private String projCode;
		
		@JSONField(name = "CUSTOMER_NAME")
		private String customerName;

		@JSONField(name = "PAY_ACCOUNT")
		private String payAccount;
		
		@JSONField(name = "PIN")
		private String pin;
		
		@JSONField(name = "PAY_AMOUNT")
		private String payAmount;
		
		@JSONField(name = "AC_TYPE")
		private String acType;
		
		@JSONField(name = "BILL_NO")
		private String billNo;
		
		@JSONField(name = "PAY_DATE")
		private String payDate;
		
		@JSONField(name = "CONTRACT_NO")
		private String contractNo;

		public String getBillKey() {
			return billKey;
		}

		public void setBillKey(String billKey) {
			this.billKey = billKey;
		}

		public String getCityCode() {
			return cityCode;
		}

		public void setCityCode(String cityCode) {
			this.cityCode = cityCode;
		}

		public String getProjCode() {
			return projCode;
		}

		public void setProjCode(String projCode) {
			this.projCode = projCode;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public String getPayAccount() {
			return payAccount;
		}

		public void setPayAccount(String payAccount) {
			this.payAccount = payAccount;
		}

		public String getPin() {
			return pin;
		}

		public void setPin(String pin) {
			this.pin = pin;
		}

		public String getPayAmount() {
			return payAmount;
		}

		public void setPayAmount(String payAmount) {
			this.payAmount = payAmount;
		}

		public String getAcType() {
			return acType;
		}

		public void setAcType(String acType) {
			this.acType = acType;
		}

		public String getBillNo() {
			return billNo;
		}

		public void setBillNo(String billNo) {
			this.billNo = billNo;
		}

		public String getPayDate() {
			return payDate;
		}

		public void setPayDate(String payDate) {
			this.payDate = payDate;
		}

		public String getContractNo() {
			return contractNo;
		}

		public void setContractNo(String contractNo) {
			this.contractNo = contractNo;
		}
		
		
	}
}
