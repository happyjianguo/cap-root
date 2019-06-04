package com.fxbank.cap.ykwm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30062001201 
* @Description: 营口供热对账请求
* @作者 杜振铎
* @date 2019年5月31日 下午2:28:22 
*  
*/
public class REQ_30062001201 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;

	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;

	@JSONField(name = "BODY")
	private REQ_BODY reqBody;

	public REQ_30062001201() {
		super.txDesc = "营口供热对账";
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

		@JSONField(name = "COLLATE_DT")
		private String collateDt;// 对账日期

		public String getCollateDt() {
			return collateDt;
		}

		public void setCollateDt(String collateDt) {
			this.collateDt = collateDt;
		}

	}

}