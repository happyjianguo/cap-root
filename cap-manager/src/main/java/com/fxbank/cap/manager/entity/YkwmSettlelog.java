package com.fxbank.cap.manager.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * YKWM_SETTLELOG
 */
@Table(name = "YKWM_SETTLELOG")
@Alias("ykwmSettlelog")
public class YkwmSettlelog extends BaseData{
    /**
     * null
     */
    @Id
    @Column(name = "CHK_DATE")
    private Long chkDate;

    /**
     * null
     */
    @Column(name = "TX_STS")
    private String txSts;

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
    @Column(name = "TX_AMT")
    private BigDecimal txAmt;

    /**
     * null
     * @return CHK_DATE null
     */
    public Long getChkDate() {
        return chkDate;
    }

    /**
     * null
     * @param chkDate null
     */
    public void setChkDate(Long chkDate) {
        this.chkDate = chkDate;
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
}