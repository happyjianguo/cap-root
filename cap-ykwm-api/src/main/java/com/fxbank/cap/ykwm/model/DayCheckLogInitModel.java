package com.fxbank.cap.ykwm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: DayCheckLogInitModel 
* @Description: 对账登记
* @author Duzhenduo
* @date 2019年1月31日 上午10:09:47 
*  
*/
public class DayCheckLogInitModel extends ModelBase implements Serializable {

	public DayCheckLogInitModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8850394891802837L;

    /**
     * 柜员号
     */
    private String telNo;

    /**
     * 机构号
     */
    private String branchNo;

    /**
     * 核心交易日期
     */
    private Integer hostDate;

    /**
     * 核心流水号
     */
    private String hostTraceno;

    /**
     * 交易金额
     */
    private BigDecimal txAmt;

    /**
     * 交易账户
     */
    private String accountno;

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

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

}
