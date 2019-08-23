package com.fxbank.cap.ceba.mapper;

import com.fxbank.cap.ceba.entity.CebaBillInfoLog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface CebaBillInfoLogMapper extends MyMapper<CebaBillInfoLog> {
    List<CebaBillInfoLog> selectAll();
}