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

import com.alibaba.fastjson.JSON;
import com.fxbank.cap.ykwm.dto.esb.REP_30012002001;
import com.fxbank.cap.ykwm.dto.esb.REP_30012002002;
import com.fxbank.cap.ykwm.dto.esb.REQ_30012002001;
import com.fxbank.cap.ykwm.dto.esb.REQ_30012002002;
import com.fxbank.cap.ykwm.dto.esb.REQ_30012002002.INVOICE;
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

	private REQ_30012002002 req;
	private REQ_SYS_HEAD reqSysHead;
	private REQ_30012002002.REQ_BODY reqBody;

	@Before
	public void init() {
		req = new REQ_30012002002();
		reqSysHead = new REQ_SYS_HEAD();
		reqSysHead.setServiceId("300120020");
		reqSysHead.setSceneId("02");
		reqSysHead.setSystemId("301907");
		reqSysHead.setTranMode("ONLINE");
		
		reqSysHead.setSourceType("YKWM");
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

		reqBody.setBatchNum("01000009");// 阜新银行网店编号
		// 用户卡号必须为数字，否则报错
		reqBody.setBatchNum("20190510");// 批次号 8位
		
		reqBody.setAcctNoT("623166001015670786");//kahao 
		reqBody.setCheckNoT("201905230004");// 交易流水号 16位
		reqBody.setPyFeeAmtT("390");// 缴费金额f
		List<INVOICE> list = new ArrayList<INVOICE>();
		INVOICE invoice = new INVOICE();
		invoice.setBillGetTpT("3");
		invoice.setInvcNaHdT3("阜新银行股份有限公司");// 发票抬头
		invoice.setReimburseAreaT("300");// 发票面积
		invoice.setNaT1("红牛阳123");// 发票姓名
		// reqBody.setInvoiceNum1("5667788");// 纳税人识别号
		// reqBody.setbankNm("100");// 开户行 行号 从头取
		invoice.setUserAddrT("火星");// 发票地址
		list.add(invoice);
		reqBody.setInvoiceList(list);
		
		reqBody.setPyFeeTpT("1");

		reqBody.setCourierCmpnyIdT("115566");// 快递公司ID
		reqBody.setMailAddrT("辽宁省沈阳市铁西区兴华街20号");// 邮寄地址
		reqBody.setCnttPhnT("18240370382");// 联系电话
		reqBody.setLnmT3("红牛阳测试123");// 联系人
		reqBody.setPostNoT5("110035");// 邮编
		reqBody.setInvoiceCountT("2");// 开票张数
		

		String reqContent = JSON.toJSONString(req);
		logger.info("缴费测试请求");
		RequestBuilder request = MockMvcRequestBuilders.post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(reqContent);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		logger.info("缴费测试请求完毕");
		int status = mvcResult.getResponse().getStatus();
		assertEquals(status, 200);
		String repContent = mvcResult.getResponse().getContentAsString();
		REP_30012002002 rep = JsonUtil.toBean(repContent, REP_30012002002.class);
	}

}
