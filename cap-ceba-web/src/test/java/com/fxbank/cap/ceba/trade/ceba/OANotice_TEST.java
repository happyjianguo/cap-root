package com.fxbank.cap.ceba.trade.ceba;

import com.fxbank.cap.ceba.dto.ceba.REQ_BJCEBOANotice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OANotice_TEST extends BASE_TEST {
	
	private REQ_BJCEBOANotice req;
	
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
		
		super.comm(req.creaFixPack());
	}
	
}
