package com.fxbank.cap.ykwm.simu;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.fxbank.cap.ykwm.model.REP_Query;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: SimuYkwmServer 
* @Description: 营口热电仿真
* @作者 杜振铎
* @date 2019年4月29日 下午2:26:15 
*  
*/
public class SimuYkwmServer {

    public static final String CODING = "UTF-8";
    public static final Integer PORT = 6003;

    public static void main(String[] args) throws Exception {
        new Work().listen(SimuYkwmServer.PORT);
    }

}

class Work {

    private static Logger logger = LoggerFactory.getLogger(Work.class);

    public String listen(Integer port) throws Exception {
		ServerSocket ss = new ServerSocket(port);
		while (true) {
			this.logger.info("监听端口" + port + "，等待连接...");
			ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 60, TimeUnit.SECONDS,
					new ArrayBlockingQueue<Runnable>(3) 
					, Executors.defaultThreadFactory());
			pool.execute(new Run(ss.accept()));
        }
    }
}

class Run implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(Run.class);

    private Socket socket;

    public Run(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = this.socket.getInputStream();
            byte[] lenByte = new byte[8];
            is.read(lenByte);
            String slen = new String(lenByte);
            int len = Integer.valueOf(slen);
            byte[] dataByte = new byte[len];
            is.read(dataByte);
            String data = new String(dataByte, SimuYkwmServer.CODING);
            this.logger.info("接收请求报文[" + data + "]");

            REP_Query rep = new REP_Query(new MyLog(), 20190909, 125609, 1);
            rep.getHeader().setResult("0");
            rep.setOwnerName("张三");
            String repData = rep.creaFixPack();
            repData = repData + "FFFFFFFFFFFFFFFF";
            os = socket.getOutputStream();
            String repLen = String.format("%08d", repData.getBytes(SimuYkwmServer.CODING).length);
            this.logger.info("发送应答报文[" + repData + "]");
            os.write(repLen.getBytes(SimuYkwmServer.CODING));
            os.write(repData.getBytes(SimuYkwmServer.CODING));
        } catch (Exception e) {
            this.logger.error("处理连接异常", e);
            // throw new RuntimeException(e);
        } finally {
            try {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
				if (this.socket != null) {
					this.socket.close();
				}
            } catch (Exception e) {
                this.logger.error("关闭连接异常", e);
            } finally {
                this.logger.info("关闭连接");
            }
        }
    }
}