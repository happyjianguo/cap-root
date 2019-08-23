package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: CebaOutageLogModel 
* @Description: 停运通知流水日志模型
* @作者 杜振铎
* @date 2019年5月14日 下午2:10:15 
*  
*/
public class CebaOutageLogModel extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = 4985529026950360461L;
	
	public CebaOutageLogModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	private Long noticeTime;
	private String outageNoticeId;
	private String companyId;
	private String companyName;
	private String outageReason;
	private String outageDesc;
	private Long outageBeginTime;
	private Long outageEndTime;
	private Integer fixDate;
	private Integer fixTime;
	private String remark;

	public Long getNoticeTime() {
		return noticeTime;
	}
	public void setNoticeTime(Long noticeTime) {
		this.noticeTime = noticeTime;
	}
	public String getOutageNoticeId() {
		return outageNoticeId;
	}
	public void setOutageNoticeId(String outageNoticeId) {
		this.outageNoticeId = outageNoticeId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOutageReason() {
		return outageReason;
	}
	public void setOutageReason(String outageReason) {
		this.outageReason = outageReason;
	}
	public String getOutageDesc() {
		return outageDesc;
	}
	public void setOutageDesc(String outageDesc) {
		this.outageDesc = outageDesc;
	}
	public Long getOutageBeginTime() {
		return outageBeginTime;
	}
	public void setOutageBeginTime(Long outageBeginTime) {
		this.outageBeginTime = outageBeginTime;
	}
	public Long getOutageEndTime() {
		return outageEndTime;
	}
	public void setOutageEndTime(Long outageEndTime) {
		this.outageEndTime = outageEndTime;
	}
	public Integer getFixDate() {
		return fixDate;
	}
	public void setFixDate(Integer fixDate) {
		this.fixDate = fixDate;
	}
	public Integer getFixTime() {
		return fixTime;
	}
	public void setFixTime(Integer fixTime) {
		this.fixTime = fixTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
