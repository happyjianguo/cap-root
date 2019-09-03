package com.fxbank.cap.ceba.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/**
 * @ClassName: REP_30063001402
 * @Description: 销账结果查询应答
 * @作者 杜振铎
 * @date 2019年5月10日 下午4:24:16
 * 
 */
public class REP_30063001402 extends REP_BASE {

	@JSONField(name = "APP_HEAD")
	private REP_APP_HEAD repAppHead = new REP_APP_HEAD();

	@JSONField(name = "SYS_HEAD")
	private REP_SYS_HEAD repSysHead = new REP_SYS_HEAD();

	@JSONField(name = "BODY")
	private REP_BODY repBody = new REP_BODY();

	public REP_APP_HEAD getRepAppHead() {
		return repAppHead;
	}

	public void setRepAppHead(REP_APP_HEAD repAppHead) {
		this.repAppHead = repAppHead;
	}

	@Override
	public REP_SYS_HEAD getRepSysHead() {
		return repSysHead;
	}

	@Override
	public void setRepSysHead(REP_SYS_HEAD repSysHead) {
		this.repSysHead = repSysHead;
	}

	public REP_BODY getRepBody() {
		return repBody;
	}

	public void setRepBody(REP_BODY repBody) {
		this.repBody = repBody;
	}

	public class REP_BODY {
		@JSONField(name = "BILL_KEY")
		private String billKey;

		@JSONField(name = "PLTF_SEQ_NO")
		private String pltfSeqNo;

		@JSONField(name = "UNPAID_AMT")
		private String unpaidAmt;

		@JSONField(name = "DEAL_STATUS")
		private String dealStatus;

		@JSONField(name = "RESERVE_FIELD1")
		private String reserveField1;

		@JSONField(name = "RESERVE_FIELD2")
		private String reserveField2;

		public String getReserveField1() {
			return reserveField1;
		}

		public void setReserveField1(String reserveField1) {
			this.reserveField1 = reserveField1;
		}

		public String getReserveField2() {
			return reserveField2;
		}

		public void setReserveField2(String reserveField2) {
			this.reserveField2 = reserveField2;
		}

		public String getReserveField3() {
			return reserveField3;
		}

		public void setReserveField3(String reserveField3) {
			this.reserveField3 = reserveField3;
		}

		public String getReserveField4() {
			return reserveField4;
		}

		public void setReserveField4(String reserveField4) {
			this.reserveField4 = reserveField4;
		}

		@JSONField(name = "RESERVE_FIELD3")
		private String reserveField3;

		@JSONField(name = "RESERVE_FIELD4")
		private String reserveField4;

		public String getBillKey() {
			return billKey;
		}

		public void setBillKey(String billKey) {
			this.billKey = billKey;
		}

		public String getPltfSeqNo() {
			return pltfSeqNo;
		}

		public void setPltfSeqNo(String pltfSeqNo) {
			this.pltfSeqNo = pltfSeqNo;
		}

		public String getUnpaidAmt() {
			return unpaidAmt;
		}

		public void setUnpaidAmt(String unpaidAmt) {
			this.unpaidAmt = unpaidAmt;
		}

		public String getDealStatus() {
			return dealStatus;
		}

		public void setDealStatus(String dealStatus) {
			this.dealStatus = dealStatus;
		}

	}
}
