package com.fxbank.cap.ykwm.mapper;

import com.fxbank.cap.ykwm.entity.YkwmChecklog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface YkwmChecklogMapper extends MyMapper<YkwmChecklog> {
    List<YkwmChecklog> selectAll();
    void deleteAll(String platDate);
}