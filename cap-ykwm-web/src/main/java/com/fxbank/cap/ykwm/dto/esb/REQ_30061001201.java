package com.fxbank.cap.ykwm.dto.esb;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30061001201 
* @Description: 营口供热缴费请求
* @作者 杜振铎
* @date 2019年5月31日 下午2:28:00 
*  
*/
public class REQ_30061001201 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;

	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;

	@JSONField(name = "BODY")
	private REQ_BODY reqBody;

	public REQ_30061001201() {
		super.txDesc = "营口供热缴费";
	}

	public REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}

	public void setReqAppHead(REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}

	@Override
	public REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}

	@Override
	public void setReqSysHead(REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}

	public REQ_BODY getReqBody() {
		return reqBody;
	}

	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public class REQ_BODY {

		@JSONField(name = "ACCT_NO")
		private String acctNo;// 账号
		@JSONField(name = "PY_FEE_AMT_T")
		private String pyFeeAmtT;// 缴费金额
		@JSONField(name = "USER_DBT_AMT_T")
		private String userDbtAmtT;// 用户欠费金额
		@JSONField(name = "USER_CARD_NO_T")
		private String userCardNoT;// 用户卡号
		@JSONField(name = "PY_FEE_TP_T")
		private String pyFeeTpT;// 缴费方式
		@JSONField(name = "HEAT_COMPANY_ID_T")
		private String heatCompanyIdT;// 供暖公司ID
		@JSONField(name = "PASSWORD")
		private String password;// 密码
		@JSONField(name = "CHANNEL_REF_NO")
		private String channelRefNo;// 渠道业务流水号
		@JSONField(name = "BLL_QNTTY_T")
		private String bllQnttyT;// 票据张数
		@JSONField(name = "INVOICE_ARRAY")
		private List<Invoice> invoiceArray;// 发票数组

		public String getAcctNo() {
			return acctNo;
		}

		public void setAcctNo(String acctNo) {
			this.acctNo = acctNo;
		}

		public String getPyFeeAmtT() {
			return pyFeeAmtT;
		}

		public void setPyFeeAmtT(String pyFeeAmtT) {
			this.pyFeeAmtT = pyFeeAmtT;
		}

		public String getUserDbtAmtT() {
			return userDbtAmtT;
		}

		public void setUserDbtAmtT(String userDbtAmtT) {
			this.userDbtAmtT = userDbtAmtT;
		}

		public String getUserCardNoT() {
			return userCardNoT;
		}

		public void setUserCardNoT(String userCardNoT) {
			this.userCardNoT = userCardNoT;
		}

		public String getPyFeeTpT() {
			return pyFeeTpT;
		}

		public void setPyFeeTpT(String pyFeeTpT) {
			this.pyFeeTpT = pyFeeTpT;
		}

		public String getHeatCompanyIdT() {
			return heatCompanyIdT;
		}

		public void setHeatCompanyIdT(String heatCompanyIdT) {
			this.heatCompanyIdT = heatCompanyIdT;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getChannelRefNo() {
			return channelRefNo;
		}

		public void setChannelRefNo(String channelRefNo) {
			this.channelRefNo = channelRefNo;
		}

		public String getBllQnttyT() {
			return bllQnttyT;
		}

		public void setBllQnttyT(String bllQnttyT) {
			this.bllQnttyT = bllQnttyT;
		}

		public List<Invoice> getInvoiceArray() {
			return invoiceArray;
		}

		public void setInvoiceArray(List<Invoice> invoiceArray) {
			this.invoiceArray = invoiceArray;
		}

	}

	public static class Invoice {
		@JSONField(name = "INVC_NA_HD_T3")
		private String invcNaHdT3;// 发票名头
		@JSONField(name = "REIMBURSE_AREA_T")
		private String reimburseAreaT;// 报销面积
		@JSONField(name = "NAME")
		private String name;// 姓名
		@JSONField(name = "TXPYR_DIST_NO")
		private String txpyrDistNo;// 纳税人识别号
		@JSONField(name = "USER_ADDR")
		private String userAddr;// 用户地址
		@JSONField(name = "INVOICE_DEAL_MODE")
		private String invoiceDealMode;// 发票处理方式
		@JSONField(name = "OPN_ACCT_BNK_NO")
		private String opnAcctBnkNo;// 开户行行号

		public String getInvcNaHdT3() {
			return invcNaHdT3;
		}

		public void setInvcNaHdT3(String invcNaHdT3) {
			this.invcNaHdT3 = invcNaHdT3;
		}

		public String getReimburseAreaT() {
			return reimburseAreaT;
		}

		public void setReimburseAreaT(String reimburseAreaT) {
			this.reimburseAreaT = reimburseAreaT;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTxpyrDistNo() {
			return txpyrDistNo;
		}

		public void setTxpyrDistNo(String txpyrDistNo) {
			this.txpyrDistNo = txpyrDistNo;
		}

		public String getUserAddr() {
			return userAddr;
		}

		public void setUserAddr(String userAddr) {
			this.userAddr = userAddr;
		}

		public String getInvoiceDealMode() {
			return invoiceDealMode;
		}

		public void setInvoiceDealMode(String invoiceDealMode) {
			this.invoiceDealMode = invoiceDealMode;
		}

		public String getOpnAcctBnkNo() {
			return opnAcctBnkNo;
		}

		public void setOpnAcctBnkNo(String opnAcctBnkNo) {
			this.opnAcctBnkNo = opnAcctBnkNo;
		}
	}

}