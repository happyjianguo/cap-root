package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: HostCheckLogInitModel 
* @Description: 核心对账登记
* @author Duzhenduo
* @date 2019年1月31日 上午10:09:47 
*  
*/
public class HostCheckLogInitModel extends ModelBase implements Serializable {

	public HostCheckLogInitModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8850394891802837L;

    /**
     * 清算日期
     */
    private Integer settleDate;
    
    /**
     * 交易类型
     */
    private String tranType;

    /**
     * 清算机构
     */
    private String settleBranch;

    /**
     * 核心交易日期
     */
    private Integer hostDate;

    /**
     * 核心流水号
     */
    private String hostTraceno;

    /**
     * 交易币种
     */
    private String ccy;

    /**
     * 交易金额
     */
    private BigDecimal txAmt;
    /**
     * 手续费收取方式
     */
    private String feeFlag;
    /**
     * 手续费
     */
    private BigDecimal fee;
    /**
     * 发起行人行行号
     */
    private String sndBankno;
    /**
     * 接收行人行行号
     */
    private String rcvBankno;
    /**
     * 付款人开户行
     */
    private String payerBank;
    /**
     * 收款人开户行
     */
    private String payerActtp;
    /**
     * 收款人开户行
     */
    private String payeeBank;
    /**
     * 收款人开户行
     */
    private String payeeActtp;

    
    
    /**
     * 交易账户
     */
    private String accountno;

    /**
     * 冲正标志
     */
    private String reversal;

    /**
     * 交易状态；00-成功 02-冲正
     */
    private String txStatus;

	public Integer getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Integer settleDate) {
		this.settleDate = settleDate;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getSettleBranch() {
		return settleBranch;
	}

	public void setSettleBranch(String settleBranch) {
		this.settleBranch = settleBranch;
	}

	public Integer getHostDate() {
		return hostDate;
	}

	public void setHostDate(Integer hostDate) {
		this.hostDate = hostDate;
	}

	public String getHostTraceno() {
		return hostTraceno;
	}

	public void setHostTraceno(String hostTraceno) {
		this.hostTraceno = hostTraceno;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

	public String getFeeFlag() {
		return feeFlag;
	}

	public void setFeeFlag(String feeFlag) {
		this.feeFlag = feeFlag;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getSndBankno() {
		return sndBankno;
	}

	public void setSndBankno(String sndBankno) {
		this.sndBankno = sndBankno;
	}

	public String getRcvBankno() {
		return rcvBankno;
	}

	public void setRcvBankno(String rcvBankno) {
		this.rcvBankno = rcvBankno;
	}

	public String getPayerBank() {
		return payerBank;
	}

	public void setPayerBank(String payerBank) {
		this.payerBank = payerBank;
	}

	public String getPayerActtp() {
		return payerActtp;
	}

	public void setPayerActtp(String payerActtp) {
		this.payerActtp = payerActtp;
	}

	public String getPayeeBank() {
		return payeeBank;
	}

	public void setPayeeBank(String payeeBank) {
		this.payeeBank = payeeBank;
	}

	public String getPayeeActtp() {
		return payeeActtp;
	}

	public void setPayeeActtp(String payeeActtp) {
		this.payeeActtp = payeeActtp;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getReversal() {
		return reversal;
	}

	public void setReversal(String reversal) {
		this.reversal = reversal;
	}

	public String getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}

}
