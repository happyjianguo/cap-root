package com.fxbank.cap.ykwm.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * YKWM_ERRORLOG
 */
@Table(name = "YKWM_ERRORLOG")
@Alias("ykwmErrorlog")
public class YkwmErrorlog {
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
    @Column(name = "USER_NAME")
    private String userName;

    /**
     * null
     */
    @Column(name = "ACCT_NO")
    private String acctNo;

    /**
     * null
     */
    @Column(name = "USER_CARD")
    private String userCard;
    
    /**
     * null
     */
    private String remark;
    
    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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
     * @return USER_NAME null
     */
    public String getUserName() {
        return userName;
    }

    /**
     * null
     * @param userName null
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * null
     * @return ACCT_NO null
     */
    public String getAcctNo() {
        return acctNo;
    }

    /**
     * null
     * @param acctNo null
     */
    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    /**
     * null
     * @return USER_CARD null
     */
    public String getUserCard() {
        return userCard;
    }

    /**
     * null
     * @param userCard null
     */
    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }
}