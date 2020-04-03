package com.itguang.resttemplatetest.util;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseToJson {
    public static List<JSONObject> parseListToJson(String sourse){
        List<JSONObject> list =new ArrayList<>();
        //sourse="[{password=123456, id=1, age=20, email=luoji@santi.com, username=逻辑}, {password=123456, id=2, age=22, email=yewenjie@santi.com, username=叶文杰}, {password=123456, id=3, age=26, email=yuntianming@santi.com, username=云天明}, {password=123456, id=4, age=28, email=chengxin@santi.com, username=程心}]";
        sourse=sourse.substring(1);
        sourse=sourse.substring(0,sourse.length()-1);
        String []strs=sourse.split("}, ");
        for (int i = 0; i < strs.length; i++) {
           if (strs[i].contains("{")){
               strs[i]=strs[i].substring(1);
           }
           if (strs[i].contains("}")){
               int len=strs[i].length();
               strs[i]=(strs[i].substring(0,len-1));
           }
           //System.out.println("strs["+i+"]:"+strs[i]);
           String []strs2=strs[i].split(", ");
           JSONObject jsonObject=jsonObject=new JSONObject();
           for (int j = 0; j <strs2.length ; j++) {
               //System.out.println(strs2[j]);
               String []strs3=strs2[j].split("=");
               //System.out.println(strs3[0]+":"+strs3[1]);
               jsonObject.put(strs3[0],strs3[1]);
           }
           //System.out.println(jsonObject.toJSONString());
           list.add(jsonObject);
        }
        return list;
    }
}
