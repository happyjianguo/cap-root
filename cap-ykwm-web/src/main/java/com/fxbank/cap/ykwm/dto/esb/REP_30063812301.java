package com.fxbank.cap.ykwm.dto.esb;

import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30063812301 
* @Description: 营口供热撤销快查应答 
* @作者 杜振铎
* @date 2019年5月31日 下午2:26:22 
*  
*/
public class REP_30063812301 extends REP_BASE {

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

	public REP_SYS_HEAD getRepSysHead() {
		return repSysHead;
	}

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

		@JSONField(name = "PY_FEE_AMT_T")
		private String pyFeeAmtT;// 缴费金额
		@JSONField(name = "CORE_DT_T2")
		private String coreDtT2;// 核心日期
		@JSONField(name = "CORE_SEQ_T2")
		private String coreSeqT2;// 核心流水
		@JSONField(name = "HEAT_SEQ_NO_T")
		private String heatSeqNoT;// 热力流水号
		@JSONField(name = "PY_FEE_CARD_NO_T")
		private String pyFeeCardNoT;// 缴费卡号

		public String getPyFeeCardNoT() {
			return pyFeeCardNoT;
		}

		public void setPyFeeCardNoT(String pyFeeCardNoT) {
			this.pyFeeCardNoT = pyFeeCardNoT;
		}

		public String getPyFeeAmtT() {
			return pyFeeAmtT;
		}

		public void setPyFeeAmtT(String pyFeeAmtT) {
			this.pyFeeAmtT = pyFeeAmtT;
		}

		public String getCoreDtT2() {
			return coreDtT2;
		}

		public void setCoreDtT2(String coreDtT2) {
			this.coreDtT2 = coreDtT2;
		}

		public String getCoreSeqT2() {
			return coreSeqT2;
		}

		public void setCoreSeqT2(String coreSeqT2) {
			this.coreSeqT2 = coreSeqT2;
		}

		public String getHeatSeqNoT() {
			return heatSeqNoT;
		}

		public void setHeatSeqNoT(String heatSeqNoT) {
			this.heatSeqNoT = heatSeqNoT;
		}

	}

}
