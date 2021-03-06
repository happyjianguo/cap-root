package com.fxbank.cap.ceba.trade.esb;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alibaba.fastjson.JSON;
import com.fxbank.cap.ceba.dto.esb.REP_30062001001;
import com.fxbank.cap.ceba.dto.esb.REQ_30062001001;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.util.JsonUtil;



/** 
* @ClassName: CG_BillInfoTest 
* @Description: 缴费单销账测试 
* @作者 杜振铎
* @date 2019年5月10日 下午3:57:29 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc	
public class CG_BillInfoTest {
	
	private static Logger logger = LoggerFactory.getLogger(CG_BillInfoTest.class);
	
    private static final String URL="http://127.0.0.1:7012/esb/ceba.do";

	@Autowired
	private MockMvc mockMvc;
	
	@Resource
	private LogPool logPool;
	
	
	private REQ_30062001001 req ;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_30062001001.REQ_BODY reqBody ;
	
	@Before
	public void init(){
		req = new REQ_30062001001();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("300620010");
		reqSysHead.setSceneId("01");
		reqSysHead.setSystemId("301907");
		reqSysHead.setTranMode("ONLINE");
		reqSysHead.setSourceType("GJ");
		reqSysHead.setBranchId("02002");
		reqSysHead.setUserId("002241");
		reqSysHead.setTranDate(String.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date())));
		reqSysHead.setTranTimestamp(String.valueOf(new SimpleDateFormat("HHmmss").format(new Date())));		
		reqSysHead.setUserLang("CHINESE");
		reqSysHead.setSeqNo(String.valueOf(Math.abs(new Random().nextInt())));
		reqSysHead.setSystemId("301907");	
		reqSysHead.setCompany("COMPANY");
		reqSysHead.setSourceBranchNo("SOURCE_BRANCH_NO");
		reqSysHead.setDestBranchNo("DEST_BRANCH_NO");
		reqSysHead.setFilePath("FILE_PATH");
		reqSysHead.setGloabalSeqNo(reqSysHead.getSeqNo());
		reqSysHead.setAuthUserId("999");
		reqSysHead.setProgramId("7J13");
		reqBody = req.new REQ_BODY(); 
		req.setReqSysHead(reqSysHead);
		req.setReqBody(reqBody);
	}
	
	@Test
	public void payOk() throws Exception {
		logger.info("缴费单销账测试");
		//billKey等于12345时，报错,12346超时
		reqBody.setBillKey("0171793363");
		reqBody.setPyCityCode("414");
		reqBody.setPyCreditNo("001301");
		reqBody.setClientNnae("李萍(黎明7 2*1-8-23)");
		reqBody.setPayAcctNo("12009000094950");
		reqBody.setPassword("147258");
		reqBody.setUnpaidAmt("0.01");
		reqBody.setAcctType("1");
		reqBody.setContractNo("");
		
		String reqContent = JSON.toJSONString(req);
		logger.info("缴费单销账测试请求");
		RequestBuilder request = MockMvcRequestBuilders.post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		logger.info("缴费单销账测试请求完毕");
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
		String repContent = mvcResult.getResponse().getContentAsString();
		REP_30062001001 rep = JsonUtil.toBean(repContent, REP_30062001001.class);
		System.out.println(rep);
	}

}
