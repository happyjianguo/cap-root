package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: CebaRefundeLogModel 
* @Description: 退款流水日志模型
* @作者 杜振铎
* @date 2019年5月14日 下午2:10:15 
*  
*/
public class CebaRefundeLogModel extends ModelBase implements Serializable{
	
	private static final long serialVersionUID = 4985529026950360461L;
	
	public CebaRefundeLogModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}
	
	private String flag;
	private String status;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
}
