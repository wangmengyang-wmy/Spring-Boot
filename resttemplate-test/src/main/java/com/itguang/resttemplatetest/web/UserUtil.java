package com.itguang.resttemplatetest.web;

import com.alibaba.fastjson.JSONObject;
import com.itguang.resttemplatetest.entity.UserEntity;
import com.itguang.resttemplatetest.util.ParseToJson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author itguang
 * @create 2017-12-17 13:14
 **/
@RestController
public class UserUtil {


    RestTemplate restTemplate = new RestTemplate();

    //无参数的 get 请求
    @RequestMapping("getAll2")
    public List<UserEntity> getAll() {
        List<UserEntity> list = restTemplate.getForObject("http://localhost/getAll", List.class);
        System.out.println(list.toString());
        return list;
    }
    @RequestMapping("getForEntity1")
    public List<UserEntity> getAll2() {
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://localhost/getAll", List.class);
//        HttpHeaders headers = responseEntity.getHeaders();
//        HttpStatus statusCode = responseEntity.getStatusCode();
//        int code = statusCode.value();

        List<UserEntity> list = responseEntity.getBody();
//        for (UserEntity item:list) {
//            System.out.println(item.toString());
//        }
        String str=list.toString();
        List<JSONObject> jsonObjectList= ParseToJson.parseListToJson(str);
        for (int i = 0; i <jsonObjectList.size() ; i++) {
            JSONObject jsonObject = jsonObjectList.get(i);
            Set<String> keys=jsonObject.keySet();
            for (String key:keys) {
                System.out.println("key:"+key+",value:"+jsonObject.get(key));
            }
        }
        System.out.println(list.toString());
        return list;

    }

    @RequestMapping("getForEntity2")
    public List<JSONObject> getAll3() {//
        System.out.println("111");
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://localhost/getAll3",List.class);
        System.out.println("222");
        HttpStatus statusCode = responseEntity.getStatusCode();
        List<JSONObject> list=null;
        if (statusCode.value()==HttpStatus.OK.value()){
            /**
             * 状态码
             */
            int code = statusCode.value();
            System.out.println("status:"+code);
            /**
             * 获取头部封装对象，并从中获取头部信息
             */
            HttpHeaders headers = responseEntity.getHeaders();
            Set<Map.Entry<String,List<String>>> entrySet=headers.entrySet();
            Iterator<Map.Entry<String,List<String>>> iterators=entrySet.iterator();
            while (iterators.hasNext()){
                Map.Entry<String,List<String>> entry=iterators.next();
                String headerName=entry.getKey();
                List<String> list1=entry.getValue();
                StringBuilder builder=new StringBuilder();
                for (String str:list1) {
                    builder.append(str);
                }
                System.out.println("headerName:"+headerName+",value:"+builder.toString());
            }
            System.out.println("header:"+headers.toString());

            /**
             * 获取响应体Json字符串，
             */
            list = responseEntity.getBody();
            String str=responseEntity.getBody().toString();
            List<JSONObject> jsonObjectList= ParseToJson.parseListToJson(str);
            for (int i = 0; i <jsonObjectList.size() ; i++) {
                JSONObject jsonObject = jsonObjectList.get(i);
                Set<String> keys=jsonObject.keySet();
                for (String key:keys) {
                    System.out.println("key:"+key+",value:"+jsonObject.get(key));
                }
            }
            System.out.println("body:"+list.toString());
        }
        return list;
    }

    //有参数的 get 请求
    @RequestMapping("get3/{id}")
    public UserEntity getById(@PathVariable(name = "id") String id) {
        UserEntity userEntity = restTemplate.getForObject("http://localhost/get/{id}", UserEntity.class, id);
        return userEntity;
    }
    //有参数的 get 请求
    @RequestMapping("get4/{id}")
    public JSONObject getById2(@PathVariable(name = "id") String id) {
        JSONObject jsonObject= restTemplate.getForObject("http://localhost/get2/{id}", JSONObject.class, id);
        return jsonObject;
    }

    //有参数的 get 请求
    @RequestMapping("get5/{id}")
    public UserEntity getById3(@PathVariable(name = "id") String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id",id);
        UserEntity userEntity = restTemplate.getForObject("http://localhost/get/{id}", UserEntity.class, map);
        return userEntity;
    }
    //有参数的 get 请求
    @RequestMapping("get6/{id}")
    public JSONObject getById4(@PathVariable(name = "id") String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id",id);
        JSONObject jsonObject= restTemplate.getForObject("http://localhost/get2/{id}", JSONObject.class, id);
        return jsonObject;
    }

    //post 请求,提交 UserEntity 对像
    @RequestMapping("saveUser")
    public String save(UserEntity userEntity) {
        System.out.println("发送用户。。。。。。。。");
        System.out.println("id:"+userEntity.getId());
        System.out.println("username:"+userEntity.getUsername());
        System.out.println("pwd:"+userEntity.getPassword());
        System.out.println("email:"+userEntity.getEmail());
        System.out.println("age:"+userEntity.getAge());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost/save", userEntity, String.class);
        String body = responseEntity.getBody();
        return body;
    }

    //get 请求,提交 UserEntity 对像
    @RequestMapping("saveUser2")
    public String save2(UserEntity userEntity) {
        System.out.println("发送用户。。。。。。。。");
        System.out.println("id:"+userEntity.getId());
        System.out.println("username:"+userEntity.getUsername());
        System.out.println("pwd:"+userEntity.getPassword());
        System.out.println("email:"+userEntity.getEmail());
        System.out.println("age:"+userEntity.getAge());
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost/save2?id="
                +userEntity.getId()+"&username="+userEntity.getUsername()+"&password="+userEntity.getPassword()
                +"&email="+userEntity.getEmail()+"&age="+userEntity.getAge(), String.class);
        String body = responseEntity.getBody();
        return body;
    }


   // 有参数的 postForEntity 请求
    @RequestMapping("saveUserByType/{type}")
    public String save2(UserEntity userEntity,@PathVariable("type")String type) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost/saveByType/{type}", userEntity, String.class, type);
        String body = responseEntity.getBody();
        return body;
    }

    // 有参数的 postForEntity 请求,使用map封装
    @RequestMapping("saveUserByType2/{type}")
    public String save3(UserEntity userEntity,@PathVariable("type")String type) {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", type);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost/saveByType/{type}", userEntity, String.class,map);
        String body = responseEntity.getBody();
        return body;
    }
}
