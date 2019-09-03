package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: CebaSettleLogModel 
* @Description: 查询缴费单信息日志模型
* @作者 杜振铎
* @date 2019年5月14日 下午2:10:15 
*  
*/
public class CebaSettleLogModel extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = 4985529026950360461L;
	
	public CebaSettleLogModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	private Integer chkDate;
	private BigDecimal txAmt;
	private String txSts;
	private Integer cnapsDate;
	private String cnapsTraceno;
	private String cnapsCode;
	private String cnapsMsg;

	public Integer getChkDate() {
		return chkDate;
	}
	public void setChkDate(Integer chkDate) {
		this.chkDate = chkDate;
	}
	public BigDecimal getTxAmt() {
		return txAmt;
	}
	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}
	public String getTxSts() {
		return txSts;
	}
	public void setTxSts(String txSts) {
		this.txSts = txSts;
	}
	public Integer getCnapsDate() {
		return cnapsDate;
	}
	public void setCnapsDate(Integer cnapsDate) {
		this.cnapsDate = cnapsDate;
	}
	public String getCnapsTraceno() {
		return cnapsTraceno;
	}
	public void setCnapsTraceno(String cnapsTraceno) {
		this.cnapsTraceno = cnapsTraceno;
	}
	public String getCnapsCode() {
		return cnapsCode;
	}
	public void setCnapsCode(String cnapsCode) {
		this.cnapsCode = cnapsCode;
	}
	public String getCnapsMsg() {
		return cnapsMsg;
	}
	public void setCnapsMsg(String cnapsMsg) {
		this.cnapsMsg = cnapsMsg;
	}
	
}
