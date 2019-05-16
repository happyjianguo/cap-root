package com.fxbank.cap.ceba.mapper;

import com.fxbank.cap.ceba.entity.CebaChargeLog;
import com.fxbank.cip.base.common.MyMapper;
import java.util.List;

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
}