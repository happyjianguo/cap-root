package com.fxbank.cap.ykwm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;


public class YkwmTraceLogModel extends ModelBase implements Serializable{

	private static final long serialVersionUID = -4845870779601322852L;

	public YkwmTraceLogModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	//渠道流水
    private String capTransactionno;
    //热电流水
    private String pyTransactionno;
    //核心流水
    private String coTransactionno;
    //热电交易状态 0-登记，1-超时，2-处理成功，3-处理失败
    private String pyResult;
    //核心交易状态 0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时
    private String coResult;
    //对账状态 “0” 未对账, "1" 已对账
    private String capResult;
    private String capRepmsg;
    //热电日期
    private String pyDate;
    //核心日期
    private String coDate;
    //渠道日期
    private String capDate;
    //所属机构
    private String teBranchno;
    //操作柜员
    private String teName;
    //虚拟柜员
    private String teVirtualName;
    //查询流水号
    private String teCheckNum;
    //用户账号
    private String acctNoT;
    //缴费金额
    private String pyFeeAmtT;
    //用户欠费金额
    private String userDbtAmtT;
    //快递金额
    private String courierAmtT;
    //报销标志
    private String reimburseSignT;
    //热力流水号
    private String heatSeqNoT;
    //用户卡号
    private String userCardNoT;
    //联系电话
    private String cnttPhnT;
    //联系人
    private String lnmT3;
    //缴费方式
    private String pyFeeTpT;
    //发票获取方式 0未选择，1邮寄，2自取，3电子发票
    private String billGetTpT;
    //公司
    private String companyT;
    //供暖公司ID
    private String heatCompanyIdT;
    //用户地址
    private String userAddrT;
    //邮寄地址
    private String mailAddrT;
    //密码
    private String pwdT;
    //发票名头
    private String invcNaHdT3;
    //供暖公司名
    private String heatCompanyNmT;
    //姓名
    private String naT1;
    //邮编
    private String postNoT5;
    //快递公司ID
    private String courierCmpnyIdT;
    //缴费渠道（CTS:柜面、MBANK:手机银行、SBANK：自助、EBANK:个人网银、WBANK：微信）
    private String pySource;
    //网点编号
    private String pyBranchnum;
    //批次号，银行方提供的对账批次号，缴费时需要提交此批次号，如果查询和缴费时的批次号不一致，将导致缴费失败
    private String pyBatchnum;
    //快递公司ID
    private String exId;
    //快递公司名称
    private String exName;
    //快递费
    private BigDecimal exAmt;
    //供暖年度
    private String dtChargeyear;
    //供暖类型
    private String dtItemname;
    //供暖面积
    private Long dtArea;
    //供暖单价
    private Long dtPrice;
    //欠费金额
    private Long dtAccount;
    //优惠金额
    private Long dtAgio;
    //滞纳金
    private Long dtLatefee;
    //应交金额
    private Long dtPayment;
    //发票列表
    private List<Invoice> invoiceList;
    //取票码列表
    private String ticketNumber;
    //热电响应码
    private String pyRspcode;
    //热电错误信息
    private String pyErrorMsg;
    //核心响应码
    private String coRspcode;
    //核心响应信息
    private String coRspmsg;
    
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

	public String getReimburseSignT() {
		return reimburseSignT;
	}

	public void setReimburseSignT(String reimburseSignT) {
		this.reimburseSignT = reimburseSignT;
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

	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getPyRspcode() {
		return pyRspcode;
	}

	public void setPyRspcode(String pyRspcode) {
		this.pyRspcode = pyRspcode;
	}

	public String getPyErrorMsg() {
		return pyErrorMsg;
	}

	public void setPyErrorMsg(String pyErrorMsg) {
		this.pyErrorMsg = pyErrorMsg;
	}

	public String getCoRspcode() {
		return coRspcode;
	}

	public void setCoRspcode(String coRspcode) {
		this.coRspcode = coRspcode;
	}

	public String getCoRspmsg() {
		return coRspmsg;
	}

	public void setCoRspmsg(String coRspmsg) {
		this.coRspmsg = coRspmsg;
	}

	public static class Invoice implements Serializable{
		private static final long serialVersionUID = 8027873480548147655L;
		//发票抬头
    	private String invoiceTitle;
    	//面积
    	private String area;
    	//姓名
    	private String invoiceName;
    	//纳税人识别号
    	private String invoiceNum;
    	//开户行账号
    	private String bankNum;
    	//地址电话
    	private String invoiceAddress;
		public String getInvoiceTitle() {
			return invoiceTitle;
		}
		public void setInvoiceTitle(String invoiceTitle) {
			this.invoiceTitle = invoiceTitle;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getInvoiceName() {
			return invoiceName;
		}
		public void setInvoiceName(String invoiceName) {
			this.invoiceName = invoiceName;
		}
		public String getInvoiceNum() {
			return invoiceNum;
		}
		public void setInvoiceNum(String invoiceNum) {
			this.invoiceNum = invoiceNum;
		}
		public String getBankNum() {
			return bankNum;
		}
		public void setBankNum(String bankNum) {
			this.bankNum = bankNum;
		}
		public String getInvoiceAddress() {
			return invoiceAddress;
		}
		public void setInvoiceAddress(String invoiceAddress) {
			this.invoiceAddress = invoiceAddress;
		}
    	
    }
}
