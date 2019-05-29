package com.fxbank.cap.ykwm.mapper;

import com.fxbank.cap.ykwm.entity.YkwmTracelog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface YkwmTracelogMapper extends MyMapper<YkwmTracelog> {
    List<YkwmTracelog> selectAll();
}