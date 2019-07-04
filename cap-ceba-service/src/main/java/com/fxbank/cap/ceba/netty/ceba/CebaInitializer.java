package com.fxbank.cap.ceba.netty.ceba;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.netty.NettySyncHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/** 
* @ClassName: CebaInitializer 
* @Description: 光大银行客户端通讯初始化 
* @作者 杜振铎
* @date 2019年5月7日 下午5:07:51 
* 
* @param <T> 
*/
public class CebaInitializer<T> extends ChannelInitializer<SocketChannel> {

    private MyLog myLog;

    public CebaInitializer(MyLog myLog) {
        this.myLog = myLog;
    }
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
       // p.addLast(new CebaLenghtDecoder<T>(this.myLog));
        p.addLast(new CebaLengthEncoder(this.myLog));
        //p.addLast(new CebaPackConvInHandler<T>(this.myLog));
        p.addLast(new CebaPackConvOutHandler(this.myLog));
       // p.addLast(new NettySyncHandler<T>(this.myLog,reqData));
    }

}