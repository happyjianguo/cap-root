package com.fxbank.cap.ceba.model;

import com.alibaba.fastjson.annotation.JSONField;


/** 
* @ClassName: ErrorInfo 
* @Description: 错误码说明
* @作者 杜振铎
* @date 2019年5月8日 下午3:00:46 
*  
*/
public class ErrorInfo {
	
	@JSONField(name = "ERROR_CODE")
	private String errorCode;
	
	@JSONField(name = "QR_ERROR_MSG")
	private String qrErrorMsg;

	@JSONField(name = "CG_ERROR_MSG")
	private String cgErrorMsg;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getQrErrorMsg() {
		return qrErrorMsg;
	}

	public void setQrErrorMsg(String qrErrorMsg) {
		this.qrErrorMsg = qrErrorMsg;
	}

	public String getCgErrorMsg() {
		return cgErrorMsg;
	}

	public void setCgErrorMsg(String cgErrorMsg) {
		this.cgErrorMsg = cgErrorMsg;
	}
	
	

	
}
