package com.fxbank.cap.ceba.trade.ceba;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import com.fxbank.cap.ceba.model.BASE_HEAD;
import com.fxbank.cap.ceba.service.IWorkKeyService;
import com.fxbank.cip.base.log.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BASE_TEST {
    private static Logger logger = LoggerFactory.getLogger(BASE_TEST.class);

    private static final String IP = "127.0.0.1";
    private static final Integer PORT = 6012;
    private static final String CODING = "GBK";
    
    public String comm(String reqData,MyLog myLog,IWorkKeyService workKeyService) throws Exception {
        Socket socket = new Socket(BASE_TEST.IP, BASE_TEST.PORT);
        InputStream is = null;
        OutputStream os = null;
        String repData = null;
        try {
            reqData = reqData + workKeyService.genMac(myLog, reqData);
            os = socket.getOutputStream();
            String reqLen = String.format("%06d", reqData.getBytes(BASE_TEST.CODING).length);
            this.logger.info("发送请求报文[" + reqData + "]");
            os.write(reqLen.getBytes(BASE_TEST.CODING));
            os.write(reqData.getBytes(BASE_TEST.CODING));

            is = socket.getInputStream();
            byte[] lenByte = new byte[8];
            is.read(lenByte);
        } catch (Exception e) {
            this.logger.error("处理连接异常", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (os != null)
                    os.close();
                if (is != null)
                    is.close();
                if (socket != null)
                    socket.close();
            } catch (Exception e) {
                this.logger.error("关闭连接异常", e);
            } finally {
                this.logger.info("关闭连接");
            }
        }
        return repData;
    }

    public void initReqHeader(String ansTranCode,BASE_HEAD header){
        String sDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Integer seq = Math.abs(new Random().nextInt(100000000));
        header.setInstId("100000000000001");
        header.setAnsTranCode(ansTranCode);
        header.setTrmSeqNum("8690897");

    }

}