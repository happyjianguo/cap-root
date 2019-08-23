package com.fxbank.cap.ceba.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * CEBA_REFUNDE_LOG
 */
@Table(name = "CEBA_REFUNDE_LOG")
@Alias("cebaRefundeLog")
public class CebaRefundeLog {
    /**
     * null
     */
    @Id
    @Column(name = "PLAT_DATE")
    private Integer platDate;

    /**
     * null
     */
    @Id
    @Column(name = "PLAT_TRACENO")
    private Integer platTraceno;

    /**
     * null
     */
    @Column(name = "FLAG")
    private String flag;

    /**
     * null
     */
    @Column(name = "STATUS")
    private String status;
    
    /**
     * null
     */
    @Column(name = "HOST_CODE")
    private String hostCode;
    
    /**
     * null
     */
    @Column(name = "HOST_MSG")
    private String hostMsg;
    
    /**
     * null
     */
    @Column(name = "REQ_DATE")
    private Integer reqDate;

    public String getHostCode() {
		return hostCode;
	}

	public void setHostCode(String hostCode) {
		this.hostCode = hostCode;
	}

	public String getHostMsg() {
		return hostMsg;
	}

	public void setHostMsg(String hostMsg) {
		this.hostMsg = hostMsg;
	}

	/**
     * null
     * @return PLAT_DATE null
     */
    public Integer getPlatDate() {
        return platDate;
    }

    /**
     * null
     * @param platDate null
     */
    public void setPlatDate(Integer platDate) {
        this.platDate = platDate;
    }

    public Integer getReqDate() {
		return reqDate;
	}

	public void setReqDate(Integer reqDate) {
		this.reqDate = reqDate;
	}

	/**
     * null
     * @return PLAT_TRACENO null
     */
    public Integer getPlatTraceno() {
        return platTraceno;
    }

    /**
     * null
     * @param platTraceno null
     */
    public void setPlatTraceno(Integer platTraceno) {
        this.platTraceno = platTraceno;
    }

    /**
     * null
     * @return FLAG null
     */
    public String getFlag() {
        return flag;
    }

    /**
     * null
     * @param flag null
     */
    public void setFlag(String flag) {
        this.flag = flag;
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
}