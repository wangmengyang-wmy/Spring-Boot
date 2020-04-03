package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.Company;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
public class TestController2 {

    @RequestMapping("/responseEntityTest")
    public JSONObject responseEntityTest(@RequestBody Company company, HttpServletRequest request) {
        Enumeration<String> headerNames=request.getHeaderNames();
        System.out.println("Header:");
        while (headerNames.hasMoreElements()){
            String parame=headerNames.nextElement();
            System.out.println(parame+":"+request.getHeader(parame));
        }

        System.out.println("Body:");
//        Enumeration<String> requestNames=  request.getParameterNames();
//        while (requestNames.hasMoreElements()){
//            String parame=requestNames.nextElement();
//            System.out.println(parame+":"+request.getParameter(parame));
//            jsonObject.put(parame,request.getParameter(parame));
//        }

        JSONObject jsonObject=new JSONObject();
        Set<String> keys=jsonObject.keySet();
        jsonObject.put("grant_type",company.getGrant_type());
        jsonObject.put("client_id",company.getClient_id());
        jsonObject.put("client_secret",company.getClient_secret());
        jsonObject.put("code",company.getCode());
        System.out.println("key:value");
        for (String item:keys) {
            System.out.println(item+":"+jsonObject.get(item));
        }

        return jsonObject;
    }
    @RequestMapping("/getTest")
    public String test_get(@RequestParam(name ="grant_type") String grant_type,
                           @RequestParam(name ="code") String code,
                           @RequestParam(name ="client_id") String client_id,
                           @RequestParam(name ="client_secret") String client_secret,
                           @RequestHeader HttpHeaders headers,
                           HttpServletRequest request){
        System.out.println("grant_type:"+grant_type);
        System.out.println("code:"+code);
        System.out.println("client_id:"+client_id);
        System.out.println("client_secret:"+client_secret);
        Set<Map.Entry<String, List<String>>> entrySet=headers.entrySet();
        System.out.println("1");
        for (Map.Entry<String, List<String>> entry :  entrySet) {
            String headerName = entry.getKey();
            List<String> values = entry.getValue();
            System.out.println("headerName:"+headerName+",value:"+values.toString());
        }

        System.out.println("2");
        Enumeration<String> headerNames=request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String values = request.getHeader(headerName);
            System.out.println("headerName:"+headerName+",value:"+values.toString());
        }
        return "success";
    }
    @RequestMapping("/postTest")
    public String test_post(Company company,@RequestHeader HttpHeaders headers,
                            HttpServletRequest request){
        System.out.println("grant_type:"+company.getGrant_type());
        System.out.println("code:"+company.getCode());
        System.out.println("client_id:"+company.getClient_id());
        System.out.println("client_secret:"+company.getClient_secret());
        Set<Map.Entry<String, List<String>>> entrySet=headers.entrySet();
        System.out.println("1");
        for (Map.Entry<String, List<String>> entry :  entrySet) {
            String headerName = entry.getKey();
            List<String> values = entry.getValue();
            System.out.println("headerName:"+headerName+",value:"+values.toString());
        }

        System.out.println("2");
        Enumeration<String> headerNames=request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String values = request.getHeader(headerName);
            System.out.println("headerName:"+headerName+",value:"+values.toString());
        }
        return "success";
    }
}
