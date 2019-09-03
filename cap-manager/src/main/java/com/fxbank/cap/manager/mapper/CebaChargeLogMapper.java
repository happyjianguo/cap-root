package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.CebaChargeLog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface CebaChargeLogMapper extends MyMapper<CebaChargeLog> {
    List<CebaChargeLog> selectAll();
    List<CebaChargeLog> selectListPage(CebaChargeLog data);
}