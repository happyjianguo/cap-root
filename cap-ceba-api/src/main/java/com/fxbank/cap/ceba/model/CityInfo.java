package com.fxbank.cap.ceba.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/** 
* @ClassName: CityInfo 
* @Description: 缴费城市和收费单位
* @作者 杜振铎
* @date 2019年5月16日 下午2:51:27 
*  
*/
public class CityInfo {

	@JSONField(name = "CITY_NAME")
	private String cityName;

	@JSONField(name = "PARENT_NAME")
	private String parentName;

	@JSONField(name = "ARRAY01")
	private List<CompanyInfo> array01 = new ArrayList<CompanyInfo>();

	@JSONField(name = "ARRAY02")
	private List<CompanyInfo> array02 = new ArrayList<CompanyInfo>();

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<CompanyInfo> getArray01() {
		return array01;
	}

	public void setArray01(List<CompanyInfo> array01) {
		this.array01 = array01;
	}

	public List<CompanyInfo> getArray02() {
		return array02;
	}

	public void setArray02(List<CompanyInfo> array02) {
		this.array02 = array02;
	}

	public static class CompanyInfo {
		@JSONField(name = "COMPANY_NAME")
		private String companyName;
		@JSONField(name = "PROJ_CODE")
		private String projCode;
		@JSONField(name = "PROJ_NAME")
		private String projName;

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getProjCode() {
			return projCode;
		}

		public void setProjCode(String projCode) {
			this.projCode = projCode;
		}

		public String getProjName() {
			return projName;
		}

		public void setProjName(String projName) {
			this.projName = projName;
		}

	}
}
