package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

public class CheckErrorModel extends ModelBase implements Serializable {

	public CheckErrorModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	private static final long serialVersionUID = 5047661389258687397L;
	
    private String prePayState;

    private String rePayState;

    private String cebaCheckFlag;
    
    private String preHostState;

    private String reHostState;

    private String hostCheckFlag;

    private BigDecimal txAmt;
    
    public String getCebaCheckFlag() {
		return cebaCheckFlag;
	}

	public void setCebaCheckFlag(String cebaCheckFlag) {
		this.cebaCheckFlag = cebaCheckFlag;
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

	public String getHostCheckFlag() {
		return hostCheckFlag;
	}

	public void setHostCheckFlag(String hostCheckFlag) {
		this.hostCheckFlag = hostCheckFlag;
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

	private Integer hostDate;
    
    private String hostTraceno;

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

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

}
