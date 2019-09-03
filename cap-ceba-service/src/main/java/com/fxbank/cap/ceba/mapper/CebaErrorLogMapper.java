package com.fxbank.cap.ceba.mapper;

import com.fxbank.cap.ceba.entity.CebaErrorLog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface CebaErrorLogMapper extends MyMapper<CebaErrorLog> {
    List<CebaErrorLog> selectAll();
    List<CebaErrorLog> selectByDate(String date);
    void deleteByDate(String date);
}