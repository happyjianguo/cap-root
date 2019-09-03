package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.CebaErrorLog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface CebaErrorLogMapper extends MyMapper<CebaErrorLog> {
    List<CebaErrorLog> selectAll();
    List<CebaErrorLog> selectListPage(CebaErrorLog data);
}