package com.example.demo.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    public static CloseableHttpResponse post(String url , String entityString , HashMap<String , String> headermap)
            throws ClientProtocolException, IOException {
        //创建一个可关闭的 httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个HttpPost的请求对象
        HttpPost httpPost = new HttpPost(url);
        //设置payload
        httpPost.setEntity(new StringEntity(entityString));
        //加载请求头到HttpPost对象
        for (Map.Entry<String , String> entry : headermap.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        //发送post请求
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        return httpResponse;
    }
}
