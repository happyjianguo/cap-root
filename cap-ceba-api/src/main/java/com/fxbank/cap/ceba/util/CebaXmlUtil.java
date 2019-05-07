package com.fxbank.cap.ceba.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/** 
* @ClassName: CebaXmlUtil 
* @Description: XML Bean转换
* @作者 杜振铎
* @date 2019年5月7日 下午5:06:09 
*  
*/
public class CebaXmlUtil {

	public static String objectToXml(Object object) {
		try {
			StringWriter writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshal = context.createMarshaller();
			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); 
			marshal.setProperty(Marshaller.JAXB_ENCODING, "GBK");
			marshal.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshal.marshal(object, writer);
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
			sb.append(writer.getBuffer());
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T xmlToObject(Class<T> clazz, String xml){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			SAXParserFactory sax = SAXParserFactory.newInstance();
			sax.setNamespaceAware(false);
			XMLReader xmlReader = sax.newSAXParser().getXMLReader();
			Source source = new SAXSource(xmlReader, new InputSource(reader));
			return (T) unmarshaller.unmarshal(source);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
