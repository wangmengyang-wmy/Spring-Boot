package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.demo.model.Company;
import com.example.demo.utils.AESUtil;
import com.example.demo.utils.HttpUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @RequestMapping("/oauth/2.0/token")
    public void AT(HttpServletResponse response) {
        response.addHeader("access_token", "a6b7dbd48f731035f771b8d63f6");
        response.addHeader("refresh_token", "385d55f8615dfd9edb7c4b5ebd");
        response.setStatus(200);
        response.setHeader("content-type", "text/html;charset=UTF-8");

        response.setBufferSize(32);
        int buffer=response.getBufferSize();

        String result="缓存区大小："+buffer;
        System.out.println("缓存区大小："+buffer);
        byte[] dataByteArr=null;
        try {
            dataByteArr= result.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            response.getOutputStream().write(dataByteArr);
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//        Map<String, List<String>> map=new HashMap<>();
//        List<String> var1=new ArrayList<>();
//        var1.add("bytes");
//        map.put("accept-ranges",var1);
//
//        List<String> var2=new ArrayList<>();
//        var2.add("144502");
//        map.put("age",var2);
//
//        List<String> var3=new ArrayList<>();
//        var3.add("123456789");
//        map.put("var3",var3);
//
//        List<String> var4=new ArrayList<>();
//        var4.add("987654321");
//        map.put("var4",var4);
//
//        Set <Map.Entry<String, List<String>>> set=map.entrySet();
//        for (Map.Entry<String, List<String>> entry :  map.entrySet()) {
//            String headerName = entry.getKey();
//            System.out.println("headerName"+headerName);
//            if ("Content-Length".equals(headerName)) {
//                continue;
//            }
//            List<String> values = entry.getValue();
//            for (String value : values) {
//                response.addHeader(headerName, value);
//            }
//        }
//        boolean containsHeader=response.containsHeader("var4");
//        System.out.println(containsHeader);
//        containsHeader=response.containsHeader("var5");
//        System.out.println(containsHeader);
//        try {
//            response.sendError(404,"404tyrfucgfdhjbkbjhc");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        response.setHeader("var4",response.getHeader("var4")+"1");
//        response.setStatus(200);
//        return new ResponseEntity(HttpStatus.OK);
//        Cookie cookie=new Cookie("c1","cookie");
//        response.addCookie(cookie);
//        return result.toJSONString();
//    }

    /**
     * 直接返回json字符串
     */
    @RequestMapping("/stringtest")
    public String string_test() {
        JSONObject result = new JSONObject();
        result.put("access_token", "a6b7dbd48f731035f771b8d63f6");
        result.put("refresh_token", "385d55f8615dfd9edb7c4b5ebd");
        result.put("expires_in", 86400);
        return result.toJSONString();
    }

    /**
     * 获取get方式的请求参数，@RequestParam注解映射得到参数值
     * @return  返回json字符串
     */
    @RequestMapping("/stringtest2")
    public String string_test2(@RequestParam("grant_type") String grant_type, @RequestParam("code") String code,
                               @RequestParam("client_id") String client_id, @RequestParam("client_secret") String client_secret) {
        System.out.println("grant_type:" + grant_type + "\tcode:" + code + "\tclient_id:" + client_id + "\tclient_secret:" + client_secret);
        JSONObject result = new JSONObject();
        result.put("grant_type", grant_type);
        result.put("code", code);
        result.put("client_id", client_id);
        result.put("client_secret", client_secret);
        return result.toJSONString();
    }

    @RequestMapping("/requestEntityTest")
    public String AT(@RequestParam("grant_type") String grant_type,
                             @RequestParam("code") String code,
                             @RequestParam("client_id") String client_id,
                             @RequestParam("client_secret") String client_secret) {

//        JSONObject postData=new JSONObject();
//        postData.put("grant_type", grant_type);
//        postData.put("code", code);
//        postData.put("client_id", client_id);
//        postData.put("client_secret", client_secret);
//        System.out.println("postData:"+postData.toJSONString());

        Company company=new Company();
        company.setGrant_type(grant_type);
        company.setClient_id(client_id);
        company.setCode(code);
        company.setClient_secret(client_secret);

        RestTemplate client = new RestTemplate();

        String url="http://localhost/responseEntityTest";
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //header添加不会覆盖，只会使用第一个值
        headers.add("1","a");
        //header set修改值
        headers.set("1","d");
        headers.add("list","a=A;b=B;c=C");
        //设置请求发送方式
        HttpMethod method = HttpMethod.POST;
        //以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        ResponseEntity<JSONObject> response = client.postForEntity(url, new HttpEntity<Company>(company,headers),JSONObject.class);
        JSONObject jsonObject=response.getBody();
        System.out.println(jsonObject.toString());
        return jsonObject.toJSONString();
    }

//    @RequestMapping("/HttpEntity")
//    public String entity_to_json() {
//        String url="http://127.0.0.1/HttpEntityTest";
//        String entityString="";
//        CloseableHttpResponse httpResponse=HttpUtil.post();
//        int statusCode = httpResponse.getStatusLine().getStatusCode();
//        HttpEntity entity = (HttpEntity) httpResponse.getEntity();
//        System.out.println(entity);
//        String responseEntity = entity.toString();
//        System.out.println(responseEntity);
//        JSONObject jsonObject = JSON.parseObject(responseEntity);
//        System.out.println(jsonObject);
//        String responseCode = jsonObject.getString("code");
//        String responseMessage = jsonObject.getString("message");
//        //由于info的值是json格式(或可理解为key-value集合)，提取info的值为JSONObject格式
//        JSONObject infoObject = jsonObject.getJSONObject("info");
//        //重复步骤6，提取orderId
//        String orderId= jsonObject.getString("orderId");
//        //或通过将infoObject转化为HashMap，再进行提取orderId
//        Map <String, Object> info = new HashMap<>();
//        info = JSON.parseObject(String.valueOf(infoObject), new TypeReference<HashMap<String, Object>>() {});
//        orderId = info.get("orderId").toString();
//        return "success";
//    }
//    @RequestMapping("/HttpEntityTest")
//    public CloseableHttpResponse entity_to_json_test() {
//        return null;
//    }
    @RequestMapping("/HttpEntity")
    public String test(){
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> stringResponseEntity=restTemplate.getForEntity("www.baidu.com",String.class);
        return null;
    }
}
