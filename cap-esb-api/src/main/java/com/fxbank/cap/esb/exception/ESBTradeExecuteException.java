package com.fxbank.cap.esb.exception;

import java.util.HashMap;
import java.util.Map;

import com.fxbank.cip.base.exception.SysTradeExecuteException;

public class ESBTradeExecuteException extends SysTradeExecuteException {

	
	private static final long serialVersionUID = -4396151878176541080L;

	public final static String CAP_E_000001 = "CAP_E_000001";
	public final static String CAP_E_000002 = "CAP_E_000002";
	public final static String CAP_E_000003 = "CAP_E_000003";
	public final static String CAP_E_000004 = "CAP_E_000004";
	
	public final static Map<String, String> ESBERRCODECONV = new HashMap<String, String>() {
		
		private static final long serialVersionUID = -6069828754523545158L;

		{
			put(CAP_E_000001, "调用加密平台PIN转加密失败");
			put(CAP_E_000002, "调用加密平台ZPK加密明文PIN失败");
			put(CAP_E_000003, "调用加密平台工作密钥更新失败");
			put(CAP_E_000004, "调用加密平台MAC校验失败");
			
			
		}
	};

	public ESBTradeExecuteException(String rspCode) {
		super(rspCode, ESBERRCODECONV.get(rspCode) == null ? "响应码未定义" : ESBERRCODECONV.get(rspCode));
	}

	public ESBTradeExecuteException(String rspCode, String rspMsg) {
		super(rspCode, rspMsg);
	}

}
