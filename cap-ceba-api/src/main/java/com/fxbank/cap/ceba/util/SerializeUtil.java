package com.fxbank.cap.ceba.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** 
* @ClassName: SerializeUtil 
* @Description: 序列化反序列化工具 
* @作者 杜振铎
* @date 2019年5月7日 下午5:09:29 
*  
*/
public class SerializeUtil {

    /**
     * @Description: 序列化
     * @Author: 周勇沩
     * @Date: 2019-04-23 20:35:48
     */
    public static byte[] serialize(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 反序列化
     * @Author: 周勇沩
     * @Date: 2019-04-23 20:35:56
     */
    public static Object unserialize(byte[] bytes) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}