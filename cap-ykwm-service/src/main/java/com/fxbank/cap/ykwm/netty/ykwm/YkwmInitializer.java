package com.fxbank.cap.ykwm.netty.ykwm;

import com.fxbank.cap.ykwm.common.ScrtUtil;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettySyncHandler;
import com.fxbank.cip.base.netty.NettySyncSlot;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/** 
* @ClassName: YkwmInitializer 
* @Description: 客户端通讯初始化 
* @author Duzhenduo
* @date 2019年4月29日 下午2:19:49 
* 
* @param <T> 
*/
public class YkwmInitializer<T> extends ChannelInitializer<SocketChannel> {

    private MyLog myLog;
    private NettySyncSlot<T> slot;
    private Object reqData;
    private Class<T> clazz;
    private ScrtUtil scrtUtil;

    public YkwmInitializer(MyLog myLog, Object reqData, Class<T> clazz,ScrtUtil scrtUtil) {
        this.myLog = myLog;
        this.reqData = reqData;
        this.clazz = clazz;
        this.scrtUtil = scrtUtil;
    }
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new YkwmLenghtDecoder<T>(this.myLog,this.slot,this.scrtUtil));
        p.addLast(new YkwmLengthEncoder(this.myLog,this.scrtUtil));
        p.addLast(new YkwmPackConvInHandler<T>(this.myLog,this.slot,clazz));
        p.addLast(new YkwmPackConvOutHandler(this.myLog));
        p.addLast(new NettySyncHandler<T>(this.myLog,this.reqData));
    }

}