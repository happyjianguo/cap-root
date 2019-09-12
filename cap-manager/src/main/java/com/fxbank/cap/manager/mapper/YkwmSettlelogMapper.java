package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.YkwmSettlelog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface YkwmSettlelogMapper extends MyMapper<YkwmSettlelog> {
    List<YkwmSettlelog> selectAll();
    List<YkwmSettlelog> selectListPage(YkwmSettlelog data);
}