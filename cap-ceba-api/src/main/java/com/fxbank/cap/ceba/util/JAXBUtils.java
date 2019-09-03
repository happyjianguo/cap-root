package com.fxbank.cap.ceba.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/** 
* @ClassName: JAXBUtils 
* @Description: xml bean转换 
* @作者 杜振铎
* @date 2019年5月7日 下午5:09:10 
*  
*/
public class JAXBUtils {
    /**
     * 获取JAXBContext实例。
     * @param clazz
     * @return
     */
    private static final JAXBContext getJAXBContext(Class<?> c){
        JAXBContext jaxbContext=null;
        try {
            jaxbContext = JAXBContext.newInstance(c);
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jaxbContext;
    }

    /**
     * 生成xml文件的二进制数据
     * @param obj 对象
     */
    public static byte[] marshal(Object obj) throws JAXBException {
        JAXBContext context = getJAXBContext(obj.getClass());
        Marshaller m = context.createMarshaller();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
        m.marshal(obj, outputStream);
        byte[] result = outputStream.toByteArray();
        return result;
    }
    /**
     * @param data xml stream
     * @param classe 类
     * @return jaxb生成xml的java 类对象
     */
    public static Object unmarshal(byte[] data, Class<?> classe) throws JAXBException {
    	JAXBContext context = getJAXBContext(classe);
        Unmarshaller m = context.createUnmarshaller();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        Object obj = m.unmarshal(inputStream);
        return obj;
    }
    
}