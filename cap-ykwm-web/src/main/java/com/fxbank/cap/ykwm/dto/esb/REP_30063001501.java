package com.fxbank.cap.ykwm.dto.esb;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;
import com.fxbank.cip.base.dto.REP_APP_HEAD;
import com.fxbank.cip.base.dto.REP_BASE;
import com.fxbank.cip.base.dto.REP_SYS_HEAD;

/** 
* @ClassName: REP_30063001501 
* @Description: 营口供热快查应答
* @作者 杜振铎
* @date 2019年5月31日 下午2:25:14 
*  
*/
public class REP_30063001501 extends REP_BASE {

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

		@JSONField(name = "USERNAME")
		private String username;// 用户姓名
		@JSONField(name = "USER_ADDR")
		private String userAddr;// 用户地址
		@JSONField(name = "MIN_PAY_FEE_AMT")
		private String minPayFeeAmt;// 最低交费金额
		@JSONField(name = "PY_FEE_AMT_T")
		private String pyFeeAmtT;// 缴费金额
		@JSONField(name = "EMPLOYER_NAME")
		private String employerName;// 工作单位
		@JSONField(name = "HEAT_AREA_T")
		private String heatAreaT;// 供暖面积
		@JSONField(name = "RESERVE_FIELD1")
		private String reserveField1;// 备用字段1
		@JSONField(name = "CHANNEL_REF_NO")
		private String channelRefNo;// 渠道业务流水号
		@JSONField(name = "DBT_INFO_ARRAY")
		private List<DbtInfo> dbtInfoArray;// 欠费明细数组
		@JSONField(name = "COURIER_CMPNY_ARRAY")
		private List<CourierCmpny> courierCmpnyArray;// 快递公司数组

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getUserAddr() {
			return userAddr;
		}

		public void setUserAddr(String userAddr) {
			this.userAddr = userAddr;
		}

		public String getMinPayFeeAmt() {
			return minPayFeeAmt;
		}

		public void setMinPayFeeAmt(String minPayFeeAmt) {
			this.minPayFeeAmt = minPayFeeAmt;
		}

		public String getPyFeeAmtT() {
			return pyFeeAmtT;
		}

		public void setPyFeeAmtT(String pyFeeAmtT) {
			this.pyFeeAmtT = pyFeeAmtT;
		}

		public String getEmployerName() {
			return employerName;
		}

		public void setEmployerName(String employerName) {
			this.employerName = employerName;
		}

		public String getHeatAreaT() {
			return heatAreaT;
		}

		public void setHeatAreaT(String heatAreaT) {
			this.heatAreaT = heatAreaT;
		}

		public String getReserveField1() {
			return reserveField1;
		}

		public void setReserveField1(String reserveField1) {
			this.reserveField1 = reserveField1;
		}

		public String getChannelRefNo() {
			return channelRefNo;
		}

		public void setChannelRefNo(String channelRefNo) {
			this.channelRefNo = channelRefNo;
		}

		public List<DbtInfo> getDbtInfoArray() {
			return dbtInfoArray;
		}

		public void setDbtInfoArray(List<DbtInfo> dbtInfoArray) {
			this.dbtInfoArray = dbtInfoArray;
		}

		public List<CourierCmpny> getCourierCmpnyArray() {
			return courierCmpnyArray;
		}

		public void setCourierCmpnyArray(List<CourierCmpny> courierCmpnyArray) {
			this.courierCmpnyArray = courierCmpnyArray;
		}

	}

	public static class DbtInfo {
		@JSONField(name = "HEAT_YEAR_T")
		private String heatYearT;// 供暖年度
		@JSONField(name = "HEAT_TP_T")
		private String heatTpT;// 供暖类型
		@JSONField(name = "HEAT_AREA_T")
		private String heatAreaT;// 供暖面积
		@JSONField(name = "HEAT_PRICE_T")
		private String heatPriceT;// 供暖单价
		@JSONField(name = "DBT_AMT_T2")
		private String dbtAmtT2;// 欠费金额
		@JSONField(name = "OFFER_AMT_T")
		private String offerAmtT;// 优惠金额
		@JSONField(name = "LATE_FEE_T")
		private String lateFeeT;// 滞纳金
		@JSONField(name = "ACCR_AMT_T")
		private String accrAmtT;// 应缴金额

		public String getHeatYearT() {
			return heatYearT;
		}

		public void setHeatYearT(String heatYearT) {
			this.heatYearT = heatYearT;
		}

		public String getHeatTpT() {
			return heatTpT;
		}

		public void setHeatTpT(String heatTpT) {
			this.heatTpT = heatTpT;
		}

		public String getHeatAreaT() {
			return heatAreaT;
		}

		public void setHeatAreaT(String heatAreaT) {
			this.heatAreaT = heatAreaT;
		}

		public String getHeatPriceT() {
			return heatPriceT;
		}

		public void setHeatPriceT(String heatPriceT) {
			this.heatPriceT = heatPriceT;
		}

		public String getDbtAmtT2() {
			return dbtAmtT2;
		}

		public void setDbtAmtT2(String dbtAmtT2) {
			this.dbtAmtT2 = dbtAmtT2;
		}

		public String getOfferAmtT() {
			return offerAmtT;
		}

		public void setOfferAmtT(String offerAmtT) {
			this.offerAmtT = offerAmtT;
		}

		public String getLateFeeT() {
			return lateFeeT;
		}

		public void setLateFeeT(String lateFeeT) {
			this.lateFeeT = lateFeeT;
		}

		public String getAccrAmtT() {
			return accrAmtT;
		}

		public void setAccrAmtT(String accrAmtT) {
			this.accrAmtT = accrAmtT;
		}
	}

	public static class CourierCmpny {
		@JSONField(name = "COURIER_CMPNY_ID_T")
		private String courierCmpnyIdT;// 快递公司ID
		@JSONField(name = "COURIER_CMPY_ADDS_T")
		private String courierCmpyAddsT;// 快递公司名称
		@JSONField(name = "COURIER_FEE_T")
		private String courierFeeT;// 快递费

		public String getCourierCmpnyIdT() {
			return courierCmpnyIdT;
		}

		public void setCourierCmpnyIdT(String courierCmpnyIdT) {
			this.courierCmpnyIdT = courierCmpnyIdT;
		}

		public String getCourierCmpyAddsT() {
			return courierCmpyAddsT;
		}

		public void setCourierCmpyAddsT(String courierCmpyAddsT) {
			this.courierCmpyAddsT = courierCmpyAddsT;
		}

		public String getCourierFeeT() {
			return courierFeeT;
		}

		public void setCourierFeeT(String courierFeeT) {
			this.courierFeeT = courierFeeT;
		}

	}

}
