package com.fxbank.cap.ceba.dto.esb;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/**
 * @ClassName: REP_30063001401
 * @Description: 查询缴费单应答
 * @作者 杜振铎
 * @date 2019年5月7日 下午5:18:11
 * 
 */
public class REP_30063001401 extends REP_BASE {

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
		@JSONField(name = "TOTAL_NUM")
		private String totalNum;

		@JSONField(name = "RESERVE_FIELD1")
		private String reserveField1;

		@JSONField(name = "RESERVE_FIELD2")
		private String reserveField2;

		@JSONField(name = "RESERVE_FIELD3")
		private String reserveField3;

		@JSONField(name = "RESERVE_FIELD4")
		private String reserveField4;

		@JSONField(name = "RESERVE_FIELD5")
		private String reserveField5;

		@JSONField(name = "RESERVE_FIELD6")
		private String reserveField6;

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

		public String getReserveField5() {
			return reserveField5;
		}

		public void setReserveField5(String reserveField5) {
			this.reserveField5 = reserveField5;
		}

		public String getReserveField6() {
			return reserveField6;
		}

		public void setReserveField6(String reserveField6) {
			this.reserveField6 = reserveField6;
		}

		public String getReserveField7() {
			return reserveField7;
		}

		public void setReserveField7(String reserveField7) {
			this.reserveField7 = reserveField7;
		}

		@JSONField(name = "RESERVE_FIELD7")
		private String reserveField7;

		@JSONField(name = "PY_INFO_ARRAY")
		private List<DataInfo> pyInfoArray;

		public String getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(String totalNum) {
			this.totalNum = totalNum;
		}

		public List<DataInfo> getPyInfoArray() {
			return pyInfoArray;
		}

		public void setPyInfoArray(List<DataInfo> pyInfoArray) {
			this.pyInfoArray = pyInfoArray;
		}

	}

	public static class DataInfo {
		@JSONField(name = "CONTRACT_NO")
		private String contractNo;
		@JSONField(name = "CLIENT_NNAE")
		private String clientNnae;
		@JSONField(name = "BALANCE")
		private String balance;
		@JSONField(name = "UNPAID_AMT")
		private String unpaidAmt;
		@JSONField(name = "START_DATE")
		private String startDate;
		@JSONField(name = "END_DATE")
		private String endDate;
		@JSONField(name = "RESERVE_FIELD1")
		private String reserveField1;
		@JSONField(name = "RESERVE_FIELD2")
		private String reserveField2;
		@JSONField(name = "RESERVE_FIELD3")
		private String reserveField3;
		@JSONField(name = "RESERVE_FIELD4")
		private String reserveField4;
		@JSONField(name = "RESERVE_FIELD5")
		private String reserveField5;

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

		public String getReserveField5() {
			return reserveField5;
		}

		public void setReserveField5(String reserveField5) {
			this.reserveField5 = reserveField5;
		}

		public String getContractNo() {
			return contractNo;
		}

		public void setContractNo(String contractNo) {
			this.contractNo = contractNo;
		}

		public String getClientNnae() {
			return clientNnae;
		}

		public void setClientNnae(String clientNnae) {
			this.clientNnae = clientNnae;
		}

		public String getBalance() {
			return balance;
		}

		public void setBalance(String balance) {
			this.balance = balance;
		}

		public String getUnpaidAmt() {
			return unpaidAmt;
		}

		public void setUnpaidAmt(String unpaidAmt) {
			this.unpaidAmt = unpaidAmt;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

	}
}
