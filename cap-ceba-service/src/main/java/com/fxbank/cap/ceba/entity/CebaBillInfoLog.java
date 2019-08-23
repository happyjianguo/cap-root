package com.fxbank.cap.ceba.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * CEBA_BILL_INFO_LOG
 */
@Table(name = "CEBA_BILL_INFO_LOG")
@Alias("cebaBillInfoLog")
public class CebaBillInfoLog {
    /**
     * null
     */
    @Id
    @Column(name = "SEQ_NO")
    private String seqNo;

    /**
     * null
     */
    @Column(name = "BILL_KEY")
    private String billKey;

    /**
     * null
     */
    @Column(name = "COMPANY_ID")
    private String companyId;

    /**
     * null
     */
    @Column(name = "CONTRACT_NO")
    private String contractNo;

    /**
     * null
     */
    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    /**
     * null
     */
    @Column(name = "BALANCE")
    private BigDecimal balance;

    /**
     * null
     */
    @Column(name = "PAY_AMOUNT")
    private BigDecimal payAmount;

    /**
     * null
     */
    @Column(name = "BEGIN_DATE")
    private String beginDate;

    /**
     * null
     */
    @Column(name = "END_DATE")
    private String endDate;

    /**
     * null
     */
    @Column(name = "ITEM1")
    private String item1;

    /**
     * null
     */
    @Column(name = "ITEM2")
    private String item2;

    /**
     * null
     */
    @Column(name = "ITEM3")
    private String item3;

    /**
     * null
     */
    @Column(name = "ITEM4")
    private String item4;

    /**
     * null
     */
    @Column(name = "ITEM5")
    private String item5;

    /**
     * null
     */
    @Column(name = "ITEM6")
    private String item6;

    /**
     * null
     */
    @Column(name = "ITEM7")
    private String item7;

    /**
     * null
     */
    @Column(name = "FILED1")
    private String filed1;

    /**
     * null
     */
    @Column(name = "FILED2")
    private String filed2;

    /**
     * null
     */
    @Column(name = "FILED3")
    private String filed3;

    /**
     * null
     */
    @Column(name = "FILED4")
    private String filed4;

    /**
     * null
     */
    @Column(name = "FILED5")
    private String filed5;

    /**
     * null
     * @return SEQ_NO null
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * null
     * @param seqNo null
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * null
     * @return BILL_KEY null
     */
    public String getBillKey() {
        return billKey;
    }

    /**
     * null
     * @param billKey null
     */
    public void setBillKey(String billKey) {
        this.billKey = billKey;
    }

    /**
     * null
     * @return COMPANY_ID null
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * null
     * @param companyId null
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * null
     * @return CONTRACT_NO null
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * null
     * @param contractNo null
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    /**
     * null
     * @return CUSTOMER_NAME null
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * null
     * @param customerName null
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * null
     * @return BALANCE null
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * null
     * @param balance null
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * null
     * @return PAY_AMOUNT null
     */
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    /**
     * null
     * @param payAmount null
     */
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * null
     * @return BEGIN_DATE null
     */
    public String getBeginDate() {
        return beginDate;
    }

    /**
     * null
     * @param beginDate null
     */
    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * null
     * @return END_DATE null
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * null
     * @param endDate null
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * null
     * @return ITEM1 null
     */
    public String getItem1() {
        return item1;
    }

    /**
     * null
     * @param item1 null
     */
    public void setItem1(String item1) {
        this.item1 = item1;
    }

    /**
     * null
     * @return ITEM2 null
     */
    public String getItem2() {
        return item2;
    }

    /**
     * null
     * @param item2 null
     */
    public void setItem2(String item2) {
        this.item2 = item2;
    }

    /**
     * null
     * @return ITEM3 null
     */
    public String getItem3() {
        return item3;
    }

    /**
     * null
     * @param item3 null
     */
    public void setItem3(String item3) {
        this.item3 = item3;
    }

    /**
     * null
     * @return ITEM4 null
     */
    public String getItem4() {
        return item4;
    }

    /**
     * null
     * @param item4 null
     */
    public void setItem4(String item4) {
        this.item4 = item4;
    }

    /**
     * null
     * @return ITEM5 null
     */
    public String getItem5() {
        return item5;
    }

    /**
     * null
     * @param item5 null
     */
    public void setItem5(String item5) {
        this.item5 = item5;
    }

    /**
     * null
     * @return ITEM6 null
     */
    public String getItem6() {
        return item6;
    }

    /**
     * null
     * @param item6 null
     */
    public void setItem6(String item6) {
        this.item6 = item6;
    }

    /**
     * null
     * @return ITEM7 null
     */
    public String getItem7() {
        return item7;
    }

    /**
     * null
     * @param item7 null
     */
    public void setItem7(String item7) {
        this.item7 = item7;
    }

    /**
     * null
     * @return FILED1 null
     */
    public String getFiled1() {
        return filed1;
    }

    /**
     * null
     * @param filed1 null
     */
    public void setFiled1(String filed1) {
        this.filed1 = filed1;
    }

    /**
     * null
     * @return FILED2 null
     */
    public String getFiled2() {
        return filed2;
    }

    /**
     * null
     * @param filed2 null
     */
    public void setFiled2(String filed2) {
        this.filed2 = filed2;
    }

    /**
     * null
     * @return FILED3 null
     */
    public String getFiled3() {
        return filed3;
    }

    /**
     * null
     * @param filed3 null
     */
    public void setFiled3(String filed3) {
        this.filed3 = filed3;
    }

    /**
     * null
     * @return FILED4 null
     */
    public String getFiled4() {
        return filed4;
    }

    /**
     * null
     * @param filed4 null
     */
    public void setFiled4(String filed4) {
        this.filed4 = filed4;
    }

    /**
     * null
     * @return FILED5 null
     */
    public String getFiled5() {
        return filed5;
    }

    /**
     * null
     * @param filed5 null
     */
    public void setFiled5(String filed5) {
        this.filed5 = filed5;
    }
}