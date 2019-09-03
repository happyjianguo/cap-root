package com.fxbank.cap.manager.service;


import com.fxbank.cap.manager.entity.CebaChargeLog;
import com.fxbank.cap.manager.entity.CebaErrorLog;
import com.fxbank.cap.manager.entity.CebaRefundeLog;
import com.fxbank.cap.manager.entity.CebaSettleLog;
import com.fxbank.cap.manager.mapper.CebaChargeLogMapper;
import com.fxbank.cap.manager.mapper.CebaErrorLogMapper;
import com.fxbank.cap.manager.mapper.CebaRefundeLogMapper;
import com.fxbank.cap.manager.mapper.CebaSettleLogMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CebaService {

    @Resource
    private CebaChargeLogMapper chargeLogMapper;

    @Resource
    private CebaErrorLogMapper errorLogMapper;
    
    @Resource
    private CebaRefundeLogMapper refundeLogMapper;
    
    @Resource
    private CebaSettleLogMapper settleLogMapper;


    public CebaChargeLog getCharegeLogListPage(CebaChargeLog data){
        //分页查询，传入页码，每页行数
        Page<CebaChargeLog> page= PageHelper.startPage(data.getPage(),data.getLimit());
        List<CebaChargeLog> list=chargeLogMapper.selectListPage(data);
        //分页信息
        PageInfo<CebaChargeLog> pageInfo = new PageInfo<CebaChargeLog>(list);
        data.setPageList(list);
        data.setPageCountRows((int)pageInfo.getTotal());//总条数
        return data;
    }
    
    public CebaErrorLog getErrorLogListPage(CebaErrorLog data){
        //分页查询，传入页码，每页行数
        Page<CebaErrorLog> page= PageHelper.startPage(data.getPage(),data.getLimit());
        List<CebaErrorLog> list=errorLogMapper.selectListPage(data);
        //分页信息
        PageInfo<CebaErrorLog> pageInfo = new PageInfo<CebaErrorLog>(list);
        data.setPageList(list);
        data.setPageCountRows((int)pageInfo.getTotal());//总条数
        return data;
    }
    
    public CebaRefundeLog getRefundeLogListPage(CebaRefundeLog data){
        //分页查询，传入页码，每页行数
        Page<CebaRefundeLog> page= PageHelper.startPage(data.getPage(),data.getLimit());
        List<CebaRefundeLog> list=refundeLogMapper.selectListPage(data);
        //分页信息
        PageInfo<CebaRefundeLog> pageInfo = new PageInfo<CebaRefundeLog>(list);
        data.setPageList(list);
        data.setPageCountRows((int)pageInfo.getTotal());//总条数
        return data;
    }
    
    public CebaSettleLog getSettleLogListPage(CebaSettleLog data){
        //分页查询，传入页码，每页行数
        Page<CebaSettleLog> page= PageHelper.startPage(data.getPage(),data.getLimit());
        List<CebaSettleLog> list=settleLogMapper.selectListPage(data);
        //分页信息
        PageInfo<CebaSettleLog> pageInfo = new PageInfo<CebaSettleLog>(list);
        data.setPageList(list);
        data.setPageCountRows((int)pageInfo.getTotal());//总条数
        return data;
    }


   
}
