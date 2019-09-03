package com.fxbank.cap.ceba.trade.ceba;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fxbank.cap.ceba.dto.ceba.REQ_BJCEBOANotice;
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
public class OANotice_TEST extends BASE_TEST {
	
	private REQ_BJCEBOANotice req;
	
	@Reference(version = "1.0.0")
	private IWorkKeyService workKeyService;
	
	@Resource
	private LogPool logPool;
	
	@Before
	public void init(){
		req = new REQ_BJCEBOANotice();
		super.initReqHeader("BJCEBOANotice",req.getHead());
	}
	
	@Test
	public void ok() throws Exception {

		req.getTin().setDate("20110513000830");
		req.getTin().setFileName("OUTAGENOTICE_20110513_01.TXT");
		req.getTin().setNoticetype("0");
		req.getTin().setFiled1("");
		logPool.init(new MyLog());
		MyLog myLog = logPool.get();
		super.comm(req.creaFixPack(),logPool.get(),workKeyService);
	}
	
}
