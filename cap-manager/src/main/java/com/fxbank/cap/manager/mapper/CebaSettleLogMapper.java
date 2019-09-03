package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.CebaSettleLog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface CebaSettleLogMapper extends MyMapper<CebaSettleLog> {
    List<CebaSettleLog> selectAll();
    List<CebaSettleLog> selectListPage(CebaSettleLog data);
}