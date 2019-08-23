package com.fxbank.cap.ceba.model;

import java.io.Serializable;
import java.math.BigDecimal;
import com.fxbank.cip.base.log.MyLog;
import com.fxbank.cip.base.model.ModelBase;

/** 
* @ClassName: CebaCheckLogInitModel 
* @Description: 光大银行对账文件登记
* @author Duzhenduo
* @date 2019年1月31日 上午10:09:47 
*  
*/
public class CebaCheckLogInitModel extends ModelBase implements Serializable {

	public CebaCheckLogInitModel(MyLog mylog, Integer sysDate, Integer sysTime, Integer sysTraceno) {
		super(mylog, sysDate, sysTime, sysTraceno);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8850394891802837L;

    /**
     * 交易金额
     */
    private BigDecimal txAmt;

    /**
     * 光大银行流水号
     */
    private String bankBillNo;

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

	public String getBankBillNo() {
		return bankBillNo;
	}

	public void setBankBillNo(String bankBillNo) {
		this.bankBillNo = bankBillNo;
	}

    
}
