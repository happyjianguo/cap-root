package com.fxbank.cap.ykwm.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * YKWM_CHECKLOG
 */
@Table(name = "YKWM_CHECKLOG")
@Alias("ykwmChecklog")
public class YkwmChecklog {
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
    @Column(name = "SETTLE_DATE")
    private Integer settleDate;

    /**
     * null
     */
    @Column(name = "SETTLE_BRANCH")
    private String settleBranch;

    /**
     * null
     */
    @Column(name = "HOST_DATE")
    private Integer hostDate;

    /**
     * null
     */
    @Column(name = "HOST_TRACENO")
    private String hostTraceno;

    /**
     * null
     */
    @Column(name = "CCY")
    private String ccy;

    /**
     * null
     */
    @Column(name = "TX_AMT")
    private BigDecimal txAmt;

    /**
     * null
     */
    @Column(name = "ACCOUNTNO")
    private String accountno;

    /**
     * null
     */
    @Column(name = "REVERSAL")
    private String reversal;

    /**
     * null
     */
    @Column(name = "TX_STATUS")
    private String txStatus;

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
     * @return SETTLE_DATE null
     */
    public Integer getSettleDate() {
        return settleDate;
    }

    /**
     * null
     * @param settleDate null
     */
    public void setSettleDate(Integer settleDate) {
        this.settleDate = settleDate;
    }

    /**
     * null
     * @return SETTLE_BRANCH null
     */
    public String getSettleBranch() {
        return settleBranch;
    }

    /**
     * null
     * @param settleBranch null
     */
    public void setSettleBranch(String settleBranch) {
        this.settleBranch = settleBranch;
    }

    /**
     * null
     * @return HOST_DATE null
     */
    public Integer getHostDate() {
        return hostDate;
    }

    /**
     * null
     * @param hostDate null
     */
    public void setHostDate(Integer hostDate) {
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
     * @return CCY null
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * null
     * @param ccy null
     */
    public void setCcy(String ccy) {
        this.ccy = ccy;
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
     * @return ACCOUNTNO null
     */
    public String getAccountno() {
        return accountno;
    }

    /**
     * null
     * @param accountno null
     */
    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    /**
     * null
     * @return REVERSAL null
     */
    public String getReversal() {
        return reversal;
    }

    /**
     * null
     * @param reversal null
     */
    public void setReversal(String reversal) {
        this.reversal = reversal;
    }

    /**
     * null
     * @return TX_STATUS null
     */
    public String getTxStatus() {
        return txStatus;
    }

    /**
     * null
     * @param txStatus null
     */
    public void setTxStatus(String txStatus) {
        this.txStatus = txStatus;
    }
}