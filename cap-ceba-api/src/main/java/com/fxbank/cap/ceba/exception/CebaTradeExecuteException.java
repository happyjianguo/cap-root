package com.fxbank.cap.ceba.exception;

import java.util.HashMap;
import java.util.Map;

import com.fxbank.cip.base.exception.SysTradeExecuteException;


/** 
* @ClassName: CebaTradeExecuteException 
* @Description: 异常类及响应码、响应信息定义
* @作者 杜振铎
* @date 2019年5月7日 下午4:58:04 
*  
*/
public class CebaTradeExecuteException extends SysTradeExecuteException {
	
	private static final long serialVersionUID = 1767308581692333362L;

	public final static String CEBA_E_10001 = "FX0001";
	public final static String CEBA_E_10002 = "FX0002";
	public final static String CEBA_E_10003 = "FX0003";
	public final static String CEBA_E_10004 = "FX0004";
	public final static String CEBA_E_10005 = "FX0005";
	public final static String CEBA_E_10006 = "FX0006";

	public final static Map<String, String> TCEXERRCODECONV = new HashMap<String, String>() {

		private static final long serialVersionUID = -3713497985727043375L;

		{
			put(CEBA_E_10001, "业务不支持");
			put(CEBA_E_10002, "光大银行受理失败");
			put(CEBA_E_10003, "光大银行记账超时");
			put(CEBA_E_10004, "缴费城市代码不存在");
			put(CEBA_E_10005, "渠道流水号不存在");
			put(CEBA_E_10006, "核心记账超时，请查账");
		}
	};

	public CebaTradeExecuteException(String rspCode) {
		super(rspCode, TCEXERRCODECONV.get(rspCode) == null ? "响应码未定义" : TCEXERRCODECONV.get(rspCode));
	}

	public CebaTradeExecuteException(String rspCode, String rspMsg) {
		super(rspCode, rspMsg);
	}

}
