/**   
* @Title: YkwmTraceLog.java 
* @Package com.fxbank.cap.ykwm.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @作者 杜振铎
* @date 2019年5月27日 下午7:10:18 
* @version V1.0   
*/
package com.fxbank.cap.ykwm.model;

import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;


/** 
* @ClassName: YkwmTraceLog 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @作者 杜振铎
* @date 2019年5月27日 下午7:10:18 
*  
*/
public class YkwmTraceLogModel extends REQ_BASE {
	private static final long serialVersionUID = -6544573451833955213L;

	@Deprecated
	public YkwmTraceLogModel() {
		super(null, 0, 0, 0);
	}

	public YkwmTraceLogModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.setTtxnNm("Query");
	}
	private String capTransactionno;
    private String pyTransactionno;
    private String coTransactionno;
    private String pyResult;
    private String coResult;
    private String capResult;
    private String capRepmsg;
    private String pyDate;
    private String coDate;
    private String capDate;
    private String teBranchno;
    private String teName;
    private String teVirtualName;
    private String teCheckNum;
    private String acctNoT;
    private String pyFeeAmtT;
    private String userDbtAmtT;
    private String courierAmtT;
    private String reimburseAreaT;
    private String heatSeqNoT;
    private String userCardNoT;
    private String cnttPhnT;
    private String lnmT3;
    private String pyFeeTpT;
    private String billGetTpT;
    private String companyT;
    private String heatCompanyIdT;
    private String userAddrT;
    private String mailAddrT;

    private String pwdT;
    private String invcNaHdT3;

    /**
     * null
     */
    private String heatCompanyNmT;

    /**
     * null
     */
    private String naT1;

    /**
     * null
     */
    private String postNoT5;

    /**
     * null
     */
    private String courierCmpnyIdT;

    /**
     * null
     */
    private String pySource;

    /**
     * null
     */
    private String pyBranchnum;

    /**
     * null
     */
    private String pyBatchnum;

    /**
     * null
     */
    private String exId;

    /**
     * null
     */
    private String exName;

    /**
     * null
     */
    private BigDecimal exAmt;

    /**
     * null
     */
    private String dtChargeyear;

    /**
     * null
     */
    private String dtItemname;

    /**
     * null
     */
    private Long dtArea;

    /**
     * null
     */
    private Long dtPrice;

    /**
     * null
     */
    private Long dtAccount;

    /**
     * null
     */
    private Long dtAgio;

    /**
     * null
     */
    private Long dtLatefee;

    /**
     * null
     */
    private Long dtPayment;

    /**
     * null
     */
    private String invoicetitle1;

    /**
     * null
     */
    private Long area1;

    /**
     * null
     */
    private String invoicename1;

    /**
     * null
     */
    private String invoicenum1;

    /**
     * null
     */
    private String banknum1;

    /**
     * null
     */
    private String invoiceaddress1;

    /**
     * null
     */
    private String invoicetitle2;

    /**
     * null
     */
    private Long area2;

    /**
     * null
     */
    private String invoicename2;

    /**
     * null
     */
    private String invoicenum2;

    /**
     * null
     */
    private String banknum2;

    /**
     * null
     */
    private String invoiceaddress2;

    /**
     * null
     */
    private String invoicetitle3;

    /**
     * null
     */
    private Long area3;

    /**
     * null
     */
    private String invoicename3;

    /**
     * null
     */
    private String invoicenum3;

    /**
     * null
     */
    private String banknum3;

    /**
     * null
     */
    private String invoiceaddress3;

    /**
     * null
     */
    private String invoicetitle4;

    /**
     * null
     */
    private Long area4;

    /**
     * null
     */
    private String invoicename4;

    /**
     * null
     */
    private String invoicenum4;

    /**
     * null
     */
    private String banknum4;

    /**
     * null
     */
    private String invoiceaddress4;

    /**
     * null
     */
    private String ticketNumber;

	public String getCapTransactionno() {
		return capTransactionno;
	}

	public void setCapTransactionno(String capTransactionno) {
		this.capTransactionno = capTransactionno;
	}

	public String getPyTransactionno() {
		return pyTransactionno;
	}

	public void setPyTransactionno(String pyTransactionno) {
		this.pyTransactionno = pyTransactionno;
	}

	public String getCoTransactionno() {
		return coTransactionno;
	}

	public void setCoTransactionno(String coTransactionno) {
		this.coTransactionno = coTransactionno;
	}

	public String getPyResult() {
		return pyResult;
	}

	public void setPyResult(String pyResult) {
		this.pyResult = pyResult;
	}

	public String getCoResult() {
		return coResult;
	}

	public void setCoResult(String coResult) {
		this.coResult = coResult;
	}

	public String getCapResult() {
		return capResult;
	}

	public void setCapResult(String capResult) {
		this.capResult = capResult;
	}

	public String getCapRepmsg() {
		return capRepmsg;
	}

	public void setCapRepmsg(String capRepmsg) {
		this.capRepmsg = capRepmsg;
	}

	public String getPyDate() {
		return pyDate;
	}

	public void setPyDate(String pyDate) {
		this.pyDate = pyDate;
	}

	public String getCoDate() {
		return coDate;
	}

	public void setCoDate(String coDate) {
		this.coDate = coDate;
	}

	public String getCapDate() {
		return capDate;
	}

	public void setCapDate(String capDate) {
		this.capDate = capDate;
	}

	public String getTeBranchno() {
		return teBranchno;
	}

	public void setTeBranchno(String teBranchno) {
		this.teBranchno = teBranchno;
	}

	public String getTeName() {
		return teName;
	}

	public void setTeName(String teName) {
		this.teName = teName;
	}

	public String getTeVirtualName() {
		return teVirtualName;
	}

	public void setTeVirtualName(String teVirtualName) {
		this.teVirtualName = teVirtualName;
	}

	public String getTeCheckNum() {
		return teCheckNum;
	}

	public void setTeCheckNum(String teCheckNum) {
		this.teCheckNum = teCheckNum;
	}

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

	public String getReimburseAreaT() {
		return reimburseAreaT;
	}

	public void setReimburseAreaT(String reimburseAreaT) {
		this.reimburseAreaT = reimburseAreaT;
	}

	public String getHeatSeqNoT() {
		return heatSeqNoT;
	}

	public void setHeatSeqNoT(String heatSeqNoT) {
		this.heatSeqNoT = heatSeqNoT;
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

	public String getBillGetTpT() {
		return billGetTpT;
	}

	public void setBillGetTpT(String billGetTpT) {
		this.billGetTpT = billGetTpT;
	}

	public String getCompanyT() {
		return companyT;
	}

	public void setCompanyT(String companyT) {
		this.companyT = companyT;
	}

	public String getHeatCompanyIdT() {
		return heatCompanyIdT;
	}

	public void setHeatCompanyIdT(String heatCompanyIdT) {
		this.heatCompanyIdT = heatCompanyIdT;
	}

	public String getUserAddrT() {
		return userAddrT;
	}

	public void setUserAddrT(String userAddrT) {
		this.userAddrT = userAddrT;
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

	public String getInvcNaHdT3() {
		return invcNaHdT3;
	}

	public void setInvcNaHdT3(String invcNaHdT3) {
		this.invcNaHdT3 = invcNaHdT3;
	}

	public String getHeatCompanyNmT() {
		return heatCompanyNmT;
	}

	public void setHeatCompanyNmT(String heatCompanyNmT) {
		this.heatCompanyNmT = heatCompanyNmT;
	}

	public String getNaT1() {
		return naT1;
	}

	public void setNaT1(String naT1) {
		this.naT1 = naT1;
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

	public String getPySource() {
		return pySource;
	}

	public void setPySource(String pySource) {
		this.pySource = pySource;
	}

	public String getPyBranchnum() {
		return pyBranchnum;
	}

	public void setPyBranchnum(String pyBranchnum) {
		this.pyBranchnum = pyBranchnum;
	}

	public String getPyBatchnum() {
		return pyBatchnum;
	}

	public void setPyBatchnum(String pyBatchnum) {
		this.pyBatchnum = pyBatchnum;
	}

	public String getExId() {
		return exId;
	}

	public void setExId(String exId) {
		this.exId = exId;
	}

	public String getExName() {
		return exName;
	}

	public void setExName(String exName) {
		this.exName = exName;
	}

	public BigDecimal getExAmt() {
		return exAmt;
	}

	public void setExAmt(BigDecimal exAmt) {
		this.exAmt = exAmt;
	}

	public String getDtChargeyear() {
		return dtChargeyear;
	}

	public void setDtChargeyear(String dtChargeyear) {
		this.dtChargeyear = dtChargeyear;
	}

	public String getDtItemname() {
		return dtItemname;
	}

	public void setDtItemname(String dtItemname) {
		this.dtItemname = dtItemname;
	}

	public Long getDtArea() {
		return dtArea;
	}

	public void setDtArea(Long dtArea) {
		this.dtArea = dtArea;
	}

	public Long getDtPrice() {
		return dtPrice;
	}

	public void setDtPrice(Long dtPrice) {
		this.dtPrice = dtPrice;
	}

	public Long getDtAccount() {
		return dtAccount;
	}

	public void setDtAccount(Long dtAccount) {
		this.dtAccount = dtAccount;
	}

	public Long getDtAgio() {
		return dtAgio;
	}

	public void setDtAgio(Long dtAgio) {
		this.dtAgio = dtAgio;
	}

	public Long getDtLatefee() {
		return dtLatefee;
	}

	public void setDtLatefee(Long dtLatefee) {
		this.dtLatefee = dtLatefee;
	}

	public Long getDtPayment() {
		return dtPayment;
	}

	public void setDtPayment(Long dtPayment) {
		this.dtPayment = dtPayment;
	}

	public String getInvoicetitle1() {
		return invoicetitle1;
	}

	public void setInvoicetitle1(String invoicetitle1) {
		this.invoicetitle1 = invoicetitle1;
	}

	public Long getArea1() {
		return area1;
	}

	public void setArea1(Long area1) {
		this.area1 = area1;
	}

	public String getInvoicename1() {
		return invoicename1;
	}

	public void setInvoicename1(String invoicename1) {
		this.invoicename1 = invoicename1;
	}

	public String getInvoicenum1() {
		return invoicenum1;
	}

	public void setInvoicenum1(String invoicenum1) {
		this.invoicenum1 = invoicenum1;
	}

	public String getBanknum1() {
		return banknum1;
	}

	public void setBanknum1(String banknum1) {
		this.banknum1 = banknum1;
	}

	public String getInvoiceaddress1() {
		return invoiceaddress1;
	}

	public void setInvoiceaddress1(String invoiceaddress1) {
		this.invoiceaddress1 = invoiceaddress1;
	}

	public String getInvoicetitle2() {
		return invoicetitle2;
	}

	public void setInvoicetitle2(String invoicetitle2) {
		this.invoicetitle2 = invoicetitle2;
	}

	public Long getArea2() {
		return area2;
	}

	public void setArea2(Long area2) {
		this.area2 = area2;
	}

	public String getInvoicename2() {
		return invoicename2;
	}

	public void setInvoicename2(String invoicename2) {
		this.invoicename2 = invoicename2;
	}

	public String getInvoicenum2() {
		return invoicenum2;
	}

	public void setInvoicenum2(String invoicenum2) {
		this.invoicenum2 = invoicenum2;
	}

	public String getBanknum2() {
		return banknum2;
	}

	public void setBanknum2(String banknum2) {
		this.banknum2 = banknum2;
	}

	public String getInvoiceaddress2() {
		return invoiceaddress2;
	}

	public void setInvoiceaddress2(String invoiceaddress2) {
		this.invoiceaddress2 = invoiceaddress2;
	}

	public String getInvoicetitle3() {
		return invoicetitle3;
	}

	public void setInvoicetitle3(String invoicetitle3) {
		this.invoicetitle3 = invoicetitle3;
	}

	public Long getArea3() {
		return area3;
	}

	public void setArea3(Long area3) {
		this.area3 = area3;
	}

	public String getInvoicename3() {
		return invoicename3;
	}

	public void setInvoicename3(String invoicename3) {
		this.invoicename3 = invoicename3;
	}

	public String getInvoicenum3() {
		return invoicenum3;
	}

	public void setInvoicenum3(String invoicenum3) {
		this.invoicenum3 = invoicenum3;
	}

	public String getBanknum3() {
		return banknum3;
	}

	public void setBanknum3(String banknum3) {
		this.banknum3 = banknum3;
	}

	public String getInvoiceaddress3() {
		return invoiceaddress3;
	}

	public void setInvoiceaddress3(String invoiceaddress3) {
		this.invoiceaddress3 = invoiceaddress3;
	}

	public String getInvoicetitle4() {
		return invoicetitle4;
	}

	public void setInvoicetitle4(String invoicetitle4) {
		this.invoicetitle4 = invoicetitle4;
	}

	public Long getArea4() {
		return area4;
	}

	public void setArea4(Long area4) {
		this.area4 = area4;
	}

	public String getInvoicename4() {
		return invoicename4;
	}

	public void setInvoicename4(String invoicename4) {
		this.invoicename4 = invoicename4;
	}

	public String getInvoicenum4() {
		return invoicenum4;
	}

	public void setInvoicenum4(String invoicenum4) {
		this.invoicenum4 = invoicenum4;
	}

	public String getBanknum4() {
		return banknum4;
	}

	public void setBanknum4(String banknum4) {
		this.banknum4 = banknum4;
	}

	public String getInvoiceaddress4() {
		return invoiceaddress4;
	}

	public void setInvoiceaddress4(String invoiceaddress4) {
		this.invoiceaddress4 = invoiceaddress4;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
    
}
