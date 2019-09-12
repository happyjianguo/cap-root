package com.fxbank.cap.ykwm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: YkwmSettleLogModel 
* @Description:清算流水日志模型
* @作者 杜振铎
* @date 2019年9月11日 下午2:25:10 
*  
*/
public class YkwmSettleLogModel extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = 4985529026950360461L;
	
	public YkwmSettleLogModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	private Integer chkDate;
	private BigDecimal txAmt;
	private String txSts;
	private Integer hostDate;
	private String hostTraceno;
	private String hostCode;
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
	
}

