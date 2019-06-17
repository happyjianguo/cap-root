package com.fxbank.cap.ykwm.trade.esb;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fxbank.cap.ykwm.common.ScrtUtil;

/** 
* @ClassName: BASE_TEST 
* @Description: 报文测试 
* @作者 杜振铎
* @date 2019年4月29日 下午2:15:34 
*  
*/
public class BASE_TEST {
    private static Logger logger = LoggerFactory.getLogger(BASE_TEST.class);

    private static final String IP = "127.0.0.1";
    private static final Integer PORT = 6006;
    private static final String CODING = "GBK";
    
	@Resource
    private ScrtUtil scrtUtil;

    /** 
    * @Title: comm 
    * @Description: 模拟报文
    * @param @param reqData
    * @param @return
    * @param @throws Exception    设定文件 
    * @return String    返回类型 
    * @throws 
    */
    public String comm(String reqData) throws Exception {
        Socket socket = new Socket(BASE_TEST.IP, BASE_TEST.PORT);
        InputStream is = null;
        OutputStream os = null;
        String repData = null;
        try {
            os = socket.getOutputStream();
            //byte[] encryptMsg = scrtUtil.encrypt3DES(reqData.getBytes(BASE_TEST.CODING)).getBytes(BASE_TEST.CODING);
            byte[] encryptMsg = reqData.getBytes(BASE_TEST.CODING);
            int len = encryptMsg.length;
            logger.info("发送请求报文长度["+len+"]");
           // String reqLen = String.format("%04d", len);
            logger.info("发送请求报文密文十六进制[" + reqData + "]");
          //  os.write(reqLen.getBytes(BASE_TEST.CODING));
            os.write(encryptMsg);

            is = socket.getInputStream();
            byte[] lenByte = new byte[4];
            is.read(lenByte);
            String slen = new String(lenByte);
            int retLen = Integer.valueOf(slen);
            byte[] dataByte = new byte[retLen];
            is.read(dataByte);
            //repData = scrtUtil.decrypt3DES(dataByte);
            repData = new String(dataByte);
            logger.info("接收应答报文[" + repData + "]");
        } catch (Exception e) {
           logger.error("处理连接异常", e);
            throw new RuntimeException(e);
        } finally {
            try {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
				if (socket != null) {
					socket.close();
				}
            } catch (Exception e) {
                this.logger.error("关闭连接异常", e);
            } finally {
                this.logger.info("关闭连接");
            }
        }
        return repData;
    }

    public void initReqHeader(String tTxnNm){
        String sDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Integer seq = Math.abs(new Random().nextInt(100000000));
        //header.settTxnNm(tTxnNm);
       
    }

}