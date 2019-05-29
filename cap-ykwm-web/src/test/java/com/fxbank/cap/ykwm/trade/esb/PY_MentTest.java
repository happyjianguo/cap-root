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
		
		reqSysHead.setSourceType("MT");
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

		reqBody.setAcctNoT("623166001015670786");// 账号
		reqBody.setPyFeeAmtT("55.55");//缴费金额
		reqBody.setUserDbtAmtT("100");//用户欠费金额
		reqBody.setCourierAmtT("10");//快递金额
		reqBody.setUserCardNoT("11111");//用户卡号
		reqBody.setCnttPhnT("18210224456");//联系电话
		reqBody.setLnmT3("张三");//联系人
		reqBody.setPyFeeTpT("1");//缴费方式
		reqBody.setReimburseSignT("A");//报销标志
		reqBody.setHeatCompanyIdT("2222");//供暖公司ID
		reqBody.setMailAddrT("沈阳市");//邮寄地址
		reqBody.setPwdT("147258");//密码
		reqBody.setHeatCompanyNmT("热电公司");//供暖公司名
		reqBody.setPostNoT5("111000");//邮编
		reqBody.setCourierCmpnyIdT("333333");//快递公司ID
		//TODO 不是数字 报错
		reqBody.setCheckNoT("44444");//查询流水号
		reqBody.setBillGetTpT("1");//发票处理方式，0未选择，1邮寄，2自取，3电子发票
		List<INVOICE> list = new ArrayList<INVOICE>();
		INVOICE invoice = new INVOICE();
		invoice.setInvcNaHdT3("阜新银行股份有限公司");// 发票抬头
		invoice.setReimburseAreaT("300");// 发票面积
		invoice.setNaT1("红牛阳123");// 发票姓名
		invoice.setInvoiceNumT("5667788");// 纳税人识别号
		invoice.setBankNumT("100");// 开户行 行号 从头取
		invoice.setUserAddrT("火星");// 发票地址
		list.add(invoice);
		reqBody.setInvoiceArray(list);
        reqBody.setInvoiceCountT(String.valueOf(list.size()));		
		

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
