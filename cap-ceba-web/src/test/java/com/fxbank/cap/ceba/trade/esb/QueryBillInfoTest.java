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
import com.fxbank.cap.ceba.dto.esb.REP_30042000901;
import com.fxbank.cap.ceba.dto.esb.REQ_30042000901;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.util.JsonUtil;


/** 
* @ClassName: QueryBillInfoTest 
* @Description: 柜面查询缴费单信息测试
* @作者 杜振铎
* @date 2019年5月7日 下午5:21:41 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc	
public class QueryBillInfoTest {
	
	private static Logger logger = LoggerFactory.getLogger(QueryBillInfoTest.class);
	
    private static final String URL="http://127.0.0.1:7000/esb/ceba.do";

	@Autowired
	private MockMvc mockMvc;
	
	@Resource
	private LogPool logPool;
	
	
	private REQ_30042000901 req ;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_30042000901.REQ_BODY reqBody ;
	
	@Before
	public void init(){
		req = new REQ_30042000901();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("300420009");
		reqSysHead.setSceneId("01");
		reqSysHead.setSystemId("301907");
		reqSysHead.setTranMode("ONLINE");
		reqSysHead.setSourceType("CEBA");	
//		reqSysHead.setSourceType("302200");
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
		reqBody = req.new REQ_BODY(); 
		req.setReqSysHead(reqSysHead);
		req.setReqBody(reqBody);
	}
	
	@Test
	public void payOk() throws Exception {
		logger.info("查询缴费单信息测试");
		//billKey等于12345时，报错
		reqBody.setBillKey("12345");
		reqBody.setCompanyId("654321");
		reqBody.setBeginNum("1");
		reqBody.setQueryNum("1");
		
		String reqContent = JSON.toJSONString(req);
		logger.info("查询缴费单信息测试请求");
		RequestBuilder request = MockMvcRequestBuilders.post(URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		logger.info("查询缴费单信息测试请求完毕");
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
		String repContent = mvcResult.getResponse().getContentAsString();
		REP_30042000901 rep = JsonUtil.toBean(repContent, REP_30042000901.class);
		System.out.println(rep);
	}

}
