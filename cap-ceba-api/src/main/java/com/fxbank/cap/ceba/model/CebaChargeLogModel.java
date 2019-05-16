package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: CebaChargeLogModel 
* @Description: 缴费单销账日志模型
* @作者 杜振铎
* @date 2019年5月14日 下午2:10:15 
*  
*/
public class CebaChargeLogModel extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = 4985529026950360461L;
	
	public CebaChargeLogModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	private String sourceType;
	private String txBranch;
	private String txTel;
	private String billKey;
	private String companyId;
	private String customerName;
	private String payAccount;
	private BigDecimal payAmount;
	private String acType;
	private String contractNo;
	private String bankBillNo;
	private String receiptNo;
	private String acctDate;
	private String payState;
	private String errorCode;
	private String checkState;
	private String seqNo;
	private Integer hostDate;
	private String hostTraceNo;
	private String hostState;
	private String hostRetCode;
	private String hostRetMsg;

	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getTxBranch() {
		return txBranch;
	}
	public void setTxBranch(String txBranch) {
		this.txBranch = txBranch;
	}
	public String getBillKey() {
		return billKey;
	}
	public void setBillKey(String billKey) {
		this.billKey = billKey;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public String getAcType() {
		return acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
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
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	
	public String getTxTel() {
		return txTel;
	}
	public void setTxTel(String txTel) {
		this.txTel = txTel;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public Integer getHostDate() {
		return hostDate;
	}
	public void setHostDate(Integer hostDate) {
		this.hostDate = hostDate;
	}
	public String getHostTraceNo() {
		return hostTraceNo;
	}
	public void setHostTraceNo(String hostTraceNo) {
		this.hostTraceNo = hostTraceNo;
	}
	public String getHostState() {
		return hostState;
	}
	public void setHostState(String hostState) {
		this.hostState = hostState;
	}
	public String getHostRetCode() {
		return hostRetCode;
	}
	public void setHostRetCode(String hostRetCode) {
		this.hostRetCode = hostRetCode;
	}
	public String getHostRetMsg() {
		return hostRetMsg;
	}
	public void setHostRetMsg(String hostRetMsg) {
		this.hostRetMsg = hostRetMsg;
	}


	
}
