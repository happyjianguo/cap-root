package com.fxbank.cap.ceba.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30042000901 
* @Description: 柜面查询缴费单信息请求
* @作者 杜振铎
* @date 2019年5月7日 下午5:18:34 
*  
*/
public class REQ_30042000901 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30042000901(){
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

		@JSONField(name = "CITY_CODE")
		private String cityCode;
		
		@JSONField(name = "PROJ_CODE")
		private String projCode;
		
		@JSONField(name = "QUERY_NUM")
		private String queryNum;

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

		public String getCityCode() {
			return cityCode;
		}

		public void setCityCode(String cityCode) {
			this.cityCode = cityCode;
		}

		public String getProjCode() {
			return projCode;
		}

		public void setProjCode(String projCode) {
			this.projCode = projCode;
		}
		
	}
}
