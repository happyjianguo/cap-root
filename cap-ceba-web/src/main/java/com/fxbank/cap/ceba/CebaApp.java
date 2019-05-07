package com.fxbank.cap.ceba;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

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

	public static void main(String[] args) {
        SpringApplication.run(CebaApp.class, args);
    }
	
    @Override
    public void run(String... strings) {
    }

}
