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
		@JSONField(name = "BILL_GET_TP_T")
		private String billGetTpT;// 发票获取方式

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


		/**
		 * @return the billGetTpT
		 */
		public String getBillGetTpT() {
			return billGetTpT;
		}

		/**
		 * @param billGetTpT the billGetTpT to set
		 */
		public void setBillGetTpT(String billGetTpT) {
			this.billGetTpT = billGetTpT;
		}

		/**
		 * @return the bankNumT
		 */
		public String getBankNumT() {
			return BankNumT;
		}

		/**
		 * @param bankNumT the bankNumT to set
		 */
		public void setBankNumT(String bankNumT) {
			this.BankNumT = bankNumT;
		}

		/**
		 * @return the userAddrT
		 */
		public String getUserAddrT() {
			return userAddrT;
		}

		/**
		 * @param userAddrT the userAddrT to set
		 */
		public void setUserAddrT(String userAddrT) {
			this.userAddrT = userAddrT;
		}

		/**
		 * @return the invoiceNumT
		 */
		public String getInvoiceNumT() {
			return invoiceNumT;
		}

		/**
		 * @param invoiceNumT the invoiceNumT to set
		 */
		public void setInvoiceNumT(String invoiceNumT) {
			this.invoiceNumT = invoiceNumT;
		}

		/**
		 * @return the naT1
		 */
		public String getNaT1() {
			return naT1;
		}

		/**
		 * @param naT1 the naT1 to set
		 */
		public void setNaT1(String naT1) {
			this.naT1 = naT1;
		}

		/**
		 * @return the reimburseAreaT
		 */
		public String getReimburseAreaT() {
			return reimburseAreaT;
		}

		/**
		 * @param reimburseAreaT the reimburseAreaT to set
		 */
		public void setReimburseAreaT(String reimburseAreaT) {
			this.reimburseAreaT = reimburseAreaT;
		}

		/**
		 * @return the invcNaHdT3
		 */
		public String getInvcNaHdT3() {
			return invcNaHdT3;
		}

		/**
		 * @param invcNaHdT3 the invcNaHdT3 to set
		 */
		public void setInvcNaHdT3(String invcNaHdT3) {
			this.invcNaHdT3 = invcNaHdT3;
		}

		
	}
	public class REQ_BODY {

		@JSONField(name = "BATCH_NUM_T")
		private String batchNum;// 批次号

		@JSONField(name = "CHECK_NO_T")
		private String checkNoT;// 查询流水号

		@JSONField(name = "ACCT_NO_T")
		private String acctNoT;// 账号

		@JSONField(name = "PY_FEE_AMT_T")
		private String pyFeeAmtT;// 缴费金额

		@JSONField(name = "USER_DBT_AMT_T")
		private String userDbtAmtT;// 用户欠费金额

		@JSONField(name = "COURIER_AMT_T")
		private String courierAmtT;// 快递金额

		@JSONField(name = "HEAT_SEQ_NO_T")
		private String heatSeqNoT;// 热力流水号

		@JSONField(name = "USER_CARD_NO_T")
		private String userCardNoT;// 用户卡号

		@JSONField(name = "CNTT_PHN_T")
		private String cnttPhnT;// 联系电话

		@JSONField(name = "LNM_T3")
		private String lnmT3;// 联系人

		@JSONField(name = "PY_FEE_TP_T")
		private String pyFeeTpT;// 缴费方式

		@JSONField(name = "REIMBURSE_SIGN_T")
		private String reimburseSignT;// 报销标志

		@JSONField(name = "COMPANY_T")
		private String companyT;// 快递公司

		@JSONField(name = "HEAT_COMPANY_ID_T")
		private String heatCompanyIdT;// 供暖公司ID

		@JSONField(name = "MAIL_ADDR_T")
		private String mailAddrT;// 邮寄地址

		@JSONField(name = "PWD_T")
		private String pwdT;// 密码

		@JSONField(name = "HEAT_COMPANY_NM_T")
		private String heatCompanyNmT;// 供暖公司名

		@JSONField(name = "POST_NO_T5")
		private String postNoT5;// 邮编

		@JSONField(name = "COURIER_CMPNY_ID_T")
		private String courierCmpnyIdT;// 快递公司ID

		@JSONField(name = "HT_COMPANY_PARTY_T")
		private String htCompanyPartyT;// 营口公司分部

		@JSONField(name = "INVOICE_ARRAY")
		private List<INVOICE> invoiceList;// 发票信息
		
		@JSONField(name = "INVOICE_COUNT_T")
		private String invoiceCountT;// 开票张数

		

		public String getInvoiceCountT() {
			return invoiceCountT;
		}

		public void setInvoiceCountT(String invoiceCountT) {
			this.invoiceCountT = invoiceCountT;
		}

		/**
		 * @return the htCompanyPartyT
		 */
		public String getHtCompanyPartyT() {
			return htCompanyPartyT;
		}

		/**
		 * @return the invoiceList
		 */
		public List<INVOICE> getInvoiceList() {
			return invoiceList;
		}

		/**
		 * @param invoiceList the invoiceList to set
		 */
		public void setInvoiceList(List<INVOICE> invoiceList) {
			this.invoiceList = invoiceList;
		}

		/**
		 * @return the batchNum
		 */
		public String getBatchNum() {
			return batchNum;
		}

		/**
		 * @param batchNum the batchNum to set
		 */
		public void setBatchNum(String batchNum) {
			this.batchNum = batchNum;
		}

		/**
		 * @return the checkNoT
		 */
		public String getCheckNoT() {
			return checkNoT;
		}

		/**
		 * @param checkNoT the checkNoT to set
		 */
		public void setCheckNoT(String checkNoT) {
			this.checkNoT = checkNoT;
		}

		/**
		 * @return the acctNoT
		 */
		public String getAcctNoT() {
			return acctNoT;
		}

		/**
		 * @param acctNoT the acctNoT to set
		 */
		public void setAcctNoT(String acctNoT) {
			this.acctNoT = acctNoT;
		}

		/**
		 * @param htCompanyPartyT the htCompanyPartyT to set
		 */
		public void setHtCompanyPartyT(String htCompanyPartyT) {
			this.htCompanyPartyT = htCompanyPartyT;
		}

		/**
		 * @return the courierCmpnyIdT
		 */
		public String getCourierCmpnyIdT() {
			return courierCmpnyIdT;
		}

		/**
		 * @param courierCmpnyIdT the courierCmpnyIdT to set
		 */
		public void setCourierCmpnyIdT(String courierCmpnyIdT) {
			this.courierCmpnyIdT = courierCmpnyIdT;
		}

		/**
		 * @return the postNoT5
		 */
		public String getPostNoT5() {
			return postNoT5;
		}

		/**
		 * @param postNoT5 the postNoT5 to set
		 */
		public void setPostNoT5(String postNoT5) {
			this.postNoT5 = postNoT5;
		}

		/**
		 * @return the heatCompanyNmT
		 */
		public String getHeatCompanyNmT() {
			return heatCompanyNmT;
		}

		/**
		 * @param heatCompanyNmT the heatCompanyNmT to set
		 */
		public void setHeatCompanyNmT(String heatCompanyNmT) {
			this.heatCompanyNmT = heatCompanyNmT;
		}

		/**
		 * @return the pwdT
		 */
		public String getPwdT() {
			return pwdT;
		}

		/**
		 * @param pwdT the pwdT to set
		 */
		public void setPwdT(String pwdT) {
			this.pwdT = pwdT;
		}

		/**
		 * @return the mailAddrT
		 */
		public String getMailAddrT() {
			return mailAddrT;
		}

		/**
		 * @param mailAddrT the mailAddrT to set
		 */
		public void setMailAddrT(String mailAddrT) {
			this.mailAddrT = mailAddrT;
		}

		/**
		 * @return the heatCompanyIdT
		 */
		public String getHeatCompanyIdT() {
			return heatCompanyIdT;
		}

		/**
		 * @param heatCompanyIdT the heatCompanyIdT to set
		 */
		public void setHeatCompanyIdT(String heatCompanyIdT) {
			this.heatCompanyIdT = heatCompanyIdT;
		}

		/**
		 * @return the companyT
		 */
		public String getCompanyT() {
			return companyT;
		}

		/**
		 * @param companyT the companyT to set
		 */
		public void setCompanyT(String companyT) {
			this.companyT = companyT;
		}

		/**
		 * @return the reimburseSignT
		 */
		public String getReimburseSignT() {
			return reimburseSignT;
		}

		/**
		 * @param reimburseSignT the reimburseSignT to set
		 */
		public void setReimburseSignT(String reimburseSignT) {
			this.reimburseSignT = reimburseSignT;
		}

		/**
		 * @return the pyFeeTpT
		 */
		public String getPyFeeTpT() {
			return pyFeeTpT;
		}

		/**
		 * @param pyFeeTpT the pyFeeTpT to set
		 */
		public void setPyFeeTpT(String pyFeeTpT) {
			this.pyFeeTpT = pyFeeTpT;
		}

		/**
		 * @return the lnmT3
		 */
		public String getLnmT3() {
			return lnmT3;
		}

		/**
		 * @param lnmT3 the lnmT3 to set
		 */
		public void setLnmT3(String lnmT3) {
			this.lnmT3 = lnmT3;
		}

		/**
		 * @return the cnttPhnT
		 */
		public String getCnttPhnT() {
			return cnttPhnT;
		}

		/**
		 * @param cnttPhnT the cnttPhnT to set
		 */
		public void setCnttPhnT(String cnttPhnT) {
			this.cnttPhnT = cnttPhnT;
		}

		/**
		 * @return the userCardNoT
		 */
		public String getUserCardNoT() {
			return userCardNoT;
		}

		/**
		 * @param userCardNoT the userCardNoT to set
		 */
		public void setUserCardNoT(String userCardNoT) {
			this.userCardNoT = userCardNoT;
		}

		/**
		 * @return the heatSeqNoT
		 */
		public String getHeatSeqNoT() {
			return heatSeqNoT;
		}

		/**
		 * @param heatSeqNoT the heatSeqNoT to set
		 */
		public void setHeatSeqNoT(String heatSeqNoT) {
			this.heatSeqNoT = heatSeqNoT;
		}

		/**
		 * @return the courierAmtT
		 */
		public String getCourierAmtT() {
			return courierAmtT;
		}

		/**
		 * @param courierAmtT the courierAmtT to set
		 */
		public void setCourierAmtT(String courierAmtT) {
			this.courierAmtT = courierAmtT;
		}

		/**
		 * @return the userDbtAmtT
		 */
		public String getUserDbtAmtT() {
			return userDbtAmtT;
		}

		/**
		 * @param userDbtAmtT the userDbtAmtT to set
		 */
		public void setUserDbtAmtT(String userDbtAmtT) {
			this.userDbtAmtT = userDbtAmtT;
		}

		public String getPyFeeAmtT() {
			return pyFeeAmtT;
		}

		public void setPyFeeAmtT(String pyFeeAmtT) {
			this.pyFeeAmtT = pyFeeAmtT;
		}

	}
}
