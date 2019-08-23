package com.fxbank.cap.ceba.mapper;

import com.fxbank.cap.ceba.entity.CebaHostCheckLog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface CebaHostCheckLogMapper extends MyMapper<CebaHostCheckLog> {
    List<CebaHostCheckLog> selectAll();
    void deleteAll(String platDate);
}