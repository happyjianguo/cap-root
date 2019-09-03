package com.fxbank.cap.manager.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.ibatis.type.Alias;

/**
 * CEBA_REFUNDE_LOG
 */
@Table(name = "CEBA_REFUNDE_LOG")
@Alias("cebaRefundeLog")
public class CebaRefundeLog extends BaseData{
    /**
     * null
     */
    @Id
    @Column(name = "PLAT_DATE")
    private Long platDate;

    /**
     * null
     */
    @Id
    @Column(name = "PLAT_TRACENO")
    private Long platTraceno;

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
     * @return PLAT_DATE null
     */
    public Long getPlatDate() {
        return platDate;
    }

    /**
     * null
     * @param platDate null
     */
    public void setPlatDate(Long platDate) {
        this.platDate = platDate;
    }

    /**
     * null
     * @return PLAT_TRACENO null
     */
    public Long getPlatTraceno() {
        return platTraceno;
    }

    /**
     * null
     * @param platTraceno null
     */
    public void setPlatTraceno(Long platTraceno) {
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

    /**
     * null
     * @return HOST_CODE null
     */
    public String getHostCode() {
        return hostCode;
    }

    /**
     * null
     * @param hostCode null
     */
    public void setHostCode(String hostCode) {
        this.hostCode = hostCode;
    }

    /**
     * null
     * @return HOST_MSG null
     */
    public String getHostMsg() {
        return hostMsg;
    }

    /**
     * null
     * @param hostMsg null
     */
    public void setHostMsg(String hostMsg) {
        this.hostMsg = hostMsg;
    }
}