package com.fxbank.cap.ceba.trade.ceba;

import com.fxbank.cap.ceba.dto.ceba.REQ_BJCEBBCNotify;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BCNotify_TEST extends BASE_TEST {
	
	private REQ_BJCEBBCNotify req;
	
	@Before
	public void init(){
		req = new REQ_BJCEBBCNotify();
		super.initReqHeader("BJCEBBCNotify",req.getHead());
	}
	
	@Test
	public void ok() throws Exception {

		req.getTin().setDate("20110513000830");
		req.getTin().setFileName("FXB_20110512_1.txt");
		req.getTin().setSignDate("20110512");
		req.getTin().setFiled1("");
		
		super.comm(req.creaFixPack());
	}
	
}
