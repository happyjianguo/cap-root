package com.fxbank.cap.ceba.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30063001403 
* @Description: 收费单位查询请求
* @作者 杜振铎
* @date 2019年5月7日 下午5:18:34 
*  
*/
public class REQ_30063001403 extends REQ_BASE {
	
	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;
	
	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;
	
	@JSONField(name = "BODY")
	private REQ_BODY reqBody;
	
	public REQ_30063001403(){
		super.txDesc = "收费单位查询";
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

		@JSONField(name = "PY_CITY_CODE")
		private String pyCityCode;		

		@JSONField(name = "PY_CREDIT_TYPE")
		private String pyCreditType;

		public String getPyCityCode() {
			return pyCityCode;
		}

		public void setPyCityCode(String pyCityCode) {
			this.pyCityCode = pyCityCode;
		}

		public String getPyCreditType() {
			return pyCreditType;
		}

		public void setPyCreditType(String pyCreditType) {
			this.pyCreditType = pyCreditType;
		}
		
	}
}
