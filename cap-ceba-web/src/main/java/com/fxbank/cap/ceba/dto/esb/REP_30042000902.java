package com.fxbank.cap.ceba.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;


/** 
* @ClassName: REP_30042000902 
* @Description: 缴费单销账
* @作者 杜振铎
* @date 2019年5月10日 下午1:43:44 
*  
*/
public class REP_30042000902 extends REP_BASE {

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

	@Override
	public REP_SYS_HEAD getRepSysHead() {
		return repSysHead;
	}


	@Override
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
		 @JSONField(name = "BANK_BILL_NO")
	     private String bankBillNo;
		 
		 @JSONField(name = "RECEIPT_NO")
	     private String receiptNo;
		 
		 @JSONField(name = "ACCT_DATE")
	     private String acctDate;

		public String getBankBillNo() {
			return bankBillNo;
		}

		public void setBankBillNo(String bankBillNo) {
			this.bankBillNo = bankBillNo;
		}

		public String getReceiptNo() {
			return receiptNo;
		}

		public void setReceiptNo(String receiptNo) {
			this.receiptNo = receiptNo;
		}

		public String getAcctDate() {
			return acctDate;
		}

		public void setAcctDate(String acctDate) {
			this.acctDate = acctDate;
		}
		 
	}
}
