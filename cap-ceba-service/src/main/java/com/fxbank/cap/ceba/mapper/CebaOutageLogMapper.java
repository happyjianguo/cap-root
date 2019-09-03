package com.fxbank.cap.ceba.mapper;

import com.fxbank.cap.ceba.entity.CebaOutageLog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CebaOutageLogMapper extends MyMapper<CebaOutageLog> {
    List<CebaOutageLog> selectAll();
    CebaOutageLog queryOutageLog(@Param("companyId") String companyId,@Param("sysDate") Long sysDate);
}