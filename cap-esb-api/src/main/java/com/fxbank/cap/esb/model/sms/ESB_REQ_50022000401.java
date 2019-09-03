package com.fxbank.cap.esb.model.sms;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ESB_BASE;
import com.fxbank.cip.base.model.ESB_REQ_APP_HEAD;
import com.fxbank.cip.base.model.ESB_REQ_SYS_HEAD;

public class ESB_REQ_50022000401 extends ESB_BASE {

	private static final long serialVersionUID = 6710338263153316861L;

	public ESB_REQ_50022000401(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	@JSONField(name = "APP_HEAD")
	private ESB_REQ_APP_HEAD reqAppHead = new ESB_REQ_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private ESB_REQ_SYS_HEAD reqSysHead = new ESB_REQ_SYS_HEAD("500220004", "01");
	@JSONField(name = "BODY")
	private REQ_BODY reqBody = new REQ_BODY();

	public class REQ_BODY implements Serializable {

		private static final long serialVersionUID = 1249043215679255222L;
		@JSONField(name = "SEND_SOURCE_TYPE")
		private String sendtype;//发送渠道
		@JSONField(name = "TEMPLATE_CODE")
		private String mid;//模板ID
		@JSONField(name = "PROD_TYPE")
		private String packet;//报文代码
		@JSONField(name = "SERVICE_CLS")
		private String srvid;//服务品种
		@JSONField(name = "ACPT_SYS_CODE")
		private String channo;//系统编号
		@JSONField(name = "PROGRAM_ID")
		private String transcode;//交易码
		@JSONField(name = "APPLY_FLAG")
		private String immediaflag;//实时标志
		@JSONField(name = "APPLY_TIME")
		private String sendtime;//预约时间
		@JSONField(name = "CUP_SEND_CODE")
		private String branchno;//交易机构号
		@JSONField(name = "MOBILE_PHONE")
		private String objaddr;//手机号
		@JSONField(name = "AGREEMENT_ID")
		private String regNo;//手机签约编号
		@JSONField(name = "BANK_ACCT_NO")
		private String acconum;//银行账号
		@JSONField(name = "WECHAT_PROGRAM_ID")
		private String wxcode;//微信交易码
		@JSONField(name = "TEMPLATE_DATA")
		private String data;//模板数据

		public String getSendtype() {
			return sendtype;
		}

		public void setSendtype(String sendtype) {
			this.sendtype = sendtype;
		}

		public String getMid() {
			return mid;
		}

		public void setMid(String mid) {
			this.mid = mid;
		}

		public String getPacket() {
			return packet;
		}

		public void setPacket(String packet) {
			this.packet = packet;
		}

		public String getSrvid() {
			return srvid;
		}

		public void setSrvid(String srvid) {
			this.srvid = srvid;
		}

		public String getChanno() {
			return channo;
		}

		public void setChanno(String channo) {
			this.channo = channo;
		}

		public String getTranscode() {
			return transcode;
		}

		public void setTranscode(String transcode) {
			this.transcode = transcode;
		}

		public String getImmediaflag() {
			return immediaflag;
		}

		public void setImmediaflag(String immediaflag) {
			this.immediaflag = immediaflag;
		}

		public String getSendtime() {
			return sendtime;
		}

		public void setSendtime(String sendtime) {
			this.sendtime = sendtime;
		}

		public String getBranchno() {
			return branchno;
		}

		public void setBranchno(String branchno) {
			this.branchno = branchno;
		}

		public String getObjaddr() {
			return objaddr;
		}

		public void setObjaddr(String objaddr) {
			this.objaddr = objaddr;
		}

		public String getRegNo() {
			return regNo;
		}

		public void setRegNo(String regNo) {
			this.regNo = regNo;
		}

		public String getAcconum() {
			return acconum;
		}

		public void setAcconum(String acconum) {
			this.acconum = acconum;
		}

		public String getWxcode() {
			return wxcode;
		}

		public void setWxcode(String wxcode) {
			this.wxcode = wxcode;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

	}

	public REQ_BODY getReqBody() {
		return reqBody;
	}

	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

	public ESB_REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}

	public void setReqAppHead(ESB_REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}

	public ESB_REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}

	public void setReqSysHead(ESB_REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}

}
