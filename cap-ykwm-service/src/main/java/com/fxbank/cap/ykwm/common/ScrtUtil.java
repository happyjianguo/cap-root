package com.fxbank.cap.ykwm.common;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.fxbank.cip.base.common.MyJedis;
import redis.clients.jedis.Jedis;

/** 
* @ClassName: ScrtUtil 
* @Description: DES加密
* @作者 杜振铎
* @date 2019年4月29日 下午2:17:32 
*  
*/
@Component
public class ScrtUtil {
	private static Logger logger = LoggerFactory.getLogger(ScrtUtil.class);
	
	private static final String COMMON_PREFIX = "ykwm.";
	private static final String DES3 = "DESede";
	private static final String DES = "DES";
	private static final int DES_LEN = 8;
	private static final int DES3_LEN = 24;
	
	@Resource
	private MyJedis myJedis;
	
	public void saveSessionKey(String sessionKey) throws Exception{
		try (Jedis jedis = myJedis.connect()) {
			jedis.set(COMMON_PREFIX+"ykwm_key", sessionKey);
		}
	}
	
	/** 
	* @Title: encrypt 
	* @Description: 加密
	* @param @param msg
	* @param @param key
	* @param @return
	* @param @throws Exception    设定文件 
	* @return byte[]    返回类型 
	* @throws 
	*/
	public String encrypt3DES(byte[] msg) throws Exception{
		byte[] retValue = null;
		try (Jedis jedis = myJedis.connect()) {
			String sessionKey = jedis.get(COMMON_PREFIX+"ykwm_key");
			SecretKey deskey =  new SecretKeySpec(get3DesKey(sessionKey), DES3);
			Cipher c1 = Cipher.getInstance(DES3);
			c1.init(1, deskey);
			retValue = c1.doFinal(msg);
		}
		return getHexString(retValue);
	}
	public String encryptDES(byte[] msg) throws Exception{
		byte[] retValue = null;
		try (Jedis jedis = myJedis.connect()) {
			String sessionKey = jedis.get(COMMON_PREFIX+"ykwm_key");
			SecretKey deskey =  new SecretKeySpec(get3DesKey(sessionKey), DES);
			Cipher c1 = Cipher.getInstance(DES);
			c1.init(1, deskey);
			retValue = c1.doFinal(msg);
		}
		return getHexString(retValue);
	}
	
	/**
	 * 生成24字节的3DES密钥。 （不够24字节，则补0；超过24字节，则取前24字节。）
	 * 
	 * @param key
	 *            密钥字符串
	 * @return
	 */
	public static byte[] get3DesKey(String key) {
		byte[] keyBytes = new byte[24];
		if (key.getBytes().length > DES3_LEN) {
			for (int i = 0; i < DES3_LEN; i++) {
				keyBytes[i] = key.getBytes()[i];
			}
		} else {
			for (int i = 0; i < DES3_LEN; i++) {
				if (i < key.getBytes().length) {
					keyBytes[i] = key.getBytes()[i];
				} else {
					keyBytes[i] = 0x00;
				}
			}
		}
		return keyBytes;
	}
	/**
	 * 生成8字节的DES密钥。 （不够8字节，则补0；超过8字节，则取前8字节。）
	 * 
	 * @param key
	 *            密钥字符串
	 * @return
	 */
	public static byte[] getDesKey(String key) {
		byte[] keyBytes = new byte[8];
		if (key.getBytes().length > DES_LEN) {
			for (int i = 0; i < DES_LEN; i++) {
				keyBytes[i] = key.getBytes()[i];
			}
		} else {
			for (int i = 0; i < DES_LEN; i++) {
				if (i < key.getBytes().length) {
					keyBytes[i] = key.getBytes()[i];
				} else {
					keyBytes[i] = 0x00;
				}
			}
		}
		return keyBytes;
	}
	/** 
	* @Title: decrypt 
	* @Description: 解密
	* @param @param msg
	* @param @param key
	* @param @return
	* @param @throws Exception    设定文件 
	* @return byte[]    返回类型 
	* @throws 
	*/
	public String decrypt3DES(byte[] msg) throws Exception{
		byte[] retValue = null;
		try (Jedis jedis = myJedis.connect()) {
			String sessionKey = jedis.get(COMMON_PREFIX+"ykwm_key");
			SecretKey deskey = new SecretKeySpec(get3DesKey(sessionKey), DES3);
			Cipher c1 = Cipher.getInstance(DES3);
			c1.init(2, deskey);
			retValue = c1.doFinal(msg);
		}
		return new String(retValue);
	}
	public String decryptDES(byte[] msg) throws Exception{
		byte[] retValue = null;
		try (Jedis jedis = myJedis.connect()) {
			String sessionKey = jedis.get(COMMON_PREFIX+"ykwm_key");
			SecretKey deskey = new SecretKeySpec(get3DesKey(sessionKey), DES);
			Cipher c1 = Cipher.getInstance(DES);
			c1.init(2, deskey);
			retValue = c1.doFinal(msg);
		}
		return new String(retValue);
	}
	
	/** 
	* @Title: getHexString 
	* @Description: 转换为16进制数
	* @param @param data
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public static String getHexString(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; ++i) {
			String ch = Integer.toHexString(data[i] & 0xFF).toUpperCase();
			if (ch.length() == 2) {
				sb.append(ch);
			} else {
				sb.append("0").append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * 将16进制的字符串转换为byte数组
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	public static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
