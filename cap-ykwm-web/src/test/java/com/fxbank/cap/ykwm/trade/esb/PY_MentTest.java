package com.fxbank.cap.ykwm.trade.esb;

import static org.junit.Assert.assertEquals;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.fxbank.cap.esb.service.ISafeService;
import com.fxbank.cap.ykwm.dto.esb.REP_30061001201;
import com.fxbank.cap.ykwm.dto.esb.REQ_30061001201;
import com.fxbank.cap.ykwm.dto.esb.REQ_30061001201.Invoice;
import com.fxbank.cip.base.common.LogPool;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;
import com.fxbank.cip.base.util.JsonUtil;

/**
 * @ClassName: QR_AccountTest
 * @Description: 柜面欠费查询模拟
 * @作者 杜振铎
 * @date 2019年4月29日 下午2:14:18
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PY_MentTest {

	private static Logger logger = LoggerFactory.getLogger(QR_AccountTest.class);

	 private static final String URL = "http://127.0.0.1:7006/esb/ykwm.do";
	//private static final String URL = "http://157.25.3.164:7006/esb/ykwm.do";

	@Autowired
	private MockMvc mockMvc;

	@Resource
	private LogPool logPool;
	
	@Reference(version = "1.0.0")
	private ISafeService passwordService;

	private REQ_30061001201 req;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_30061001201.REQ_BODY reqBody;

	@Before
	public void init() {
		req = new REQ_30061001201();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("300610012");
		reqSysHead.setSceneId("01");
		reqSysHead.setSystemId("301907");
		reqSysHead.setTranMode("ONLINE");
		
		reqSysHead.setSourceType("BH");
		reqSysHead.setBranchId("01002");
		reqSysHead.setUserId("001068");
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
		logger.info("缴费测试");

		reqBody.setAcctNo("12009000094950");// 账号
		reqBody.setPyFeeAmtT("55.55");//缴费金额
		reqBody.setUserDbtAmtT("100");//用户欠费金额
		reqBody.setUserCardNoT("11111");//用户卡号
		reqBody.setPyFeeTpT("2");//缴费方式
		reqBody.setHeatCompanyIdT("2222");//供暖公司ID
		reqBody.setPassword("147258");//密码
		//TODO 不是数字 报错
		reqBody.setChannelRefNo("44444");//查询流水号
		List<Invoice> list = new ArrayList<Invoice>();
		Invoice invoice = new Invoice();
		invoice.setInvcNaHdT3("阜新银行股份有限公司1");// 发票抬头
		invoice.setInvoiceDealMode("1");
		invoice.setReimburseAreaT("100");// 发票面积
		invoice.setName("红牛阳1");// 发票姓名
		invoice.setTxpyrDistNo("1111");// 纳税人识别号
		invoice.setOpnAcctBnkNo("100");// 开户行 行号 从头取
		invoice.setUserAddr("火星1");// 发票地址
		list.add(invoice);
		Invoice invoice1 = new Invoice();
		invoice1.setInvcNaHdT3("阜新银行股份有限公司2");// 发票抬头
		invoice1.setInvoiceDealMode("1");
		invoice1.setReimburseAreaT("200");// 发票面积
		invoice1.setName("红牛阳2");// 发票姓名
		invoice1.setTxpyrDistNo("22222");// 纳税人识别号
		invoice1.setOpnAcctBnkNo("200");// 开户行 行号 从头取
		invoice1.setUserAddr("火星2");// 发票地址
		list.add(invoice1);
		Invoice invoice2 = new Invoice();
		invoice2.setInvcNaHdT3("阜新银行股份有限公司3");// 发票抬头
		invoice2.setInvoiceDealMode("1");
		invoice2.setReimburseAreaT("300");// 发票面积
		invoice2.setName("红牛阳3");// 发票姓名
		invoice2.setTxpyrDistNo("3333");// 纳税人识别号
		invoice2.setOpnAcctBnkNo("300");// 开户行 行号 从头取
		invoice2.setUserAddr("火星3");// 发票地址
		list.add(invoice2);
		Invoice invoice3 = new Invoice();
		invoice3.setInvcNaHdT3("阜新银行股份有限公司4");// 发票抬头
		invoice3.setInvoiceDealMode("1");
		invoice3.setReimburseAreaT("400");// 发票面积
		invoice3.setName("红牛阳4");// 发票姓名
		invoice3.setTxpyrDistNo("44444");// 纳税人识别号
		invoice3.setOpnAcctBnkNo("400");// 开户行 行号 从头取
		invoice3.setUserAddr("火星4");// 发票地址
		list.add(invoice3);
		reqBody.setInvoiceArray(list);
        reqBody.setBllQnttyT(String.valueOf(list.size()));		
		
		String macDataStr = JsonUtil.toJson(reqBody);
		byte[] macBytes = macDataStr.getBytes();
		String macValue = passwordService.calcMac(logPool.get(), macBytes);
		reqSysHead.setMacValue(macValue);
		
		
		String reqContent = JSON.toJSONString(req);
		logger.info("缴费测试请求");
		RequestBuilder request = MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		logger.info("缴费测试请求完毕");
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
		String repContent = mvcResult.getResponse().getContentAsString();
		REP_30061001201 rep = JsonUtil.toBean(repContent, REP_30061001201.class);
	}

}
