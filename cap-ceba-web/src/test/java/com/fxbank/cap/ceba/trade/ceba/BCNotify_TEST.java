package com.fxbank.cap.ceba.trade.ceba;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.ceba.REQ_BJCEBBCNotify;
import com.fxbank.cap.ceba.service.IWorkKeyService;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.log.MyLog;
import javax.annotation.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BCNotify_TEST extends BASE_TEST {
	
	private REQ_BJCEBBCNotify req;
	
	@Reference(version = "1.0.0")
	private IWorkKeyService workKeyService;
	
	@Resource
	private LogPool logPool;
	
	@Before
	public void init(){
		req = new REQ_BJCEBBCNotify();
		super.initReqHeader("BJCEBBCNotify",req.getHead());
	}
	
	@Test
	public void ok() throws Exception {

		req.getTin().setDate("20190816000830");
		req.getTin().setFileName("FXB_20190815_1.txt");
		req.getTin().setSignDate("20190815");
		req.getTin().setFiled1("");
		MyLog myLog = new MyLog();
		logPool.init(myLog);
		super.comm(req.creaFixPack(),logPool.get(),workKeyService);
	}
	
}
