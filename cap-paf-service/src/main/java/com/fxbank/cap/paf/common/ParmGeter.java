package com.fxbank.cap.paf.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;

import redis.clients.jedis.Jedis;

@Component
public class ParmGeter {
	
	@Resource
	private MyJedis myJedis;
	
	public String getValue(String prefix,String key) throws SysTradeExecuteException{
		String value;
		try (Jedis jedis = myJedis.connect()) {
			if (jedis == null) {
				SysTradeExecuteException e = new SysTradeExecuteException(
						SysTradeExecuteException.CIP_E_000001);
				throw e;
			}
			value = jedis.get(prefix+key);
			if (value == null) {
				SysTradeExecuteException e = new SysTradeExecuteException(
						SysTradeExecuteException.CIP_E_000001);
				throw e;
			}
		}
		return value;
	}
}
