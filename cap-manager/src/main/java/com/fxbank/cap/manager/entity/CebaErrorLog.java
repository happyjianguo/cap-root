package com.fxbank.cap.manager.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * CEBA_ERROR_LOG
 */
@Table(name = "CEBA_ERROR_LOG")
@Alias("cebaErrorLog")
public class CebaErrorLog extends BaseData{
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
    @Column(name = "PRE_PAY_STATE")
    private String prePayState;

    /**
     * null
     */
    @Column(name = "RE_PAY_STATE")
    private String rePayState;

    /**
     * null
     */
    @Column(name = "CEBA_CHECK_FLAG")
    private String cebaCheckFlag;

    /**
     * null
     */
    @Column(name = "PRE_HOST_STATE")
    private String preHostState;

    /**
     * null
     */
    @Column(name = "RE_HOST_STATE")
    private String reHostState;

    /**
     * null
     */
    @Column(name = "HOST_CHECK_FLAG")
    private String hostCheckFlag;

    /**
     * null
     */
    @Column(name = "TX_AMT")
    private Short txAmt;

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
    @Column(name = "BANK_BILL_NO")
    private String bankBillNo;

    /**
     * null
     */
    @Column(name = "REMARK")
    private String remark;

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
     * @return PRE_PAY_STATE null
     */
    public String getPrePayState() {
        return prePayState;
    }

    /**
     * null
     * @param prePayState null
     */
    public void setPrePayState(String prePayState) {
        this.prePayState = prePayState;
    }

    /**
     * null
     * @return RE_PAY_STATE null
     */
    public String getRePayState() {
        return rePayState;
    }

    /**
     * null
     * @param rePayState null
     */
    public void setRePayState(String rePayState) {
        this.rePayState = rePayState;
    }

    /**
     * null
     * @return CEBA_CHECK_FLAG null
     */
    public String getCebaCheckFlag() {
        return cebaCheckFlag;
    }

    /**
     * null
     * @param cebaCheckFlag null
     */
    public void setCebaCheckFlag(String cebaCheckFlag) {
        this.cebaCheckFlag = cebaCheckFlag;
    }

    /**
     * null
     * @return PRE_HOST_STATE null
     */
    public String getPreHostState() {
        return preHostState;
    }

    /**
     * null
     * @param preHostState null
     */
    public void setPreHostState(String preHostState) {
        this.preHostState = preHostState;
    }

    /**
     * null
     * @return RE_HOST_STATE null
     */
    public String getReHostState() {
        return reHostState;
    }

    /**
     * null
     * @param reHostState null
     */
    public void setReHostState(String reHostState) {
        this.reHostState = reHostState;
    }

    /**
     * null
     * @return HOST_CHECK_FLAG null
     */
    public String getHostCheckFlag() {
        return hostCheckFlag;
    }

    /**
     * null
     * @param hostCheckFlag null
     */
    public void setHostCheckFlag(String hostCheckFlag) {
        this.hostCheckFlag = hostCheckFlag;
    }

    /**
     * null
     * @return TX_AMT null
     */
    public Short getTxAmt() {
        return txAmt;
    }

    /**
     * null
     * @param txAmt null
     */
    public void setTxAmt(Short txAmt) {
        this.txAmt = txAmt;
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
     * @return REMARK null
     */
    public String getRemark() {
        return remark;
    }

    /**
     * null
     * @param remark null
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}