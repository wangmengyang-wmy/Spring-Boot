package com.itguang.resttemplatetest.web;

import com.alibaba.fastjson.JSONObject;
import com.itguang.resttemplatetest.entity.UserEntity;
import com.itguang.resttemplatetest.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author itguang
 * @create 2017-12-17 10:37
 **/
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "getAll")
    public ResponseEntity<List<UserEntity>> getUser(HttpServletResponse response) {
        List<UserEntity> list = userService.getAll();
        HttpHeaders headers=new HttpHeaders();
        headers.add("1","a");
        headers.add("2","b");
        headers.add("3","c");
        headers.add("4","d");
        HttpStatus status=HttpStatus.OK;
        ResponseEntity<List<UserEntity>> responseEntity=new ResponseEntity<>(list,headers,status);

//        response.setStatus(400);
        return responseEntity;
    }

    @RequestMapping(value = "getAll3")
    public ResponseEntity<List<JSONObject>> getUser2() {
        List<UserEntity> list = userService.getAll();
        List<JSONObject> jsonObjects=new ArrayList<>();
        for (UserEntity item:list) {
            JSONObject jsonObject=new JSONObject();
            String username=item.getUsername();
            jsonObject.put("username",username);
            String password=item.getPassword();
            jsonObject.put("password",password);
            int age=item.getAge();
            jsonObject.put("age",age);
            String email=item.getEmail();
            jsonObject.put("email",email);
            String id=item.getId();
            jsonObject.put("id",id);
            System.out.println(jsonObject.toJSONString());
            jsonObjects.add(jsonObject);
        }
        HttpHeaders headers=new HttpHeaders();
        headers.add("1","a");
        headers.add("2","b");
        headers.add("3","c");
        headers.add("4","d");
        HttpStatus status=HttpStatus.OK;
        ResponseEntity<List<JSONObject>> responseEntity=new ResponseEntity<>(jsonObjects,headers,status);

//        response.setStatus(400);
        return responseEntity;
    }

    @RequestMapping("get/{id}")
    public UserEntity getById(@PathVariable(name = "id") String id) {
        return userService.getById(id);
    }

    @RequestMapping("get2/{id}")
    public JSONObject getById2(@PathVariable(name = "id") String id) {
        UserEntity userEntity= userService.getById(id);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("id",userEntity.getId());
        jsonObject.put("age",userEntity.getAge());
        jsonObject.put("username",userEntity.getUsername());
        jsonObject.put("email",userEntity.getEmail());
        jsonObject.put("pwd",userEntity.getPassword());
        return jsonObject;
    }


    @RequestMapping(value = "save")
    public String save(@RequestBody UserEntity userEntity) {
        System.out.println("正在save。。。。。。。。");
        System.out.println("id:"+userEntity.getId());
        System.out.println("username:"+userEntity.getUsername());
        System.out.println("pwd:"+userEntity.getPassword());
        System.out.println("email:"+userEntity.getEmail());
        System.out.println("age:"+userEntity.getAge());
        return "保存成功";
    }

    @RequestMapping(value = "save2")
    public String save2(HttpServletRequest request,UserEntity userEntity) {
        System.out.println("请求路径："+request.getRequestURI());
        System.out.println("正在save。。。。。。。。");
        System.out.println("id:"+userEntity.getId());
        System.out.println("username:"+userEntity.getUsername());
        System.out.println("pwd:"+userEntity.getPassword());
        System.out.println("email:"+userEntity.getEmail());
        System.out.println("age:"+userEntity.getAge());
        return "保存成功";
    }



    @RequestMapping(value = "saveByType/{type}")
    public String saveByType(UserEntity userEntity,@PathVariable("type")String type) {

        return "保存成功,type="+type;
    }




}
