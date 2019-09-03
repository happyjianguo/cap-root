package com.fxbank.cap.manager.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * CEBA_CHARGE_LOG
 */
@Table(name = "CEBA_CHARGE_LOG")
@Alias("cebaChargeLog")
public class CebaChargeLog extends BaseData{
    /**
     * null
     */
    @Id
    @Column(name = "PLAT_DATE")
    private Long platDate;

    /**
     * null
     */
    @Id
    @Column(name = "PLAT_TRACE")
    private Long platTrace;

    /**
     * null
     */
    @Column(name = "PLAT_TIME")
    private Long platTime;

    /**
     * null
     */
    @Column(name = "SOURCE_TYPE")
    private String sourceType;

    /**
     * null
     */
    @Column(name = "TX_BRANCH")
    private String txBranch;

    /**
     * null
     */
    @Column(name = "TX_TEL")
    private String txTel;

    /**
     * null
     */
    @Column(name = "BILL_KEY")
    private String billKey;

    /**
     * null
     */
    @Column(name = "COMPANY_ID")
    private String companyId;

    /**
     * null
     */
    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    /**
     * null
     */
    @Column(name = "PAY_ACCOUNT")
    private String payAccount;

    /**
     * null
     */
    @Column(name = "PAY_AMOUNT")
    private Short payAmount;

    /**
     * null
     */
    @Column(name = "AC_TYPE")
    private String acType;

    /**
     * null
     */
    @Column(name = "CONTRACT_NO")
    private String contractNo;

    /**
     * null
     */
    @Column(name = "BANK_BILL_NO")
    private String bankBillNo;

    /**
     * null
     */
    @Column(name = "RECEIPT_NO")
    private String receiptNo;

    /**
     * null
     */
    @Column(name = "ACCT_DATE")
    private String acctDate;

    /**
     * null
     */
    @Column(name = "PAY_STATE")
    private String payState;

    /**
     * null
     */
    @Column(name = "ERROR_CODE")
    private String errorCode;

    /**
     * null
     */
    @Column(name = "HOST_CHECK_STATE")
    private String hostCheckState;

    /**
     * null
     */
    @Column(name = "SEQ_NO")
    private String seqNo;

    /**
     * null
     */
    @Column(name = "HOST_DATE")
    private Long hostDate;

    /**
     * null
     */
    @Column(name = "HOST_TRACENO")
    private String hostTraceno;

    /**
     * null
     */
    @Column(name = "HOST_STATE")
    private String hostState;

    /**
     * null
     */
    @Column(name = "HOST_RET_CODE")
    private String hostRetCode;

    /**
     * null
     */
    @Column(name = "HOST_RET_MSG")
    private String hostRetMsg;

    /**
     * null
     */
    @Column(name = "CEBA_CHECK_STATE")
    private String cebaCheckState;

    /**
     * null
     * @return PLAT_DATE null
     */
    public Long getPlatDate() {
        return platDate;
    }

    /**
     * null
     * @param platDate null
     */
    public void setPlatDate(Long platDate) {
        this.platDate = platDate;
    }

    /**
     * null
     * @return PLAT_TRACE null
     */
    public Long getPlatTrace() {
        return platTrace;
    }

    /**
     * null
     * @param platTrace null
     */
    public void setPlatTrace(Long platTrace) {
        this.platTrace = platTrace;
    }

    /**
     * null
     * @return PLAT_TIME null
     */
    public Long getPlatTime() {
        return platTime;
    }

    /**
     * null
     * @param platTime null
     */
    public void setPlatTime(Long platTime) {
        this.platTime = platTime;
    }

    /**
     * null
     * @return SOURCE_TYPE null
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * null
     * @param sourceType null
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * null
     * @return TX_BRANCH null
     */
    public String getTxBranch() {
        return txBranch;
    }

    /**
     * null
     * @param txBranch null
     */
    public void setTxBranch(String txBranch) {
        this.txBranch = txBranch;
    }

    /**
     * null
     * @return TX_TEL null
     */
    public String getTxTel() {
        return txTel;
    }

    /**
     * null
     * @param txTel null
     */
    public void setTxTel(String txTel) {
        this.txTel = txTel;
    }

    /**
     * null
     * @return BILL_KEY null
     */
    public String getBillKey() {
        return billKey;
    }

    /**
     * null
     * @param billKey null
     */
    public void setBillKey(String billKey) {
        this.billKey = billKey;
    }

    /**
     * null
     * @return COMPANY_ID null
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * null
     * @param companyId null
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * null
     * @return CUSTOMER_NAME null
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * null
     * @param customerName null
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * null
     * @return PAY_ACCOUNT null
     */
    public String getPayAccount() {
        return payAccount;
    }

    /**
     * null
     * @param payAccount null
     */
    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    /**
     * null
     * @return PAY_AMOUNT null
     */
    public Short getPayAmount() {
        return payAmount;
    }

    /**
     * null
     * @param payAmount null
     */
    public void setPayAmount(Short payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * null
     * @return AC_TYPE null
     */
    public String getAcType() {
        return acType;
    }

    /**
     * null
     * @param acType null
     */
    public void setAcType(String acType) {
        this.acType = acType;
    }

    /**
     * null
     * @return CONTRACT_NO null
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * null
     * @param contractNo null
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    /**
     * null
     * @return BANK_BILL_NO null
     */
    public String getBankBillNo() {
        return bankBillNo;
    }

    /**
     * null
     * @param bankBillNo null
     */
    public void setBankBillNo(String bankBillNo) {
        this.bankBillNo = bankBillNo;
    }

    /**
     * null
     * @return RECEIPT_NO null
     */
    public String getReceiptNo() {
        return receiptNo;
    }

    /**
     * null
     * @param receiptNo null
     */
    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    /**
     * null
     * @return ACCT_DATE null
     */
    public String getAcctDate() {
        return acctDate;
    }

    /**
     * null
     * @param acctDate null
     */
    public void setAcctDate(String acctDate) {
        this.acctDate = acctDate;
    }

    /**
     * null
     * @return PAY_STATE null
     */
    public String getPayState() {
        return payState;
    }

    /**
     * null
     * @param payState null
     */
    public void setPayState(String payState) {
        this.payState = payState;
    }

    /**
     * null
     * @return ERROR_CODE null
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * null
     * @param errorCode null
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * null
     * @return HOST_CHECK_STATE null
     */
    public String getHostCheckState() {
        return hostCheckState;
    }

    /**
     * null
     * @param hostCheckState null
     */
    public void setHostCheckState(String hostCheckState) {
        this.hostCheckState = hostCheckState;
    }

    /**
     * null
     * @return SEQ_NO null
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * null
     * @param seqNo null
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * null
     * @return HOST_DATE null
     */
    public Long getHostDate() {
        return hostDate;
    }

    /**
     * null
     * @param hostDate null
     */
    public void setHostDate(Long hostDate) {
        this.hostDate = hostDate;
    }

    /**
     * null
     * @return HOST_TRACENO null
     */
    public String getHostTraceno() {
        return hostTraceno;
    }

    /**
     * null
     * @param hostTraceno null
     */
    public void setHostTraceno(String hostTraceno) {
        this.hostTraceno = hostTraceno;
    }

    /**
     * null
     * @return HOST_STATE null
     */
    public String getHostState() {
        return hostState;
    }

    /**
     * null
     * @param hostState null
     */
    public void setHostState(String hostState) {
        this.hostState = hostState;
    }

    /**
     * null
     * @return HOST_RET_CODE null
     */
    public String getHostRetCode() {
        return hostRetCode;
    }

    /**
     * null
     * @param hostRetCode null
     */
    public void setHostRetCode(String hostRetCode) {
        this.hostRetCode = hostRetCode;
    }

    /**
     * null
     * @return HOST_RET_MSG null
     */
    public String getHostRetMsg() {
        return hostRetMsg;
    }

    /**
     * null
     * @param hostRetMsg null
     */
    public void setHostRetMsg(String hostRetMsg) {
        this.hostRetMsg = hostRetMsg;
    }

    /**
     * null
     * @return CEBA_CHECK_STATE null
     */
    public String getCebaCheckState() {
        return cebaCheckState;
    }

    /**
     * null
     * @param cebaCheckState null
     */
    public void setCebaCheckState(String cebaCheckState) {
        this.cebaCheckState = cebaCheckState;
    }
}