package com.fxbank.cap.ykwm.exception;

import java.util.HashMap;
import java.util.Map;

import com.fxbank.cip.base.exception.SysTradeExecuteException;

/** 
* @ClassName: YkwmTradeExecuteException 
* @Description: 异常类及响应码、响应信息定义
* @作者 杜振铎
* @date 2018年4月3日 下午3:40:26 
*  
*/
public class YkwmTradeExecuteException extends SysTradeExecuteException {
	
	private static final long serialVersionUID = 2915820465784358202L;

	public final static String YKWM_E_10001 = "fx10001";
	public final static String YKWM_E_10002 = "fx10002";
	public final static String YKWM_E_10003 = "fx10003";
	public final static String YKWM_E_10004 = "fx10004";
	public final static String YKWM_E_10005 = "fx10005";
	public final static String YKWM_E_10006 = "fx10006";
	public final static String YKWM_E_10007 = "fx10007";
	public final static String YKWM_E_10008 = "fx10008";
	public final static String YKWM_E_10009 = "fx10009";

	public final static Map<String, String> YKWMERRCODECONV = new HashMap<String, String>() {
		private static final long serialVersionUID = -8667135437237760216L;

		{
			put(YKWM_E_10001, "业务不支持");
			put(YKWM_E_10002, "核心记账超时，请查账");
			put(YKWM_E_10003, "营口热力记账超时");
			put(YKWM_E_10004, "待冲正信息不存在");
			put(YKWM_E_10005, "下载ESB文件失败");
			put(YKWM_E_10006, "营口热力对账失败");
			put(YKWM_E_10007, "缴费金额不能大于欠费金额");
			put(YKWM_E_10008, "营口热力撤销超时");
			put(YKWM_E_10009, "请勿重复缴费");
			
		}
	};

	public YkwmTradeExecuteException(String rspCode) {
		super(rspCode, YKWMERRCODECONV.get(rspCode) == null ? "响应码未定义" : YKWMERRCODECONV.get(rspCode));
	}

	public YkwmTradeExecuteException(String rspCode, String rspMsg) {
		super(rspCode, rspMsg);
	}

}
