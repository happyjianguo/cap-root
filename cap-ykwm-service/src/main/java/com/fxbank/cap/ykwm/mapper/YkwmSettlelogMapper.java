package com.fxbank.cap.ykwm.mapper;

import com.fxbank.cap.ykwm.entity.YkwmSettlelog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface YkwmSettlelogMapper extends MyMapper<YkwmSettlelog> {
    List<YkwmSettlelog> selectAll();
    List<YkwmSettlelog> selectSettleLog(@Param("date") Integer chkDate);
}