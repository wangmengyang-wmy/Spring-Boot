package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.User;
import com.example.demo.utils.AESUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AjaxController {
    @RequestMapping("test")
    public ModelAndView request_test(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("test");
        return modelAndView;
    }

    @RequestMapping("getPassword")
    public String Encode(@RequestBody User user,HttpServletRequest request){
        System.out.println("ajax发送请求");
        JSONObject jsonObject=new JSONObject();
        String AESEncode=AESUtil.AESEncode(user.getPassword());
        System.out.println("加密的密码"+AESEncode);
        jsonObject.put("AESEncode", AESEncode);
        HttpSession session=request.getSession();
        user.setPassword(AESEncode);
        session.setAttribute("user",user);
        return jsonObject.toJSONString();
    }

    @RequestMapping("result")
    public JSONObject Decode(@RequestBody User user,HttpServletRequest request){
        String AESDncode=AESUtil.AESDecode(user.getPassword());
        System.out.println(AESDncode);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("AESDncode",AESDncode);
        HttpSession session=request.getSession();
        user.setPassword(user.getPassword()+AESDncode);
        session.setAttribute("user",user);
        return jsonObject;
    }
    @RequestMapping("result.html")
    public ModelAndView result(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("result");
        User user=(User)request.getSession().getAttribute("user");
        modelAndView.addObject("user",user);
        System.out.println("user"+user.toString());
        return modelAndView;
    }

    @RequestMapping("async.html")
    public ModelAndView async_page(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("async");
        return modelAndView;
    }

    @RequestMapping("testasync")
    public String async(@RequestBody User user){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("username",user.getUsername());
        jsonObject.put("password",AESUtil.AESEncode(user.getPassword()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }
}
