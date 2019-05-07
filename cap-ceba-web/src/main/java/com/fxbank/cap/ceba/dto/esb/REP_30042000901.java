package com.fxbank.cap.ceba.dto.esb;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;


/** 
* @ClassName: REP_30042000901 
* @Description: 柜面查询缴费单应答 
* @作者 杜振铎
* @date 2019年5月7日 下午5:18:11 
*  
*/
public class REP_30042000901 extends REP_BASE {

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
		 @JSONField(name = "billKey")
	     private String billKey;
		 
		 @JSONField(name = "companyId")
	     private String companyId;
		 
		 @JSONField(name = "totalNum")
	     private String totalNum;
		 
		 @JSONField(name = "Data")
	     private List<DataInfo> dataArray;

		public String getBillKey() {
			return billKey;
		}

		public void setBillKey(String billKey) {
			this.billKey = billKey;
		}

		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}

		public String getTotalNum() {
			return totalNum;
		}

		public void setTotalNum(String totalNum) {
			this.totalNum = totalNum;
		}

		public List<DataInfo> getDataArray() {
			return dataArray;
		}

		public void setDataArray(List<DataInfo> dataArray) {
			this.dataArray = dataArray;
		}
		 
	     
	}
	public static class DataInfo implements Serializable{

		private static final long serialVersionUID = 4092345989534005224L;
		private String contractNo;
		private String customerName;
		private String balance;
		private String payAmount;
		private String beginDate;
		private String endDate;
		public String getContractNo() {
			return contractNo;
		}
		public void setContractNo(String contractNo) {
			this.contractNo = contractNo;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getBalance() {
			return balance;
		}
		public void setBalance(String balance) {
			this.balance = balance;
		}
		public String getPayAmount() {
			return payAmount;
		}
		public void setPayAmount(String payAmount) {
			this.payAmount = payAmount;
		}
		public String getBeginDate() {
			return beginDate;
		}
		public void setBeginDate(String beginDate) {
			this.beginDate = beginDate;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		
		 
	 }
}
