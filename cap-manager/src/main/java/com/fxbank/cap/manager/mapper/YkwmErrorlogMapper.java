package com.fxbank.cap.manager.mapper;

import com.fxbank.cap.manager.entity.YkwmErrorlog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface YkwmErrorlogMapper extends MyMapper<YkwmErrorlog> {
    List<YkwmErrorlog> selectAll();
    List<YkwmErrorlog> selectListPage(YkwmErrorlog data);

}