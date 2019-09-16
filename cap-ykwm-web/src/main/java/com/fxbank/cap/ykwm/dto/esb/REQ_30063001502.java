package com.fxbank.cap.ykwm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REQ_APP_HEAD;
import com.fxbank.cip.base.dto.REQ_BASE;
import com.fxbank.cip.base.dto.REQ_SYS_HEAD;

/** 
* @ClassName: REQ_30063001502 
* @Description: 营口供热模糊查询请求
* @作者 杜振铎
* @date 2019年5月31日 下午2:29:29 
*  
*/
public class REQ_30063001502 extends REQ_BASE {

	@JSONField(name = "APP_HEAD")
	private REQ_APP_HEAD reqAppHead;

	@JSONField(name = "SYS_HEAD")
	private REQ_SYS_HEAD reqSysHead;

	@JSONField(name = "BODY")
	private REQ_BODY reqBody;

	public REQ_30063001502() {
		super.txDesc = "营口供热模糊查询";
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

		@JSONField(name = "USERNAME")
		private String username;// 用户姓名
		@JSONField(name = "QUERY_TYPE")
		private String queryType;// 查询类型
		@JSONField(name = "QUERY_INFO")
		private String queryInfo;// 查询内容
		@JSONField(name = "HEAT_COMPANY_ID_T")
		private String heatCompanyIdT;// 供暖公司ID

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getQueryType() {
			return queryType;
		}

		public void setQueryType(String queryType) {
			this.queryType = queryType;
		}

		public String getQueryInfo() {
			return queryInfo;
		}

		public void setQueryInfo(String queryInfo) {
			this.queryInfo = queryInfo;
		}

		public String getHeatCompanyIdT() {
			return heatCompanyIdT;
		}

		public void setHeatCompanyIdT(String heatCompanyIdT) {
			this.heatCompanyIdT = heatCompanyIdT;
		}

	}

}