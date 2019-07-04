package com.fxbank.cap.ceba.netty.ceba;

import javax.annotation.Resource;

import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.exception.SysTradeExecuteException;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettyAsyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/** 
* @ClassName: CebaClient 
* @Description: 光大银行客户端通讯主程序
* @作者 杜振铎
* @date 2019年5月7日 下午5:07:33 
*  
*/
@Component
public class CebaClient {

    private static Logger logger = LoggerFactory.getLogger(CebaClient.class);

    public static final String CODING = "GBK";
    public static final String PREFIX = "ceba.";
    public static final String CEBA_IP_KEY = PREFIX + "ceba_ip";
    public static final String CEBA_PORT_KEY = PREFIX + "ceba_port";
    public static final String CEBA_TIMEOUT_KEY = PREFIX + "ceba_timeout";

    @Resource
    private MyJedis myJedis;

    public void comm(MyLog myLog, Object req) throws SysTradeExecuteException {
        String ip = null;
        Integer port = 0;
        Integer timeOut = 0;
        try (Jedis jedis = myJedis.connect()) {
            ip = jedis.get(CEBA_IP_KEY);
            String sport = jedis.get(CEBA_PORT_KEY);
            String stimeOut = jedis.get(CEBA_TIMEOUT_KEY);
            if (ip == null || sport == null || stimeOut == null) {
                myLog.error(logger, "IP、PORT、TIMEOUT配置异常", ip, sport, stimeOut);
                SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
                throw e;
            }
            port = Integer.valueOf(sport);
            timeOut = Integer.valueOf(stimeOut);
            if (port == 0 || timeOut == 0) {
                myLog.error(logger, "IP、PORT、TIMEOUT配置异常", port, timeOut);
                SysTradeExecuteException e = new SysTradeExecuteException(SysTradeExecuteException.CIP_E_999999);
                throw e;
            }
        }
        myLog.info(logger, "连接信息：IP[" + ip + "],PORT[" + port + "],TIMEOUT[" + timeOut + "]");

        CebaInitializer initializer = new CebaInitializer(myLog);
        NettyAsyncClient clientAsync = new NettyAsyncClient(myLog, initializer);
        clientAsync.comm(ip, port,(String)req);
    }

}