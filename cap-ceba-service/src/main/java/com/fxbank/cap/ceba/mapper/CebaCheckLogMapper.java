package com.fxbank.cap.ceba.mapper;

import com.fxbank.cap.ceba.entity.CebaCheckLog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface CebaCheckLogMapper extends MyMapper<CebaCheckLog> {
    List<CebaCheckLog> selectAll();
    void deleteAll(String platDate);
}