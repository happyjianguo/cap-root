package com.fxbank.cap.ceba.mapper;

import com.fxbank.cap.ceba.entity.CebaRefundeLog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CebaRefundeLogMapper extends MyMapper<CebaRefundeLog> {
    List<CebaRefundeLog> selectAll();
    List<CebaRefundeLog> selectRefundeFileLog(@Param("date") String date);
}