package com.fxbank.cap.ykwm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

public class CheckErrorModel extends ModelBase implements Serializable {

	public CheckErrorModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5047661389258687397L;
	
	/**
     * 渠道日期
     */
	@NotNull(message = "platDate渠道日期不能为空")
    private Integer platDate;

    /**
     * 渠道流水
     */
	@NotNull(message = "platTrace渠道流水不能为空")
    private Integer platTrace;

    /**
     * 调整前核心记账状态，核心交易状态 0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时
     */
    private String preHostState;

    /**
     * 调整后核心记账状态，核心交易状态 0-成功，1-冲正成功，2-冲正失败，3-冲正超时，4-超时
     */
    private String reHostState;

    /**
     * 对账标志，1-未对账，2-已对账，3-核心多，4-渠道多
     */
    private String checkFlag;

    /**
     * 交易金额
     */
    private BigDecimal txAmt;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号
     */
    private String acctNo;
    
    /**
     * 用户卡号
     */
    private String userCard;
    
    
    /**
     * 
    * 备注
     */
    private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserCard() {
		return userCard;
	}

	public void setUserCard(String userCard) {
		this.userCard = userCard;
	}

	public Integer getPlatDate() {
		return platDate;
	}

	public void setPlatDate(Integer platDate) {
		this.platDate = platDate;
	}

	public Integer getPlatTrace() {
		return platTrace;
	}

	public void setPlatTrace(Integer platTrace) {
		this.platTrace = platTrace;
	}

	public String getPreHostState() {
		return preHostState;
	}

	public void setPreHostState(String preHostState) {
		this.preHostState = preHostState;
	}

	public String getReHostState() {
		return reHostState;
	}

	public void setReHostState(String reHostState) {
		this.reHostState = reHostState;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

}
