package com.fxbank.cap.ykwm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/** 
* @ClassName: YkwmApp 
* @Description: 启动器
* @author Duzhenduo
* @date 2019年4月29日 下午1:36:06 
*  
*/
@ServletComponentScan
@SpringBootApplication
@ComponentScan("com.fxbank")
public class YkwmApp implements CommandLineRunner {

	public static void main(String[] args) {
        SpringApplication.run(YkwmApp.class, args);
    }
	
    @Override
    public void run(String... strings) {
    }

}
