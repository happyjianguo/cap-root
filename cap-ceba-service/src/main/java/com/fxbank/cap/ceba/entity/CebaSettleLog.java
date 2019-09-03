package com.fxbank.cap.ceba.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * CEBA_SETTLE_LOG
 */
@Table(name = "CEBA_SETTLE_LOG")
@Alias("cebaSettleLog")
public class CebaSettleLog {
    /**
     * null
     */
    @Id
    @Column(name = "CHK_DATE")
    private Integer chkDate;

    /**
     * null
     */
    @Column(name = "TX_AMT")
    private BigDecimal txAmt;

    /**
     * null
     */
    @Column(name = "TX_STS")
    private String txSts;

    /**
     * null
     */
    @Column(name = "CNAPS_DATE")
    private Integer cnapsDate;

    /**
     * null
     */
    @Column(name = "CNAPS_TRACENO")
    private String cnapsTraceno;

    /**
     * null
     */
    @Column(name = "CNAPS_CODE")
    private String cnapsCode;

    /**
     * null
     */
    @Column(name = "CNAPS_MSG")
    private String cnapsMsg;

    /**
     * null
     * @return CHK_DATE null
     */
    public Integer getChkDate() {
        return chkDate;
    }

    /**
     * null
     * @param chkDate null
     */
    public void setChkDate(Integer chkDate) {
        this.chkDate = chkDate;
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
     * @return TX_STS null
     */
    public String getTxSts() {
        return txSts;
    }

    /**
     * null
     * @param txSts null
     */
    public void setTxSts(String txSts) {
        this.txSts = txSts;
    }

    /**
     * null
     * @return CNAPS_DATE null
     */
    public Integer getCnapsDate() {
        return cnapsDate;
    }

    /**
     * null
     * @param cnapsDate null
     */
    public void setCnapsDate(Integer cnapsDate) {
        this.cnapsDate = cnapsDate;
    }

    /**
     * null
     * @return CNAPS_TRACENO null
     */
    public String getCnapsTraceno() {
        return cnapsTraceno;
    }

    /**
     * null
     * @param cnapsTraceno null
     */
    public void setCnapsTraceno(String cnapsTraceno) {
        this.cnapsTraceno = cnapsTraceno;
    }

    /**
     * null
     * @return CNAPS_CODE null
     */
    public String getCnapsCode() {
        return cnapsCode;
    }

    /**
     * null
     * @param cnapsCode null
     */
    public void setCnapsCode(String cnapsCode) {
        this.cnapsCode = cnapsCode;
    }

    /**
     * null
     * @return CNAPS_MSG null
     */
    public String getCnapsMsg() {
        return cnapsMsg;
    }

    /**
     * null
     * @param cnapsMsg null
     */
    public void setCnapsMsg(String cnapsMsg) {
        this.cnapsMsg = cnapsMsg;
    }
}