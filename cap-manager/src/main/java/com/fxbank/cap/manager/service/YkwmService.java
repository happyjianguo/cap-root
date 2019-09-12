package com.fxbank.cap.manager.service;

import com.fxbank.cap.manager.entity.YkwmErrorlog;
import com.fxbank.cap.manager.entity.YkwmSettlelog;
import com.fxbank.cap.manager.entity.YkwmTracelog;
import com.fxbank.cap.manager.mapper.YkwmErrorlogMapper;
import com.fxbank.cap.manager.mapper.YkwmSettlelogMapper;
import com.fxbank.cap.manager.mapper.YkwmTracelogMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class YkwmService {

    @Resource
    private YkwmTracelogMapper ykwmTracelogMapper;

    @Resource
    private YkwmErrorlogMapper ykwmErrorlogMapper;
    
    @Resource
    private YkwmSettlelogMapper ykwmSettlelogMapper;


    public YkwmTracelog getCharegeLogListPage(YkwmTracelog data){
        //分页查询，传入页码，每页行数
        Page<YkwmTracelog> page= PageHelper.startPage(data.getPage(),data.getLimit());
        List<YkwmTracelog> list=ykwmTracelogMapper.selectListPage(data);
        //分页信息
        PageInfo<YkwmTracelog> pageInfo = new PageInfo<YkwmTracelog>(list);
        data.setPageList(list);
        data.setPageCountRows((int)pageInfo.getTotal());//总条数
        return data;
    }
    
    public YkwmErrorlog getErrorLogListPage(YkwmErrorlog data){
        //分页查询，传入页码，每页行数
        Page<YkwmErrorlog> page= PageHelper.startPage(data.getPage(),data.getLimit());
        List<YkwmErrorlog> list=ykwmErrorlogMapper.selectListPage(data);
        //分页信息
        PageInfo<YkwmErrorlog> pageInfo = new PageInfo<YkwmErrorlog>(list);
        data.setPageList(list);
        data.setPageCountRows((int)pageInfo.getTotal());//总条数
        return data;
    }
    
    public YkwmSettlelog getSettleLogListPage(YkwmSettlelog data){
        //分页查询，传入页码，每页行数
        Page<YkwmSettlelog> page= PageHelper.startPage(data.getPage(),data.getLimit());
        List<YkwmSettlelog> list=ykwmSettlelogMapper.selectListPage(data);
        //分页信息
        PageInfo<YkwmSettlelog> pageInfo = new PageInfo<YkwmSettlelog>(list);
        data.setPageList(list);
        data.setPageCountRows((int)pageInfo.getTotal());//总条数
        return data;
    }


   
}
