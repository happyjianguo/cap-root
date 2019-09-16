package com.fxbank.cap.ykwm.entity;

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
public class YkwmSettlelog {
    /**
     * null
     */
    @Id
    @Column(name = "CHK_DATE")
    private Integer chkDate;

    /**
     * null
     */
    @Column(name = "TX_STS")
    private String txSts;

    /**
     * null
     */
    @Column(name = "HOST_DATE")
    private Integer hostDate;

    
    @Column(name = "HOST_CODE")
    private String hostCode;
    
    @Column(name = "HOST_MSG")
    private String hostMsg;
    
    public String getHostCode() {
		return hostCode;
	}

	public void setHostCode(String hostCode) {
		this.hostCode = hostCode;
	}

	public String getHostMsg() {
		return hostMsg;
	}

	public void setHostMsg(String hostMsg) {
		this.hostMsg = hostMsg;
	}

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