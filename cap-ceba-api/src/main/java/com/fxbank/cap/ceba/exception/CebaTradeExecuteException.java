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

	public final static Map<String, String> TCEXERRCODECONV = new HashMap<String, String>() {

		private static final long serialVersionUID = -3713497985727043375L;

		{
			put(CEBA_E_10001, "业务不支持");
		}
	};

	public CebaTradeExecuteException(String rspCode) {
		super(rspCode, TCEXERRCODECONV.get(rspCode) == null ? "响应码未定义" : TCEXERRCODECONV.get(rspCode));
	}

	public CebaTradeExecuteException(String rspCode, String rspMsg) {
		super(rspCode, rspMsg);
	}

}
