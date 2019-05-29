package com.fxbank.cap.ykwm.model;

import java.util.ArrayList;
import java.util.List;

import com.fxbank.cip.base.log.MyLog;

public class REQ_Payment extends REQ_BASE {

	private static final long serialVersionUID = 4990422436703924474L;

	private String source;// 缴费渠道（柜面、手机银行、自助、个人网银、支付宝、微信）
	private String branchNum;// 网点编号 我行的固定值
	private String batchNum;// 批次号，银行方提供的对账批次号 需和查询一致
	private String checkNum;// 交易流水号,此流水号为查询时返回的查询流水号
	private String bankNum;// 开户行行号
	private String core_Result;// 核心交易状态
	private String cap_Result;// 渠道交易状态
	private String payment_Result;// 热电交易状态
	// 柜面的值
	private String acctNoT;// 账号
	private String pyFeeAmtT;// 缴费金额
	private String userDbtAmtT;// 用户欠费金额
	private String courierAmtT;// 快递金额
	private String heatSeqNoT;// 热力流水号
	private String userCardNoT;// 用户卡号
	private String cnttPhnT;// 联系电话
	private String lnmT3;// 联系人
	private String pyFeeTpT;// 缴费方式
	private String reimburseSignT;// 报销标志
	private String companyT;// 快递公司
	private String heatCompanyIdT;// 供暖公司ID
	private String mailAddrT;// 邮寄地址
	private String pwdT;// 密码
	private String heatCompanyNmT;// 供暖公司名
	private String postNoT5;// 邮编
	private String courierCmpnyIdT;// 快递公司ID
	private String ykCompanyPartyT;// 营口公司分部

	private String invoiceCount;// 开票张数

	private List<INVOICE> invoiceList = new ArrayList<INVOICE>();// 发票信息

	@Deprecated
	public REQ_Payment() {
		super(null, 0, 0, 0);
	}

	/**
	 * @return the invoiceCount
	 */
	public String getInvoiceCount() {
		return invoiceCount;
	}

	/**
	 * @param invoiceCount the invoiceCount to set
	 */
	public void setInvoiceCount(String invoiceCount) {
		this.invoiceCount = invoiceCount;
	}

	/**
	 * @return the bankNum
	 */
	public String getBankNum() {
		return bankNum;
	}

	/**
	 * @param bankNum the bankNum to set
	 */
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
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

	// 发票信息
	public static class INVOICE {

		private String billGetTpT;// 发票获取方式 0未选择 1邮寄 2自取 3 电子发票

		private String invcNaHdT3;// 发票抬头

		private String reimburseAreaT;// 报销面积

		private String naT1;// 发票姓名

		private String invoiceNum;// 纳税人识别号 个人无需

		private String bankNum;// 开户行行号

		private String userAddrT;// 用户地址

		/**
		 * @return the userAddrT
		 */
		public String getUserAddrT() {
			return userAddrT;
		}

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
		 * @param userAddrT the userAddrT to set
		 */
		public void setUserAddrT(String userAddrT) {
			this.userAddrT = userAddrT;
		}

		/**
		 * @return the bankNum
		 */
		public String getBankNum() {
			return bankNum;
		}

		/**
		 * @param bankNum the bankNum to set
		 */
		public void setBankNum(String bankNum) {
			this.bankNum = bankNum;
		}

		/**
		 * @return the invoiceNum
		 */
		public String getInvoiceNum() {
			return invoiceNum;
		}

		/**
		 * @param invoiceNum the invoiceNum to set
		 */
		public void setInvoiceNum(String invoiceNum) {
			this.invoiceNum = invoiceNum;
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

	/**
	 * @return the cap_Result
	 */
	public String getCap_Result() {
		return cap_Result;
	}

	/**
	 * @param cap_Result the cap_Result to set
	 */
	public void setCap_Result(String cap_Result) {
		this.cap_Result = cap_Result;
	}

	/**
	 * @return the payment_Result
	 */
	public String getPayment_Result() {
		return payment_Result;
	}

	/**
	 * @param payment_Result the payment_Result to set
	 */
	public void setPayment_Result(String payment_Result) {
		this.payment_Result = payment_Result;
	}

	/**
	 * @return the ykCompanyPartyT
	 */
	public String getYkCompanyPartyT() {
		return ykCompanyPartyT;
	}

	/**
	 * @param ykCompanyPartyT the ykCompanyPartyT to set
	 */
	public void setYkCompanyPartyT(String ykCompanyPartyT) {
		this.ykCompanyPartyT = ykCompanyPartyT;
	}

	/**
	 * @return the core_Result
	 */
	public String getCore_Result() {
		return core_Result;
	}

	/**
	 * @param core_Result the core_Result to set
	 */
	public void setCore_Result(String core_Result) {
		this.core_Result = core_Result;
	}

	/**
	 * @return the result
	 */

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
	 * @return the bnvoiceCount
	 */

	/**
	 * @return the checkNum
	 */
	public String getCheckNum() {
		return checkNum;
	}

	/**
	 * @param checkNum the checkNum to set
	 */
	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}

	/**
	 * @return the branchNum
	 */
	public String getBranchNum() {
		return branchNum;
	}

	/**
	 * @param branchNum the branchNum to set
	 */
	public void setBranchNum(String branchNum) {
		this.branchNum = branchNum;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the htCompanyPartyT
	 */

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

	/**
	 * @return the pyFeeAmtT
	 */
	public String getPyFeeAmtT() {
		return pyFeeAmtT;
	}

	/**
	 * @param pyFeeAmtT the pyFeeAmtT to set
	 */
	public void setPyFeeAmtT(String pyFeeAmtT) {
		this.pyFeeAmtT = pyFeeAmtT;
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

	public REQ_Payment(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

}