package com.fxbank.cap.ceba.mapper;


import com.fxbank.cap.ceba.entity.CebaSettleLog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CebaSettleLogMapper extends MyMapper<CebaSettleLog> {
    List<CebaSettleLog> selectAll();
    List<CebaSettleLog> selectSettleLog(@Param("date") Integer chkDate);
}