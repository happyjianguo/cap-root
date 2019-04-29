package com.fxbank.cap.ykwm.model;

import com.fxbank.cip.base.log.MyLog;


/** 
* @ClassName: REQ_Query 
* @Description: 欠费查询请求
* @author Duzhenduo
* @date 2019年4月29日 下午3:02:57 
*  
*/
public class REQ_Query extends REQ_BASE {

	private static final long serialVersionUID = 4990422436703924474L;

	private String companyID;

	private String cardNum;

	private String batchNum;
    
    @Deprecated
	public REQ_Query() {
		super(null, 0, 0, 0);
	}

    public REQ_Query(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
        super(mylog, sysDate, sysTime, sysTraceno);
        super.getHeader().settTxnNm("Query");
    }

    @Override
	public String creaFixPack() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.getHeader().creaFixPack());
		sb.append(this.companyID == null ? "" : this.companyID).append("|");
		sb.append(this.cardNum == null ? "" : this.cardNum).append("|");
		sb.append(this.batchNum == null ? "" : this.batchNum).append("|");

		return sb.toString();
	}

	@Override
	public void chanFixPack(String pack) {
		String[] array = pack.split("\\|");
		super.getHeader().chanFixPack(array[0]);
		this.companyID = array[1].trim();
		this.cardNum = array[2].trim();
		this.batchNum = array[3].trim();
	}

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