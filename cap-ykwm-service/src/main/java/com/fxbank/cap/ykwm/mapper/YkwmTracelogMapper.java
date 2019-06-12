package com.fxbank.cap.ykwm.mapper;

import com.fxbank.cap.ykwm.entity.YkwmTracelog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YkwmTracelogMapper extends MyMapper<YkwmTracelog> {
    List<YkwmTracelog> selectAll();
    String selectTotalNum(@Param("date") String date);
    String selectTotalSum(@Param("date") String date);
    List<YkwmTracelog> selectCheckedTrace(@Param("date") String date);
    String selectTraceNum(@Param("date") String date,@Param("capResult") String capResult);
}