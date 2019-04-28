package com.fxbank.cap.ykwm.netty.ykwm;

import javax.annotation.Resource;

import com.fxbank.cap.ykwm.common.ScrtUtil;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettySyncClient;
import com.fxbank.cip.base.netty.NettySyncSlot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
public class YkwmClient {

    private static Logger logger = LoggerFactory.getLogger(YkwmClient.class);

    public static final String CODING = "UTF-8";
    public static final String PREFIX = "ykwm.";
    public static final String YKWM_IP_KEY = PREFIX+"ykwm_ip";
    public static final String YKWM_PORT_KEY = PREFIX+"ykwm_port";
    public static final String YKWM_TIMEOUT_KEY = PREFIX+"ykwm_timeout";

    @Resource
	private MyJedis myJedis;
    
    @Resource
	private ScrtUtil scrtUtil;
    
    public <T> T comm(MyLog myLog,Object req,Class<T> clazz) throws SysTradeExecuteException {
        String ip = null;
        Integer port =0;
        Integer timeOut =0;
		try(Jedis jedis=myJedis.connect()){
            ip = jedis.get(YKWM_IP_KEY);
            String sport = jedis.get(YKWM_PORT_KEY);
            String stimeOut = jedis.get(YKWM_TIMEOUT_KEY);
            if(ip==null || sport == null || stimeOut ==null){
                myLog.error(logger, "IP、PORT、TIMEOUT配置异常", ip,sport,stimeOut);
                SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
                throw e;
            }
            port = Integer.valueOf(sport);
            timeOut = Integer.valueOf(stimeOut);
            if(port==0 || timeOut ==0){
                myLog.error(logger, "IP、PORT、TIMEOUT配置异常", port,timeOut);
                SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
                throw e;
            }
        }
        myLog.info(logger, "连接信息：IP["+ip+"],PORT["+port+"],TIMEOUT["+timeOut+"]");

        YkwmInitializer<T> initializer = new YkwmInitializer<T>(myLog, req,clazz,scrtUtil);
        NettySyncClient<T> clientSync = new NettySyncClient<T>(myLog, initializer);        
        T repData = clientSync.comm(ip,port,timeOut);
        return repData;
    }

}