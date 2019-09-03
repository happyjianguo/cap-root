package com.fxbank.cap.ceba.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30063001401 
* @Description: 查询缴费单信息请求
* @作者 杜振铎
* @date 2019年5月7日 下午5:18:34 
*  
*/
public class REQ_30063001401 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30063001401(){
		super.txDesc = "查询缴费单信息";
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

		@JSONField(name = "BILL_KEY")
		private String billKey;		

		@JSONField(name = "PY_CITY_CODE")
		private String pyCityCode;
		
		@JSONField(name = "PY_CREDIT_NO")
		private String pyCreditNo;
		
		@JSONField(name = "START_NUM")
		private String startNum;
		
		@JSONField(name = "QUERY_NUM")
		private String queryNum;
		
		@JSONField(name = "RESERVE_FIELD1")
		private String reserveField1;
		
		@JSONField(name = "RESERVE_FIELD2")
		private String reserveField2;
		
		@JSONField(name = "RESERVE_FIELD3")
		private String reserveField3;
		
		@JSONField(name = "RESERVE_FIELD4")
		private String reserveField4;
		
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

		public String getBillKey() {
			return billKey;
		}

		public void setBillKey(String billKey) {
			this.billKey = billKey;
		}

		public String getQueryNum() {
			return queryNum;
		}

		public void setQueryNum(String queryNum) {
			this.queryNum = queryNum;
		}

		public String getPyCityCode() {
			return pyCityCode;
		}

		public void setPyCityCode(String pyCityCode) {
			this.pyCityCode = pyCityCode;
		}

		public String getPyCreditNo() {
			return pyCreditNo;
		}

		public void setPyCreditNo(String pyCreditNo) {
			this.pyCreditNo = pyCreditNo;
		}

		public String getStartNum() {
			return startNum;
		}

		public void setStartNum(String startNum) {
			this.startNum = startNum;
		}
		
	}
}
