package com.fxbank.cap.manager.quartz;

import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzJobConfigration {

	public static final String JOBGROUP = "cap";
	public static final String METHODNAME = "exec";
	public static final String PAF_CRON = "paf_cron.";
	public static final String CEBA_CRON = "ceba_cron.";
	public static final String YKWM_CRON = "ykwm_cron.";
	
}
