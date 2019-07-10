package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
     * 调整前光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败
     */
    private String prePayState;

    /**
     * 调整后光大银行记账状态,0-登记，1-超时，2-处理成功，3-处理失败
     */
    private String rePayState;

    /**
     * 对账标志，1-未对账，2-已对账，3-光大银行多，4-渠道多
     */
    private String checkFlag;

    /**
     * 交易金额
     */
    private BigDecimal txAmt;

    private String bankBillNo;
    
    private String remark;
    
	public String getPrePayState() {
		return prePayState;
	}

	public void setPrePayState(String prePayState) {
		this.prePayState = prePayState;
	}

	public String getRePayState() {
		return rePayState;
	}

	public void setRePayState(String rePayState) {
		this.rePayState = rePayState;
	}

	public String getBankBillNo() {
		return bankBillNo;
	}

	public void setBankBillNo(String bankBillNo) {
		this.bankBillNo = bankBillNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}
