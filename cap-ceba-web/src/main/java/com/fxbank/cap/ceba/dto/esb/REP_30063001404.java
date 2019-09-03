package com.fxbank.cap.ceba.dto.esb;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/**
 * @ClassName: REP_30063001404
 * @Description: 查询缴费城市应答
 * @作者 杜振铎
 * @date 2019年5月7日 下午5:18:11
 * 
 */
public class REP_30063001404 extends REP_BASE {

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
		@JSONField(name = "CITY_NUM")
		private String cityNum;

		@JSONField(name = "CITY_ARRAY")
		private List<DataInfo> cityArray;

		public String getCityNum() {
			return cityNum;
		}

		public void setCityNum(String cityNum) {
			this.cityNum = cityNum;
		}

		public List<DataInfo> getCityArray() {
			return cityArray;
		}

		public void setCityArray(List<DataInfo> cityArray) {
			this.cityArray = cityArray;
		}

	}

	public static class DataInfo {

		@JSONField(name = "CITY_NAME")
		private String cityName;
		@JSONField(name = "PY_CITY_CODE")
		private String pyCityCode;
		@JSONField(name = "STATE_NAME")
		private String stateName;
		public String getCityName() {
			return cityName;
		}
		public void setCityName(String cityName) {
			this.cityName = cityName;
		}
		public String getPyCityCode() {
			return pyCityCode;
		}
		public void setPyCityCode(String pyCityCode) {
			this.pyCityCode = pyCityCode;
		}
		public String getStateName() {
			return stateName;
		}
		public void setStateName(String stateName) {
			this.stateName = stateName;
		}

	}
}
