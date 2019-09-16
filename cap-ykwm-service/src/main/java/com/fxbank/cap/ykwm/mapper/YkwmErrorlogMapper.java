package com.fxbank.cap.ykwm.mapper;

import com.fxbank.cap.ykwm.entity.YkwmErrorlog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface YkwmErrorlogMapper extends MyMapper<YkwmErrorlog> {
    List<YkwmErrorlog> selectAll();
    List<YkwmErrorlog> selectByDate(String date);
    void deleteByDate(String date);
}