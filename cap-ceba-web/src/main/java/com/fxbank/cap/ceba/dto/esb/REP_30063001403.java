package com.fxbank.cap.ceba.dto.esb;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/**
 * @ClassName: REP_30063001403
 * @Description: 查询收费单位应答
 * @作者 杜振铎
 * @date 2019年5月7日 下午5:18:11
 * 
 */
public class REP_30063001403 extends REP_BASE {

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
		@JSONField(name = "REC_COMPANY_NUM")
		private String recCompanyNum;

		@JSONField(name = "REC_COMPANY_ARRAY")
		private List<DataInfo> recCompanyArray;

		public String getRecCompanyNum() {
			return recCompanyNum;
		}

		public void setRecCompanyNum(String recCompanyNum) {
			this.recCompanyNum = recCompanyNum;
		}

		public List<DataInfo> getRecCompanyArray() {
			return recCompanyArray;
		}

		public void setRecCompanyArray(List<DataInfo> recCompanyArray) {
			this.recCompanyArray = recCompanyArray;
		}

	}

	public static class DataInfo {
		@JSONField(name = "REC_COMPANY_NAME")
		private String recCompanyName;
		@JSONField(name = "PY_CREDIT_NAME")
		private String pyCreditName;
		@JSONField(name = "PY_CREDIT_NO")
		private String pyCreditNo;
		public String getRecCompanyName() {
			return recCompanyName;
		}
		public void setRecCompanyName(String recCompanyName) {
			this.recCompanyName = recCompanyName;
		}
		public String getPyCreditName() {
			return pyCreditName;
		}
		public void setPyCreditName(String pyCreditName) {
			this.pyCreditName = pyCreditName;
		}
		public String getPyCreditNo() {
			return pyCreditNo;
		}
		public void setPyCreditNo(String pyCreditNo) {
			this.pyCreditNo = pyCreditNo;
		}

	}
}
