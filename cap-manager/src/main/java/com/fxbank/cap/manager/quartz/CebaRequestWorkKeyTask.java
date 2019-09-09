package com.fxbank.cap.manager.quartz;

import java.net.SocketTimeoutException;
import javax.annotation.Resource;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.service.IForwardToCebaService;
import com.fxbank.cap.ceba.service.IWorkKeyService;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.pub.service.IPublicService;
import redis.clients.jedis.Jedis;

@Configuration
@Component
@EnableScheduling
public class CebaRequestWorkKeyTask {
	private static Logger logger = LoggerFactory.getLogger(CebaRequestWorkKeyTask.class);

	private static final String JOBNAME = "CebaRequestWorkKey";

	@Reference(version = "1.0.0")
	private IPublicService publicService;

	@Reference(version = "1.0.0")
	private IForwardToCebaService forwardToCebaService;
	
	@Reference(version = "1.0.0", cluster="broadcast")
	private IWorkKeyService workKeyService;

	@Resource
	private MyJedis myJedis;

	private final static String COMMON_PREFIX = "ceba.";

	public void exec() throws Exception {
		MyLog myLog = new MyLog();
		String ipArray = null;
		try (Jedis jedis = myJedis.connect()) {
			ipArray = jedis.get(COMMON_PREFIX + "work_key_ip");
		}
		for(String ip:ipArray.split("\\|")) {
			sendRwk(myLog,ip);
		}
	}
	
	private void sendRwk(MyLog myLog,String ip) {
		try {
			HttpPost httpPost = new HttpPost("http://"+ip+":8004/cap/ceba/test");
			httpPost.setHeader("Content-Type", "application/json");
			StringEntity se = new StringEntity("{\"BODY\":{},\"SYS_HEAD\":{\"SCENE_ID\":\"01\",\"SERVICE_ID\":\"300630014\"}}", "utf-8");
			httpPost.setEntity(se);
			CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
			closeableHttpClient.execute(httpPost);
			closeableHttpClient.close();
		} catch (SocketTimeoutException e) {
			myLog.error(logger,"工作密钥申请请求响应超时",e);
		} catch (Exception e) {
			myLog.error(logger,"工作密钥申请请求失败",e);
		}
	}

	@Bean(name = "cebaRequestWorkKeyJobDetail")
	public MethodInvokingJobDetailFactoryBean cebaRequestWorkKeyJobDetail(CebaRequestWorkKeyTask task) {// ScheduleTask为需要执行的任务
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false);
		jobDetail.setName(JOBNAME);
		jobDetail.setGroup(QuartzJobConfigration.JOBGROUP);
		jobDetail.setTargetObject(task);
		jobDetail.setTargetMethod(QuartzJobConfigration.METHODNAME);
		return jobDetail;
	}

	@Bean(name = "cebaRequestWorkKeyJobTrigger")
	public CronTriggerFactoryBean cebaRequestWorkKeyJobTrigger(
			@Qualifier("cebaRequestWorkKeyJobDetail") MethodInvokingJobDetailFactoryBean jobDetail) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(jobDetail.getObject());
		String exp = null;
		try (Jedis jedis = myJedis.connect()) {
			exp = jedis.get(QuartzJobConfigration.CEBA_CRON + JOBNAME);
		}
		if (exp == null) {
			exp = "0 0 7 ? * *";
		}
		logger.info("任务[" + JOBNAME + "]启动[" + exp + "]");
		tigger.setCronExpression(exp);
		tigger.setName(JOBNAME);
		return tigger;
	}

	@Bean(name = "cebaRequestWorkKeyScheduler")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("cebaRequestWorkKeyJobTrigger") Trigger cronJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(5);
		bean.setTriggers(cronJobTrigger);
		return bean;
	}
}
