package com.fxbank.cap.manager.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * YKWM_TRACELOG
 */
@Table(name = "YKWM_TRACELOG")
@Alias("ykwmTracelog")
public class YkwmTracelog extends BaseData{
    /**
	 * null
	 */
	@Id
	@Column(name = "CAP_TRANSACTIONNO")
	private String capTransactionno;

	/**
	 * null
	 */
	@Id
	@Column(name = "CAP_DATE")
	private String capDate;

	/**
	 * null
	 */
	@Column(name = "PY_TRANSACTIONNO")
	private String pyTransactionno;

	/**
	 * null
	 */
	@Column(name = "CO_TRANSACTIONNO")
	private String coTransactionno;

	/**
	 * null
	 */
	@Column(name = "PY_RESULT")
	private String pyResult;

	/**
	 * null
	 */
	@Column(name = "CO_RESULT")
	private String coResult;

	/**
	 * null
	 */
	@Column(name = "CAP_RESULT")
	private String capResult;

	/**
	 * null
	 */
	@Column(name = "CAP_REPMSG")
	private String capRepmsg;

	/**
	 * null
	 */
	@Column(name = "PY_DATE")
	private String pyDate;

	/**
	 * null
	 */
	@Column(name = "CO_DATE")
	private String coDate;

	/**
	 * null
	 */
	@Column(name = "TE_BRANCHNO")
	private String teBranchno;

	/**
	 * null
	 */
	@Column(name = "TE_NAME")
	private String teName;

	/**
	 * null
	 */
	@Column(name = "TE_VIRTUAL_NAME")
	private String teVirtualName;

	/**
	 * null
	 */
	@Column(name = "TE_CHECK_NUM")
	private String teCheckNum;

	/**
	 * null
	 */
	@Column(name = "ACCT_NO_T")
	private String acctNoT;

	/**
	 * null
	 */
	@Column(name = "PY_FEE_AMT_T")
	private String pyFeeAmtT;

	/**
	 * null
	 */
	@Column(name = "USER_DBT_AMT_T")
	private String userDbtAmtT;

	/**
	 * null
	 */
	@Column(name = "COURIER_AMT_T")
	private String courierAmtT;

	/**
	 * null
	 */
	@Column(name = "REIMBURSE_SIGN_T")
	private String reimburseSignT;

	/**
	 * null
	 */
	@Column(name = "HEAT_SEQ_NO_T")
	private String heatSeqNoT;

	/**
	 * null
	 */
	@Column(name = "USER_CARD_NO_T")
	private String userCardNoT;

	/**
	 * null
	 */
	@Column(name = "CNTT_PHN_T")
	private String cnttPhnT;

	/**
	 * null
	 */
	@Column(name = "LNM_T3")
	private String lnmT3;

	/**
	 * null
	 */
	@Column(name = "PY_FEE_TP_T")
	private String pyFeeTpT;

	/**
	 * null
	 */
	@Column(name = "BILL_GET_TP_T")
	private String billGetTpT;

	/**
	 * null
	 */
	@Column(name = "COMPANY_T")
	private String companyT;

	/**
	 * null
	 */
	@Column(name = "HEAT_COMPANY_ID_T")
	private String heatCompanyIdT;

	/**
	 * null
	 */
	@Column(name = "USER_ADDR_T")
	private String userAddrT;

	/**
	 * null
	 */
	@Column(name = "MAIL_ADDR_T")
	private String mailAddrT;

	/**
	 * null
	 */
	@Column(name = "PWD_T")
	private String pwdT;

	/**
	 * null
	 */
	@Column(name = "INVC_NA_HD_T3")
	private String invcNaHdT3;

	/**
	 * null
	 */
	@Column(name = "HEAT_COMPANY_NM_T")
	private String heatCompanyNmT;

	/**
	 * null
	 */
	@Column(name = "NA_T1")
	private String naT1;

	/**
	 * null
	 */
	@Column(name = "POST_NO_T5")
	private String postNoT5;

	/**
	 * null
	 */
	@Column(name = "COURIER_CMPNY_ID_T")
	private String courierCmpnyIdT;

	/**
	 * null
	 */
	@Column(name = "PY_SOURCE")
	private String pySource;

	/**
	 * null
	 */
	@Column(name = "PY_BRANCHNUM")
	private String pyBranchnum;

	/**
	 * null
	 */
	@Column(name = "PY_BATCHNUM")
	private String pyBatchnum;

	/**
	 * null
	 */
	@Column(name = "EX_ID")
	private String exId;

	/**
	 * null
	 */
	@Column(name = "EX_NAME")
	private String exName;

	/**
	 * null
	 */
	@Column(name = "EX_AMT")
	private BigDecimal exAmt;

	/**
	 * null
	 */
	@Column(name = "DT_CHARGEYEAR")
	private String dtChargeyear;

	/**
	 * null
	 */
	@Column(name = "DT_ITEMNAME")
	private String dtItemname;

	/**
	 * null
	 */
	@Column(name = "DT_AREA")
	private Long dtArea;

	/**
	 * null
	 */
	@Column(name = "DT_PRICE")
	private Long dtPrice;

	/**
	 * null
	 */
	@Column(name = "DT_ACCOUNT")
	private Long dtAccount;

	/**
	 * null
	 */
	@Column(name = "DT_AGIO")
	private Long dtAgio;

	/**
	 * null
	 */
	@Column(name = "DT_LATEFEE")
	private Long dtLatefee;

	/**
	 * null
	 */
	@Column(name = "DT_PAYMENT")
	private Long dtPayment;

	/**
	 * null
	 */
	@Column(name = "INVOICETITLE1")
	private String invoicetitle1;

	/**
	 * null
	 */
	@Column(name = "AREA1")
	private Long area1;

	/**
	 * null
	 */
	@Column(name = "INVOICENAME1")
	private String invoicename1;

	/**
	 * null
	 */
	@Column(name = "INVOICENUM1")
	private String invoicenum1;

	/**
	 * null
	 */
	@Column(name = "BANKNUM1")
	private String banknum1;

	/**
	 * null
	 */
	@Column(name = "INVOICEADDRESS1")
	private String invoiceaddress1;

	/**
	 * null
	 */
	@Column(name = "INVOICETITLE2")
	private String invoicetitle2;

	/**
	 * null
	 */
	@Column(name = "AREA2")
	private Long area2;

	/**
	 * null
	 */
	@Column(name = "INVOICENAME2")
	private String invoicename2;

	/**
	 * null
	 */
	@Column(name = "INVOICENUM2")
	private String invoicenum2;

	/**
	 * null
	 */
	@Column(name = "BANKNUM2")
	private String banknum2;

	/**
	 * null
	 */
	@Column(name = "INVOICEADDRESS2")
	private String invoiceaddress2;

	/**
	 * null
	 */
	@Column(name = "INVOICETITLE3")
	private String invoicetitle3;

	/**
	 * null
	 */
	@Column(name = "AREA3")
	private Long area3;

	/**
	 * null
	 */
	@Column(name = "INVOICENAME3")
	private String invoicename3;

	/**
	 * null
	 */
	@Column(name = "INVOICENUM3")
	private String invoicenum3;

	/**
	 * null
	 */
	@Column(name = "BANKNUM3")
	private String banknum3;

	/**
	 * null
	 */
	@Column(name = "INVOICEADDRESS3")
	private String invoiceaddress3;

	/**
	 * null
	 */
	@Column(name = "INVOICETITLE4")
	private String invoicetitle4;

	/**
	 * null
	 */
	@Column(name = "AREA4")
	private Long area4;

	/**
	 * null
	 */
	@Column(name = "INVOICENAME4")
	private String invoicename4;

	/**
	 * null
	 */
	@Column(name = "INVOICENUM4")
	private String invoicenum4;

	/**
	 * null
	 */
	@Column(name = "BANKNUM4")
	private String banknum4;

	/**
	 * null
	 */
	@Column(name = "INVOICEADDRESS4")
	private String invoiceaddress4;

	/**
	 * null
	 */
	@Column(name = "TICKET_NUMBER")
	private String ticketNumber;

	/**
	 * null
	 */
	@Column(name = "PY_RSPCODE")
	private String pyRspcode;

	/**
	 * null
	 */
	@Column(name = "PY_ERROR_MSG")
	private String pyErrorMsg;

	/**
	 * null
	 */
	@Column(name = "CO_RSPCODE")
	private String coRspcode;

	/**
	 * null
	 */
	@Column(name = "CO_RSPMSG")
	private String coRspmsg;

	/**
	 * null
	 * @return  CAP_TRANSACTIONNO null
	 */
	public String getCapTransactionno() {
		return capTransactionno;
	}

	/**
	 * null
	 * @param capTransactionno  null
	 */
	public void setCapTransactionno(String capTransactionno) {
		this.capTransactionno = capTransactionno;
	}

	/**
	 * null
	 * @return  CAP_DATE null
	 */
	public String getCapDate() {
		return capDate;
	}

	/**
	 * null
	 * @param capDate  null
	 */
	public void setCapDate(String capDate) {
		this.capDate = capDate;
	}

	/**
	 * null
	 * @return  PY_TRANSACTIONNO null
	 */
	public String getPyTransactionno() {
		return pyTransactionno;
	}

	/**
	 * null
	 * @param pyTransactionno  null
	 */
	public void setPyTransactionno(String pyTransactionno) {
		this.pyTransactionno = pyTransactionno;
	}

	/**
	 * null
	 * @return  CO_TRANSACTIONNO null
	 */
	public String getCoTransactionno() {
		return coTransactionno;
	}

	/**
	 * null
	 * @param coTransactionno  null
	 */
	public void setCoTransactionno(String coTransactionno) {
		this.coTransactionno = coTransactionno;
	}

	/**
	 * null
	 * @return  PY_RESULT null
	 */
	public String getPyResult() {
		return pyResult;
	}

	/**
	 * null
	 * @param pyResult  null
	 */
	public void setPyResult(String pyResult) {
		this.pyResult = pyResult;
	}

	/**
	 * null
	 * @return  CO_RESULT null
	 */
	public String getCoResult() {
		return coResult;
	}

	/**
	 * null
	 * @param coResult  null
	 */
	public void setCoResult(String coResult) {
		this.coResult = coResult;
	}

	/**
	 * null
	 * @return  CAP_RESULT null
	 */
	public String getCapResult() {
		return capResult;
	}

	/**
	 * null
	 * @param capResult  null
	 */
	public void setCapResult(String capResult) {
		this.capResult = capResult;
	}

	/**
	 * null
	 * @return  CAP_REPMSG null
	 */
	public String getCapRepmsg() {
		return capRepmsg;
	}

	/**
	 * null
	 * @param capRepmsg  null
	 */
	public void setCapRepmsg(String capRepmsg) {
		this.capRepmsg = capRepmsg;
	}

	/**
	 * null
	 * @return  PY_DATE null
	 */
	public String getPyDate() {
		return pyDate;
	}

	/**
	 * null
	 * @param pyDate  null
	 */
	public void setPyDate(String pyDate) {
		this.pyDate = pyDate;
	}

	/**
	 * null
	 * @return  CO_DATE null
	 */
	public String getCoDate() {
		return coDate;
	}

	/**
	 * null
	 * @param coDate  null
	 */
	public void setCoDate(String coDate) {
		this.coDate = coDate;
	}

	/**
	 * null
	 * @return  TE_BRANCHNO null
	 */
	public String getTeBranchno() {
		return teBranchno;
	}

	/**
	 * null
	 * @param teBranchno  null
	 */
	public void setTeBranchno(String teBranchno) {
		this.teBranchno = teBranchno;
	}

	/**
	 * null
	 * @return  TE_NAME null
	 */
	public String getTeName() {
		return teName;
	}

	/**
	 * null
	 * @param teName  null
	 */
	public void setTeName(String teName) {
		this.teName = teName;
	}

	/**
	 * null
	 * @return  TE_VIRTUAL_NAME null
	 */
	public String getTeVirtualName() {
		return teVirtualName;
	}

	/**
	 * null
	 * @param teVirtualName  null
	 */
	public void setTeVirtualName(String teVirtualName) {
		this.teVirtualName = teVirtualName;
	}

	/**
	 * null
	 * @return  TE_CHECK_NUM null
	 */
	public String getTeCheckNum() {
		return teCheckNum;
	}

	/**
	 * null
	 * @param teCheckNum  null
	 */
	public void setTeCheckNum(String teCheckNum) {
		this.teCheckNum = teCheckNum;
	}

	/**
	 * null
	 * @return  ACCT_NO_T null
	 */
	public String getAcctNoT() {
		return acctNoT;
	}

	/**
	 * null
	 * @param acctNoT  null
	 */
	public void setAcctNoT(String acctNoT) {
		this.acctNoT = acctNoT;
	}

	/**
	 * null
	 * @return  PY_FEE_AMT_T null
	 */
	public String getPyFeeAmtT() {
		return pyFeeAmtT;
	}

	/**
	 * null
	 * @param pyFeeAmtT  null
	 */
	public void setPyFeeAmtT(String pyFeeAmtT) {
		this.pyFeeAmtT = pyFeeAmtT;
	}

	/**
	 * null
	 * @return  USER_DBT_AMT_T null
	 */
	public String getUserDbtAmtT() {
		return userDbtAmtT;
	}

	/**
	 * null
	 * @param userDbtAmtT  null
	 */
	public void setUserDbtAmtT(String userDbtAmtT) {
		this.userDbtAmtT = userDbtAmtT;
	}

	/**
	 * null
	 * @return  COURIER_AMT_T null
	 */
	public String getCourierAmtT() {
		return courierAmtT;
	}

	/**
	 * null
	 * @param courierAmtT  null
	 */
	public void setCourierAmtT(String courierAmtT) {
		this.courierAmtT = courierAmtT;
	}

	/**
	 * null
	 * @return  REIMBURSE_SIGN_T null
	 */
	public String getReimburseSignT() {
		return reimburseSignT;
	}

	/**
	 * null
	 * @param reimburseSignT  null
	 */
	public void setReimburseSignT(String reimburseSignT) {
		this.reimburseSignT = reimburseSignT;
	}

	/**
	 * null
	 * @return  HEAT_SEQ_NO_T null
	 */
	public String getHeatSeqNoT() {
		return heatSeqNoT;
	}

	/**
	 * null
	 * @param heatSeqNoT  null
	 */
	public void setHeatSeqNoT(String heatSeqNoT) {
		this.heatSeqNoT = heatSeqNoT;
	}

	/**
	 * null
	 * @return  USER_CARD_NO_T null
	 */
	public String getUserCardNoT() {
		return userCardNoT;
	}

	/**
	 * null
	 * @param userCardNoT  null
	 */
	public void setUserCardNoT(String userCardNoT) {
		this.userCardNoT = userCardNoT;
	}

	/**
	 * null
	 * @return  CNTT_PHN_T null
	 */
	public String getCnttPhnT() {
		return cnttPhnT;
	}

	/**
	 * null
	 * @param cnttPhnT  null
	 */
	public void setCnttPhnT(String cnttPhnT) {
		this.cnttPhnT = cnttPhnT;
	}

	/**
	 * null
	 * @return  LNM_T3 null
	 */
	public String getLnmT3() {
		return lnmT3;
	}

	/**
	 * null
	 * @param lnmT3  null
	 */
	public void setLnmT3(String lnmT3) {
		this.lnmT3 = lnmT3;
	}

	/**
	 * null
	 * @return  PY_FEE_TP_T null
	 */
	public String getPyFeeTpT() {
		return pyFeeTpT;
	}

	/**
	 * null
	 * @param pyFeeTpT  null
	 */
	public void setPyFeeTpT(String pyFeeTpT) {
		this.pyFeeTpT = pyFeeTpT;
	}

	/**
	 * null
	 * @return  BILL_GET_TP_T null
	 */
	public String getBillGetTpT() {
		return billGetTpT;
	}

	/**
	 * null
	 * @param billGetTpT  null
	 */
	public void setBillGetTpT(String billGetTpT) {
		this.billGetTpT = billGetTpT;
	}

	/**
	 * null
	 * @return  COMPANY_T null
	 */
	public String getCompanyT() {
		return companyT;
	}

	/**
	 * null
	 * @param companyT  null
	 */
	public void setCompanyT(String companyT) {
		this.companyT = companyT;
	}

	/**
	 * null
	 * @return  HEAT_COMPANY_ID_T null
	 */
	public String getHeatCompanyIdT() {
		return heatCompanyIdT;
	}

	/**
	 * null
	 * @param heatCompanyIdT  null
	 */
	public void setHeatCompanyIdT(String heatCompanyIdT) {
		this.heatCompanyIdT = heatCompanyIdT;
	}

	/**
	 * null
	 * @return  USER_ADDR_T null
	 */
	public String getUserAddrT() {
		return userAddrT;
	}

	/**
	 * null
	 * @param userAddrT  null
	 */
	public void setUserAddrT(String userAddrT) {
		this.userAddrT = userAddrT;
	}

	/**
	 * null
	 * @return  MAIL_ADDR_T null
	 */
	public String getMailAddrT() {
		return mailAddrT;
	}

	/**
	 * null
	 * @param mailAddrT  null
	 */
	public void setMailAddrT(String mailAddrT) {
		this.mailAddrT = mailAddrT;
	}

	/**
	 * null
	 * @return  PWD_T null
	 */
	public String getPwdT() {
		return pwdT;
	}

	/**
	 * null
	 * @param pwdT  null
	 */
	public void setPwdT(String pwdT) {
		this.pwdT = pwdT;
	}

	/**
	 * null
	 * @return  INVC_NA_HD_T3 null
	 */
	public String getInvcNaHdT3() {
		return invcNaHdT3;
	}

	/**
	 * null
	 * @param invcNaHdT3  null
	 */
	public void setInvcNaHdT3(String invcNaHdT3) {
		this.invcNaHdT3 = invcNaHdT3;
	}

	/**
	 * null
	 * @return  HEAT_COMPANY_NM_T null
	 */
	public String getHeatCompanyNmT() {
		return heatCompanyNmT;
	}

	/**
	 * null
	 * @param heatCompanyNmT  null
	 */
	public void setHeatCompanyNmT(String heatCompanyNmT) {
		this.heatCompanyNmT = heatCompanyNmT;
	}

	/**
	 * null
	 * @return  NA_T1 null
	 */
	public String getNaT1() {
		return naT1;
	}

	/**
	 * null
	 * @param naT1  null
	 */
	public void setNaT1(String naT1) {
		this.naT1 = naT1;
	}

	/**
	 * null
	 * @return  POST_NO_T5 null
	 */
	public String getPostNoT5() {
		return postNoT5;
	}

	/**
	 * null
	 * @param postNoT5  null
	 */
	public void setPostNoT5(String postNoT5) {
		this.postNoT5 = postNoT5;
	}

	/**
	 * null
	 * @return  COURIER_CMPNY_ID_T null
	 */
	public String getCourierCmpnyIdT() {
		return courierCmpnyIdT;
	}

	/**
	 * null
	 * @param courierCmpnyIdT  null
	 */
	public void setCourierCmpnyIdT(String courierCmpnyIdT) {
		this.courierCmpnyIdT = courierCmpnyIdT;
	}

	/**
	 * null
	 * @return  PY_SOURCE null
	 */
	public String getPySource() {
		return pySource;
	}

	/**
	 * null
	 * @param pySource  null
	 */
	public void setPySource(String pySource) {
		this.pySource = pySource;
	}

	/**
	 * null
	 * @return  PY_BRANCHNUM null
	 */
	public String getPyBranchnum() {
		return pyBranchnum;
	}

	/**
	 * null
	 * @param pyBranchnum  null
	 */
	public void setPyBranchnum(String pyBranchnum) {
		this.pyBranchnum = pyBranchnum;
	}

	/**
	 * null
	 * @return  PY_BATCHNUM null
	 */
	public String getPyBatchnum() {
		return pyBatchnum;
	}

	/**
	 * null
	 * @param pyBatchnum  null
	 */
	public void setPyBatchnum(String pyBatchnum) {
		this.pyBatchnum = pyBatchnum;
	}

	/**
	 * null
	 * @return  EX_ID null
	 */
	public String getExId() {
		return exId;
	}

	/**
	 * null
	 * @param exId  null
	 */
	public void setExId(String exId) {
		this.exId = exId;
	}

	/**
	 * null
	 * @return  EX_NAME null
	 */
	public String getExName() {
		return exName;
	}

	/**
	 * null
	 * @param exName  null
	 */
	public void setExName(String exName) {
		this.exName = exName;
	}

	/**
	 * null
	 * @return  EX_AMT null
	 */
	public BigDecimal getExAmt() {
		return exAmt;
	}

	/**
	 * null
	 * @param exAmt  null
	 */
	public void setExAmt(BigDecimal exAmt) {
		this.exAmt = exAmt;
	}

	/**
	 * null
	 * @return  DT_CHARGEYEAR null
	 */
	public String getDtChargeyear() {
		return dtChargeyear;
	}

	/**
	 * null
	 * @param dtChargeyear  null
	 */
	public void setDtChargeyear(String dtChargeyear) {
		this.dtChargeyear = dtChargeyear;
	}

	/**
	 * null
	 * @return  DT_ITEMNAME null
	 */
	public String getDtItemname() {
		return dtItemname;
	}

	/**
	 * null
	 * @param dtItemname  null
	 */
	public void setDtItemname(String dtItemname) {
		this.dtItemname = dtItemname;
	}

	/**
	 * null
	 * @return  DT_AREA null
	 */
	public Long getDtArea() {
		return dtArea;
	}

	/**
	 * null
	 * @param dtArea  null
	 */
	public void setDtArea(Long dtArea) {
		this.dtArea = dtArea;
	}

	/**
	 * null
	 * @return  DT_PRICE null
	 */
	public Long getDtPrice() {
		return dtPrice;
	}

	/**
	 * null
	 * @param dtPrice  null
	 */
	public void setDtPrice(Long dtPrice) {
		this.dtPrice = dtPrice;
	}

	/**
	 * null
	 * @return  DT_ACCOUNT null
	 */
	public Long getDtAccount() {
		return dtAccount;
	}

	/**
	 * null
	 * @param dtAccount  null
	 */
	public void setDtAccount(Long dtAccount) {
		this.dtAccount = dtAccount;
	}

	/**
	 * null
	 * @return  DT_AGIO null
	 */
	public Long getDtAgio() {
		return dtAgio;
	}

	/**
	 * null
	 * @param dtAgio  null
	 */
	public void setDtAgio(Long dtAgio) {
		this.dtAgio = dtAgio;
	}

	/**
	 * null
	 * @return  DT_LATEFEE null
	 */
	public Long getDtLatefee() {
		return dtLatefee;
	}

	/**
	 * null
	 * @param dtLatefee  null
	 */
	public void setDtLatefee(Long dtLatefee) {
		this.dtLatefee = dtLatefee;
	}

	/**
	 * null
	 * @return  DT_PAYMENT null
	 */
	public Long getDtPayment() {
		return dtPayment;
	}

	/**
	 * null
	 * @param dtPayment  null
	 */
	public void setDtPayment(Long dtPayment) {
		this.dtPayment = dtPayment;
	}

	/**
	 * null
	 * @return  INVOICETITLE1 null
	 */
	public String getInvoicetitle1() {
		return invoicetitle1;
	}

	/**
	 * null
	 * @param invoicetitle1  null
	 */
	public void setInvoicetitle1(String invoicetitle1) {
		this.invoicetitle1 = invoicetitle1;
	}

	/**
	 * null
	 * @return  AREA1 null
	 */
	public Long getArea1() {
		return area1;
	}

	/**
	 * null
	 * @param area1  null
	 */
	public void setArea1(Long area1) {
		this.area1 = area1;
	}

	/**
	 * null
	 * @return  INVOICENAME1 null
	 */
	public String getInvoicename1() {
		return invoicename1;
	}

	/**
	 * null
	 * @param invoicename1  null
	 */
	public void setInvoicename1(String invoicename1) {
		this.invoicename1 = invoicename1;
	}

	/**
	 * null
	 * @return  INVOICENUM1 null
	 */
	public String getInvoicenum1() {
		return invoicenum1;
	}

	/**
	 * null
	 * @param invoicenum1  null
	 */
	public void setInvoicenum1(String invoicenum1) {
		this.invoicenum1 = invoicenum1;
	}

	/**
	 * null
	 * @return  BANKNUM1 null
	 */
	public String getBanknum1() {
		return banknum1;
	}

	/**
	 * null
	 * @param banknum1  null
	 */
	public void setBanknum1(String banknum1) {
		this.banknum1 = banknum1;
	}

	/**
	 * null
	 * @return  INVOICEADDRESS1 null
	 */
	public String getInvoiceaddress1() {
		return invoiceaddress1;
	}

	/**
	 * null
	 * @param invoiceaddress1  null
	 */
	public void setInvoiceaddress1(String invoiceaddress1) {
		this.invoiceaddress1 = invoiceaddress1;
	}

	/**
	 * null
	 * @return  INVOICETITLE2 null
	 */
	public String getInvoicetitle2() {
		return invoicetitle2;
	}

	/**
	 * null
	 * @param invoicetitle2  null
	 */
	public void setInvoicetitle2(String invoicetitle2) {
		this.invoicetitle2 = invoicetitle2;
	}

	/**
	 * null
	 * @return  AREA2 null
	 */
	public Long getArea2() {
		return area2;
	}

	/**
	 * null
	 * @param area2  null
	 */
	public void setArea2(Long area2) {
		this.area2 = area2;
	}

	/**
	 * null
	 * @return  INVOICENAME2 null
	 */
	public String getInvoicename2() {
		return invoicename2;
	}

	/**
	 * null
	 * @param invoicename2  null
	 */
	public void setInvoicename2(String invoicename2) {
		this.invoicename2 = invoicename2;
	}

	/**
	 * null
	 * @return  INVOICENUM2 null
	 */
	public String getInvoicenum2() {
		return invoicenum2;
	}

	/**
	 * null
	 * @param invoicenum2  null
	 */
	public void setInvoicenum2(String invoicenum2) {
		this.invoicenum2 = invoicenum2;
	}

	/**
	 * null
	 * @return  BANKNUM2 null
	 */
	public String getBanknum2() {
		return banknum2;
	}

	/**
	 * null
	 * @param banknum2  null
	 */
	public void setBanknum2(String banknum2) {
		this.banknum2 = banknum2;
	}

	/**
	 * null
	 * @return  INVOICEADDRESS2 null
	 */
	public String getInvoiceaddress2() {
		return invoiceaddress2;
	}

	/**
	 * null
	 * @param invoiceaddress2  null
	 */
	public void setInvoiceaddress2(String invoiceaddress2) {
		this.invoiceaddress2 = invoiceaddress2;
	}

	/**
	 * null
	 * @return  INVOICETITLE3 null
	 */
	public String getInvoicetitle3() {
		return invoicetitle3;
	}

	/**
	 * null
	 * @param invoicetitle3  null
	 */
	public void setInvoicetitle3(String invoicetitle3) {
		this.invoicetitle3 = invoicetitle3;
	}

	/**
	 * null
	 * @return  AREA3 null
	 */
	public Long getArea3() {
		return area3;
	}

	/**
	 * null
	 * @param area3  null
	 */
	public void setArea3(Long area3) {
		this.area3 = area3;
	}

	/**
	 * null
	 * @return  INVOICENAME3 null
	 */
	public String getInvoicename3() {
		return invoicename3;
	}

	/**
	 * null
	 * @param invoicename3  null
	 */
	public void setInvoicename3(String invoicename3) {
		this.invoicename3 = invoicename3;
	}

	/**
	 * null
	 * @return  INVOICENUM3 null
	 */
	public String getInvoicenum3() {
		return invoicenum3;
	}

	/**
	 * null
	 * @param invoicenum3  null
	 */
	public void setInvoicenum3(String invoicenum3) {
		this.invoicenum3 = invoicenum3;
	}

	/**
	 * null
	 * @return  BANKNUM3 null
	 */
	public String getBanknum3() {
		return banknum3;
	}

	/**
	 * null
	 * @param banknum3  null
	 */
	public void setBanknum3(String banknum3) {
		this.banknum3 = banknum3;
	}

	/**
	 * null
	 * @return  INVOICEADDRESS3 null
	 */
	public String getInvoiceaddress3() {
		return invoiceaddress3;
	}

	/**
	 * null
	 * @param invoiceaddress3  null
	 */
	public void setInvoiceaddress3(String invoiceaddress3) {
		this.invoiceaddress3 = invoiceaddress3;
	}

	/**
	 * null
	 * @return  INVOICETITLE4 null
	 */
	public String getInvoicetitle4() {
		return invoicetitle4;
	}

	/**
	 * null
	 * @param invoicetitle4  null
	 */
	public void setInvoicetitle4(String invoicetitle4) {
		this.invoicetitle4 = invoicetitle4;
	}

	/**
	 * null
	 * @return  AREA4 null
	 */
	public Long getArea4() {
		return area4;
	}

	/**
	 * null
	 * @param area4  null
	 */
	public void setArea4(Long area4) {
		this.area4 = area4;
	}

	/**
	 * null
	 * @return  INVOICENAME4 null
	 */
	public String getInvoicename4() {
		return invoicename4;
	}

	/**
	 * null
	 * @param invoicename4  null
	 */
	public void setInvoicename4(String invoicename4) {
		this.invoicename4 = invoicename4;
	}

	/**
	 * null
	 * @return  INVOICENUM4 null
	 */
	public String getInvoicenum4() {
		return invoicenum4;
	}

	/**
	 * null
	 * @param invoicenum4  null
	 */
	public void setInvoicenum4(String invoicenum4) {
		this.invoicenum4 = invoicenum4;
	}

	/**
	 * null
	 * @return  BANKNUM4 null
	 */
	public String getBanknum4() {
		return banknum4;
	}

	/**
	 * null
	 * @param banknum4  null
	 */
	public void setBanknum4(String banknum4) {
		this.banknum4 = banknum4;
	}

	/**
	 * null
	 * @return  INVOICEADDRESS4 null
	 */
	public String getInvoiceaddress4() {
		return invoiceaddress4;
	}

	/**
	 * null
	 * @param invoiceaddress4  null
	 */
	public void setInvoiceaddress4(String invoiceaddress4) {
		this.invoiceaddress4 = invoiceaddress4;
	}

	/**
	 * null
	 * @return  TICKET_NUMBER null
	 */
	public String getTicketNumber() {
		return ticketNumber;
	}

	/**
	 * null
	 * @param ticketNumber  null
	 */
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	/**
	 * null
	 * @return  PY_RSPCODE null
	 */
	public String getPyRspcode() {
		return pyRspcode;
	}

	/**
	 * null
	 * @param pyRspcode  null
	 */
	public void setPyRspcode(String pyRspcode) {
		this.pyRspcode = pyRspcode;
	}

	/**
	 * null
	 * @return  PY_ERROR_MSG null
	 */
	public String getPyErrorMsg() {
		return pyErrorMsg;
	}

	/**
	 * null
	 * @param pyErrorMsg  null
	 */
	public void setPyErrorMsg(String pyErrorMsg) {
		this.pyErrorMsg = pyErrorMsg;
	}

	/**
	 * null
	 * @return  CO_RSPCODE null
	 */
	public String getCoRspcode() {
		return coRspcode;
	}

	/**
	 * null
	 * @param coRspcode  null
	 */
	public void setCoRspcode(String coRspcode) {
		this.coRspcode = coRspcode;
	}

	/**
	 * null
	 * @return  CO_RSPMSG null
	 */
	public String getCoRspmsg() {
		return coRspmsg;
	}

	/**
	 * null
	 * @param coRspmsg  null
	 */
	public void setCoRspmsg(String coRspmsg) {
		this.coRspmsg = coRspmsg;
	}

}