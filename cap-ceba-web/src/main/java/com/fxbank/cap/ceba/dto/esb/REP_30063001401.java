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
