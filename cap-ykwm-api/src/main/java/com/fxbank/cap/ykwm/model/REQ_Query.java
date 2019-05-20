package com.fxbank.cap.ykwm.model;
import java.io.Serializable;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.pkg.fixed.FixedAnno.FixedField;

/**
 * @ClassName: REQ_Query
 * @Description: 欠费查询请求
 * @作者 杜振铎
 * @date 2019年4月29日 下午3:02:57
 * 
 */
public class REQ_Query extends REQ_BASE {

	private static final long serialVersionUID = -7068590384756394085L;

	@Deprecated
	public REQ_Query() {
		super(null, 0, 0, 0);
	}

	public REQ_Query(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
		super.setTtxnNm("Query");
	}

	@FixedField(order = 2, len = 8, desc = "业务类型")
	private String companyID;

	@FixedField(order = 3, len = 30, desc = "业务类型")
	private String cardNum;

	@FixedField(order = 4, len = 8, desc = "业务类型")
	private String batchNum;

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

}