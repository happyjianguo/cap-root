package com.fxbank.cap.ykwm.dto.esb;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

public class REQ_30012002002 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;

	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;

	@JSONField(name = "BODY")
	private REQ_BODY reqBody;

	public REQ_30012002002() {
		super.txDesc = "热电缴费";
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
	public static class INVOICE {

		@JSONField(name = "INVC_NA_HD_T3")
		private String invcNaHdT3;// 发票抬头

		@JSONField(name = "REIMBURSE_AREA_T")
		private String reimburseAreaT;// 报销面积

		@JSONField(name = "NA_T1")
		private String naT1;// 姓名

		@JSONField(name = "INVOICE_NUM_T")
		private String invoiceNumT;// 纳税人识别号

		@JSONField(name = "USER_ADDR_T")
		private String userAddrT;// 用户地址

		@JSONField(name = "BANK_NUM_T")
		private String BankNumT;// 开户行行号

		public String getInvcNaHdT3() {
			return invcNaHdT3;
		}

		public void setInvcNaHdT3(String invcNaHdT3) {
			this.invcNaHdT3 = invcNaHdT3;
		}

		public String getReimburseAreaT() {
			return reimburseAreaT;
		}

		public void setReimburseAreaT(String reimburseAreaT) {
			this.reimburseAreaT = reimburseAreaT;
		}

		public String getNaT1() {
			return naT1;
		}

		public void setNaT1(String naT1) {
			this.naT1 = naT1;
		}

		public String getInvoiceNumT() {
			return invoiceNumT;
		}

		public void setInvoiceNumT(String invoiceNumT) {
			this.invoiceNumT = invoiceNumT;
		}

		public String getUserAddrT() {
			return userAddrT;
		}

		public void setUserAddrT(String userAddrT) {
			this.userAddrT = userAddrT;
		}

		public String getBankNumT() {
			return BankNumT;
		}

		public void setBankNumT(String bankNumT) {
			BankNumT = bankNumT;
		}

		
	}
	public class REQ_BODY {

		@JSONField(name = "ACCT_NO_T")
		private String acctNoT;// 账号

		@JSONField(name = "PY_FEE_AMT_T")
		private String pyFeeAmtT;//缴费金额 
		
		@JSONField(name = "USER_DBT_AMT_T")
		private String userDbtAmtT;// 用户欠费金额
		
		@JSONField(name = "COURIER_AMT_T")
		private String courierAmtT;// 快递金额

		@JSONField(name = "USER_CARD_NO_T")
		private String userCardNoT;//用户卡号 
		
		@JSONField(name = "CNTT_PHN_T")
		private String cnttPhnT;//联系电话 
		
		@JSONField(name = "LNM_T3")
		private String lnmT3;//联系人 

		@JSONField(name = "PY_FEE_TP_T")
		private String pyFeeTpT;// 缴费方式
		
		@JSONField(name = "REIMBURSE_SIGN_T")
		private String reimburseSignT;// 报销标志
		
		@JSONField(name = "HEAT_COMPANY_ID_T")
		private String heatCompanyIdT;// 供暖公司ID

		@JSONField(name = "MAIL_ADDR_T")
		private String mailAddrT;// 邮寄地址
		
		@JSONField(name = "PWD_T")
		private String pwdT;// 密码
		
		@JSONField(name = "HEAT_COMPANY_NM_T")
		private String heatCompanyNmT;//供暖公司名 

		@JSONField(name = "POST_NO_T5")
		private String postNoT5;//邮编 
		
		@JSONField(name = "COURIER_CMPNY_ID_T")
		private String courierCmpnyIdT;// 快递公司ID
		
		@JSONField(name = "checkNoT")
		private String checkNoT;// 查询流水号

		@JSONField(name = "BILL_GET_TP_T")
		private String billGetTpT;// 发票处理方式
		
		@JSONField(name = "invoiceCountT")
		private String invoiceCountT;// 开票张数
		
		@JSONField(name = "INVOICE_ARRAY")
		private List<INVOICE> invoiceArray;// 发票数组

		public String getAcctNoT() {
			return acctNoT;
		}

		public void setAcctNoT(String acctNoT) {
			this.acctNoT = acctNoT;
		}

		public String getPyFeeAmtT() {
			return pyFeeAmtT;
		}

		public void setPyFeeAmtT(String pyFeeAmtT) {
			this.pyFeeAmtT = pyFeeAmtT;
		}

		public String getUserDbtAmtT() {
			return userDbtAmtT;
		}

		public void setUserDbtAmtT(String userDbtAmtT) {
			this.userDbtAmtT = userDbtAmtT;
		}

		public String getCourierAmtT() {
			return courierAmtT;
		}

		public void setCourierAmtT(String courierAmtT) {
			this.courierAmtT = courierAmtT;
		}

		public String getUserCardNoT() {
			return userCardNoT;
		}

		public void setUserCardNoT(String userCardNoT) {
			this.userCardNoT = userCardNoT;
		}

		public String getCnttPhnT() {
			return cnttPhnT;
		}

		public void setCnttPhnT(String cnttPhnT) {
			this.cnttPhnT = cnttPhnT;
		}

		public String getLnmT3() {
			return lnmT3;
		}

		public void setLnmT3(String lnmT3) {
			this.lnmT3 = lnmT3;
		}

		public String getPyFeeTpT() {
			return pyFeeTpT;
		}

		public void setPyFeeTpT(String pyFeeTpT) {
			this.pyFeeTpT = pyFeeTpT;
		}

		public String getReimburseSignT() {
			return reimburseSignT;
		}

		public void setReimburseSignT(String reimburseSignT) {
			this.reimburseSignT = reimburseSignT;
		}

		public String getHeatCompanyIdT() {
			return heatCompanyIdT;
		}

		public void setHeatCompanyIdT(String heatCompanyIdT) {
			this.heatCompanyIdT = heatCompanyIdT;
		}

		public String getMailAddrT() {
			return mailAddrT;
		}

		public void setMailAddrT(String mailAddrT) {
			this.mailAddrT = mailAddrT;
		}

		public String getPwdT() {
			return pwdT;
		}

		public void setPwdT(String pwdT) {
			this.pwdT = pwdT;
		}

		public String getHeatCompanyNmT() {
			return heatCompanyNmT;
		}

		public void setHeatCompanyNmT(String heatCompanyNmT) {
			this.heatCompanyNmT = heatCompanyNmT;
		}

		public String getPostNoT5() {
			return postNoT5;
		}

		public void setPostNoT5(String postNoT5) {
			this.postNoT5 = postNoT5;
		}

		public String getCourierCmpnyIdT() {
			return courierCmpnyIdT;
		}

		public void setCourierCmpnyIdT(String courierCmpnyIdT) {
			this.courierCmpnyIdT = courierCmpnyIdT;
		}

		public String getCheckNoT() {
			return checkNoT;
		}

		public void setCheckNoT(String checkNoT) {
			this.checkNoT = checkNoT;
		}

		public String getBillGetTpT() {
			return billGetTpT;
		}

		public void setBillGetTpT(String billGetTpT) {
			this.billGetTpT = billGetTpT;
		}

		public String getInvoiceCountT() {
			return invoiceCountT;
		}

		public void setInvoiceCountT(String invoiceCountT) {
			this.invoiceCountT = invoiceCountT;
		}

		public List<INVOICE> getInvoiceArray() {
			return invoiceArray;
		}

		public void setInvoiceArray(List<INVOICE> invoiceArray) {
			this.invoiceArray = invoiceArray;
		}

		
	}
}
