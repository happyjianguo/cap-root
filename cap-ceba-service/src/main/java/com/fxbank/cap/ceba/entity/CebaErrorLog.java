package com.fxbank.cap.ceba.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * CEBA_ERROR_LOG
 */
@Table(name = "CEBA_ERROR_LOG")
@Alias("cebaErrorLog")
public class CebaErrorLog {
    /**
     * null
     */
    @Id
    @Column(name = "PLAT_DATE")
    private Integer platDate;

    /**
     * null
     */
    @Id
    @Column(name = "PLAT_TRACE")
    private Integer platTrace;

    /**
     * null
     */
    @Column(name = "PLAT_TIME")
    private Integer platTime;

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
    @Column(name = "CHECK_FLAG")
    private String checkFlag;

    /**
     * null
     */
    @Column(name = "TX_AMT")
    private BigDecimal txAmt;

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
    public Integer getPlatDate() {
        return platDate;
    }

    /**
     * null
     * @param platDate null
     */
    public void setPlatDate(Integer platDate) {
        this.platDate = platDate;
    }

    /**
     * null
     * @return PLAT_TRACE null
     */
    public Integer getPlatTrace() {
        return platTrace;
    }

    /**
     * null
     * @param platTrace null
     */
    public void setPlatTrace(Integer platTrace) {
        this.platTrace = platTrace;
    }

    /**
     * null
     * @return PLAT_TIME null
     */
    public Integer getPlatTime() {
        return platTime;
    }

    /**
     * null
     * @param platTime null
     */
    public void setPlatTime(Integer platTime) {
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
     * @return CHECK_FLAG null
     */
    public String getCheckFlag() {
        return checkFlag;
    }

    /**
     * null
     * @param checkFlag null
     */
    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    /**
     * null
     * @return TX_AMT null
     */
    public BigDecimal getTxAmt() {
        return txAmt;
    }

    /**
     * null
     * @param txAmt null
     */
    public void setTxAmt(BigDecimal txAmt) {
        this.txAmt = txAmt;
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