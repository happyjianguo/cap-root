package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: CebaBillCheckModel 
* @Description: 对账文件模型 
* @作者 杜振铎
* @date 2019年9月24日 下午2:31:46 
*  
*/
public class CebaBillCheckModel extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = 4985529026950360461L;
	
	public CebaBillCheckModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	private Integer signDate;
	private String fileName;
	private Long uploadDate;
	private String status;
	private String remark;

	public Integer getSignDate() {
		return signDate;
	}
	public void setSignDate(Integer signDate) {
		this.signDate = signDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Long uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
