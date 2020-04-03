package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.Picture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PictureController {
    @RequestMapping("getpic")
    public String getpic(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("picture","/Users/yangmengwang/lj.png");
        return jsonObject.toJSONString();
    }
}
