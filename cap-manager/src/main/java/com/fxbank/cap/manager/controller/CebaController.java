package com.fxbank.cap.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.manager.entity.CebaChargeLog;
import com.fxbank.cap.manager.entity.CebaErrorLog;
import com.fxbank.cap.manager.entity.CebaRefundeLog;
import com.fxbank.cap.manager.entity.CebaSettleLog;
import com.fxbank.cap.manager.service.CebaService;
import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.cip.pub.service.IPublicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.List;

@Controller
public class CebaController {

    private static Logger logger = LoggerFactory.getLogger(CebaController.class);

    @Reference(version = "1.0.0")
    private IPublicService publicService;

    @Resource
    private CebaService service;

    @Resource
    private MyJedis myJedis;

    private final static String BRTEL_PREFIX = "ceba.";

    @RequestMapping("/ceba/chargeLogList")
    public String list(Model model,CebaChargeLog data){
        model.addAttribute("data",data);
        return "ceba/charge_log";
    }
    
    @RequestMapping("/ceba/errorLogList")
    public String list1(Model model,CebaErrorLog data){
        model.addAttribute("data",data);
        return "ceba/error_log";
    }
    
    @RequestMapping("/ceba/refundeLogList")
    public String list2(Model model,CebaRefundeLog data){
        model.addAttribute("data",data);
        return "ceba/refunde_log";
    }
    
    @RequestMapping("/ceba/settleLogList")
    public String list3(Model model,CebaSettleLog data){
        model.addAttribute("data",data);
        return "ceba/settle_log";
    }

    @ResponseBody
    @RequestMapping("/ceba/chargeLogData")
    public String chargeListData(CebaChargeLog data){
        data=service.getCharegeLogListPage(data);
        List<CebaChargeLog> list=data.getPageList();
        JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(list));
//        JSONArray jsonArray=new JSONArray();
        JSONObject json=new JSONObject();
        json.put("msg","");
        json.put("code",0);
        json.put("count",data.getPageCountRows());
        json.put("data",jsonArray.toArray());
        return json.toString();
    }

    @ResponseBody
    @RequestMapping("/ceba/errorLogData")
    public String errorListData(CebaErrorLog data){
        data=service.getErrorLogListPage(data);
        List<CebaErrorLog> list=data.getPageList();
        JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(list));
//        JSONArray jsonArray=new JSONArray();
        JSONObject json=new JSONObject();
        json.put("msg","");
        json.put("code",0);
        json.put("count",data.getPageCountRows());
        json.put("data",jsonArray.toArray());
        return json.toString();
    }
    
    @ResponseBody
    @RequestMapping("/ceba/refundeLogData")
    public String refundeListData(CebaRefundeLog data){
        data=service.getRefundeLogListPage(data);
        List<CebaRefundeLog> list=data.getPageList();
        JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(list));
//        JSONArray jsonArray=new JSONArray();
        JSONObject json=new JSONObject();
        json.put("msg","");
        json.put("code",0);
        json.put("count",data.getPageCountRows());
        json.put("data",jsonArray.toArray());
        return json.toString();
    }
    
    @ResponseBody
    @RequestMapping("/ceba/settleLogData")
    public String settleListData(CebaSettleLog data){
        data=service.getSettleLogListPage(data);
        List<CebaSettleLog> list=data.getPageList();
        JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(list));
//        JSONArray jsonArray=new JSONArray();
        JSONObject json=new JSONObject();
        json.put("msg","");
        json.put("code",0);
        json.put("count",data.getPageCountRows());
        json.put("data",jsonArray.toArray());
        return json.toString();
    }

}
