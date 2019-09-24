package com.fxbank.cap.ceba.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * CEBA_BILL_CHECK
 */
@Table(name = "CEBA_BILL_CHECK")
@Alias("cebaBillCheck")
public class CebaBillCheck {
    /**
     * null
     */
	@Id
    @Column(name = "SIGN_DATE")
    private Integer signDate;

    /**
     * null
     */
    @Column(name = "FILE_NAME")
    private String fileName;

    /**
     * null
     */
    @Id
    @Column(name = "UPLOAD_DATE")
    private Long uploadDate;

    /**
     * null
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * null
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * null
     * @return SIGN_DATE null
     */
    public Integer getSignDate() {
        return signDate;
    }

    /**
     * null
     * @param signDate null
     */
    public void setSignDate(Integer signDate) {
        this.signDate = signDate;
    }

    /**
     * null
     * @return FILE_NAME null
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * null
     * @param fileName null
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * null
     * @return UPLOAD_DATE null
     */
    public Long getUploadDate() {
        return uploadDate;
    }

    /**
     * null
     * @param uploadDate null
     */
    public void setUploadDate(Long uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * null
     * @return STATUS null
     */
    public String getStatus() {
        return status;
    }

    /**
     * null
     * @param status null
     */
    public void setStatus(String status) {
        this.status = status;
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