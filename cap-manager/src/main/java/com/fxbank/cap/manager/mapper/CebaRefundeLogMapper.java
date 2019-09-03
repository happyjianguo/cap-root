package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.CebaRefundeLog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface CebaRefundeLogMapper extends MyMapper<CebaRefundeLog> {
    List<CebaRefundeLog> selectAll();
    List<CebaRefundeLog> selectListPage(CebaRefundeLog data);
}