package com.fxbank.cap.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fxbank.cap.manager.entity.YkwmErrorlog;
import com.fxbank.cap.manager.entity.YkwmSettlelog;
import com.fxbank.cap.manager.entity.YkwmTracelog;
import com.fxbank.cap.manager.service.YkwmService;
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
public class YkwmController {

    private static Logger logger = LoggerFactory.getLogger(YkwmController.class);

    @Reference(version = "1.0.0")
    private IPublicService publicService;

    @Resource
    private YkwmService service;

    @Resource
    private MyJedis myJedis;

    private final static String COMMON_PREFIX = "ykwm.";

    @RequestMapping("/ykwm/tracelogList")
    public String list(Model model,YkwmTracelog data){
        model.addAttribute("data",data);
        return "ykwm/tracelog";
    }
    
    @RequestMapping("/ykwm/errorlogList")
    public String list1(Model model,YkwmErrorlog data){
        model.addAttribute("data",data);
        return "ykwm/errorlog";
    }
    
    @RequestMapping("/ykwm/settlelogList")
    public String list2(Model model,YkwmSettlelog data){
        model.addAttribute("data",data);
        return "ykwm/settlelog";
    }

    @ResponseBody
    @RequestMapping("/ykwm/tracelogData")
    public String chargeListData(YkwmTracelog data){
        data=service.getCharegeLogListPage(data);
        List<YkwmTracelog> list=data.getPageList();
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
    @RequestMapping("/ykwm/errorlogData")
    public String errorListData(YkwmErrorlog data){
        data=service.getErrorLogListPage(data);
        List<YkwmErrorlog> list=data.getPageList();
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
    @RequestMapping("/ykwm/settlelogData")
    public String settleListData(YkwmSettlelog data){
        data=service.getSettleLogListPage(data);
        List<YkwmSettlelog> list=data.getPageList();
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
