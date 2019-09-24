package com.fxbank.cap.ceba.mapper;

import com.fxbank.cap.ceba.entity.CebaChargeLog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/** 
* @ClassName: CebaChargeLogMapper 
* @Description: 缴费单销账日志
* @作者 杜振铎
* @date 2019年5月14日 下午2:06:07 
*  
*/
public interface CebaChargeLogMapper extends MyMapper<CebaChargeLog> {
    /** 
     * 查询所有缴费单销账日志
    * @Title: selectAll 
    * @Description: 查询所有缴费单销账日志 
    * @param @return    设定文件 
    * @throws 
    */
    List<CebaChargeLog> selectAll();
    String selectTotalNum(@Param("date") String date);
    String selectTotalSum(@Param("date") String date);
    List<CebaChargeLog> selectCheckedTrace(@Param("date") String date);
    List<CebaChargeLog> selectTraceByCheckState(@Param("date") String date,@Param("hostCheckState") String hostCheckState,
    		@Param("cebaCheckState") String cebaCheckState);
    String selectHostCheckNum(@Param("date") String date,@Param("capResult") String capResult);
    String selectCebaCheckNum(@Param("date") String date,@Param("capResult") String capResult);
    String selectCheckSuccNum(@Param("date") String date);
    String selectTotalCheckNum(@Param("date") String date);
    String selectCheckSuccAmt(@Param("date") String date);
    CebaChargeLog selectByCheckState(@Param("platDate") String platDate,
    		@Param("platTrace") String platTrace,@Param("hostCheckState") String hostCheckState,
    		@Param("cebaCheckState") String cebaCheckState);
}