package com.fxbank.cap.ceba;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import com.fxbank.cip.base.common.MyJedis;

import cebenc.softenc.SoftEnc;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: CebaApp
 * @Description: 启动器
 * @作者 杜振铎
 * @date 2019年5月7日 下午5:22:20
 * 
 */
@ServletComponentScan
@SpringBootApplication
@ComponentScan("com.fxbank")
public class CebaApp implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(CebaApp.class);
	public static SoftEnc softEnc = new SoftEnc();
	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "ceba.";

	public static void main(String[] args) {
		SpringApplication.run(CebaApp.class, args);
	}

	@Override
	public void run(String... strings) {
		String keyPath = "";
		try (Jedis jedis = myJedis.connect()) {
			keyPath = jedis.get(COMMON_PREFIX + "key_path");
			softEnc.Init(keyPath);
		} catch (Exception e) {
			logger.error("加密配置环境初始化失败" + e);
		}
	}

}
