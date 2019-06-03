package com.fxbank.cap.ykwm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30063001501 
* @Description: 营口供热快查请求 
* @作者 杜振铎
* @date 2019年5月31日 下午2:28:53 
*  
*/
public class REQ_30063001501 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;

	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;

	@JSONField(name = "BODY")
	private REQ_BODY reqBody;

	public REQ_30063001501() {
		super.txDesc = "营口供热快查";
	}

	public class REQ_BODY {

		@JSONField(name = "USER_CARD_NO_T")
		private String userCardNoT;// 用户卡号
		@JSONField(name = "HEAT_COMPANY_ID_T")
		private String heatCompanyIdT;// 供暖公司ID

		public String getUserCardNoT() {
			return userCardNoT;
		}

		public void setUserCardNoT(String userCardNoT) {
			this.userCardNoT = userCardNoT;
		}

		public String getHeatCompanyIdT() {
			return heatCompanyIdT;
		}

		public void setHeatCompanyIdT(String heatCompanyIdT) {
			this.heatCompanyIdT = heatCompanyIdT;
		}

	}

	public REQ_APP_HEAD getReqAppHead() {
		return reqAppHead;
	}

	public void setReqAppHead(REQ_APP_HEAD reqAppHead) {
		this.reqAppHead = reqAppHead;
	}

	public REQ_SYS_HEAD getReqSysHead() {
		return reqSysHead;
	}

	public void setReqSysHead(REQ_SYS_HEAD reqSysHead) {
		this.reqSysHead = reqSysHead;
	}

	public REQ_BODY getReqBody() {
		return reqBody;
	}

	public void setReqBody(REQ_BODY reqBody) {
		this.reqBody = reqBody;
	}

}