package com.fxbank.cap.ceba.mapper;

import com.fxbank.cap.ceba.entity.CebaBillCheck;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

public interface CebaBillCheckMapper extends MyMapper<CebaBillCheck> {
    List<CebaBillCheck> selectAll();
}