package com.fxbank.cap.ceba.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * CEBA_OUTAGE_LOG
 */
@Table(name = "CEBA_OUTAGE_LOG")
@Alias("cebaOutageLog")
public class CebaOutageLog {
    /**
     * null
     */
	@Id
    @Column(name = "NOTICE_TIME")
    private Long noticeTime;
    /**
     * null
     */
    @Column(name = "OUTAGE_NOTICE_ID")
    private String outageNoticeId;

    /**
     * null
     */
    @Id
    @Column(name = "COMPANY_ID")
    private String companyId;

    /**
     * null
     */
    @Column(name = "COMPANY_NAME")
    private String companyName;

    /**
     * null
     */
    @Column(name = "OUTAGE_REASON")
    private String outageReason;

    /**
     * null
     */
    @Column(name = "OUTAGE_DESC")
    private String outageDesc;

    public Long getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(Long noticeTime) {
		this.noticeTime = noticeTime;
	}

	/**
     * null
     */
    @Column(name = "OUTAGE_BEGIN_TIME")
    private Long outageBeginTime;

    /**
     * null
     */
    @Column(name = "OUTAGE_END_TIME")
    private Long outageEndTime;

    /**
     * null
     */
    @Column(name = "FIX_DATE")
    private Integer fixDate;

    /**
     * null
     */
    @Column(name = "FIX_TIME")
    private Integer fixTime;

    /**
     * null
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * null
     * @return OUTAGE_NOTICE_ID null
     */
    public String getOutageNoticeId() {
        return outageNoticeId;
    }

    /**
     * null
     * @param outageNoticeId null
     */
    public void setOutageNoticeId(String outageNoticeId) {
        this.outageNoticeId = outageNoticeId;
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
     * @return COMPANY_NAME null
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * null
     * @param companyName null
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * null
     * @return OUTAGE_REASON null
     */
    public String getOutageReason() {
        return outageReason;
    }

    /**
     * null
     * @param outageReason null
     */
    public void setOutageReason(String outageReason) {
        this.outageReason = outageReason;
    }

    /**
     * null
     * @return OUTAGE_DESC null
     */
    public String getOutageDesc() {
        return outageDesc;
    }

    /**
     * null
     * @param outageDesc null
     */
    public void setOutageDesc(String outageDesc) {
        this.outageDesc = outageDesc;
    }

    /**
     * null
     * @return OUTAGE_BEGIN_TIME null
     */
    public Long getOutageBeginTime() {
        return outageBeginTime;
    }

    /**
     * null
     * @param outageBeginTime null
     */
    public void setOutageBeginTime(Long outageBeginTime) {
        this.outageBeginTime = outageBeginTime;
    }

    /**
     * null
     * @return OUTAGE_END_TIME null
     */
    public Long getOutageEndTime() {
        return outageEndTime;
    }

    /**
     * null
     * @param outageEndTime null
     */
    public void setOutageEndTime(Long outageEndTime) {
        this.outageEndTime = outageEndTime;
    }

    /**
     * null
     * @return FIX_DATE null
     */
    public Integer getFixDate() {
        return fixDate;
    }

    /**
     * null
     * @param fixDate null
     */
    public void setFixDate(Integer fixDate) {
        this.fixDate = fixDate;
    }

    /**
     * null
     * @return FIX_TIME null
     */
    public Integer getFixTime() {
        return fixTime;
    }

    /**
     * null
     * @param fixTime null
     */
    public void setFixTime(Integer fixTime) {
        this.fixTime = fixTime;
    }

    /**
     * null
     * @return REMARK null
     */
    public String getRemark() {
        return remark;
    }

    /**
     * null
     * @param remark null
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}