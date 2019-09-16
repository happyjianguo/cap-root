package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.YkwmTracelog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface YkwmTracelogMapper extends MyMapper<YkwmTracelog> {
    List<YkwmTracelog> selectAll();
    List<YkwmTracelog> selectListPage(YkwmTracelog data);

}