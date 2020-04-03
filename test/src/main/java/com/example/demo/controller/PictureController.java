package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.RRException;
import com.example.demo.model.FastDFSFileEntity;
import com.example.demo.service.FastDFSClient;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
public class PictureController {

    @RequestMapping("getpic")
    public String getpic(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("picture","http://47.93.232.197/group1/M00/00/00/rBgNlF6G18aAJT7OAABcTdol0Zk688.png");
        return jsonObject.toJSONString();
    }

    @RequestMapping("/upload")
    public JSONObject upload(@RequestParam("file") MultipartFile file) throws Exception {
        JSONObject jsonObject=new JSONObject();
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        String url;
        String domainUrl = "";
        if (StringUtils.isNotBlank(domainUrl)){
            url = uploadFile(file,domainUrl);
            jsonObject.put("url",url);
        }else {
            jsonObject.put("error","域名配置为空,请先配置对象存储域名");
        }
        return jsonObject;
    }

    /**
     * @Author maoqitian
     * @Description 上传文件到 FastDFS
     * @Date 2018/10/29 0029 11:11
     * @Param [file]
     * @Param [domainName] 域名
     * @return path 文件访问路径
     **/
    public String uploadFile(MultipartFile file, String domainName) throws Exception {

        String[] fileAbsolutePath={};
        String fileName=file.getOriginalFilename();
        String ext=fileName.substring(fileName.lastIndexOf(".")+1);
        byte[] file_buff=null;
        InputStream inputStream = file.getInputStream();
        if(inputStream!=null){
            int available = inputStream.available();
            file_buff=new byte[available];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFileEntity fastDFSFileEntity=new FastDFSFileEntity(fileName,file_buff,ext);
        try {
            fileAbsolutePath= FastDFSClient.getInstance().upload(fastDFSFileEntity);
        }catch (Exception e){
            throw new RRException("文件上传出错"+e);
        }
        if(fileAbsolutePath == null){
            throw new RRException("文件上传失败，请重新上传");
        }
        String path=domainName+fileAbsolutePath[0]+ "/"+fileAbsolutePath[1];
        return path;
    }


//    public void testUpload() throws Exception {
//
//        //1、把FastDFS提供的jar包添加到工程中
//        //2、初始化全局配置。加载一个配置文件。
//        ClientGlobal.init("F:\\wolf-shop\\src\\main\\resources\\fdfs.conf");
//        //3、创建一个TrackerClient对象。
//        TrackerClient trackerClient = new TrackerClient();
//
//        //4、创建一个TrackerServer对象。
//        TrackerServer trackerServer = trackerClient.getConnection();
//        //5、声明一个StorageServer对象，null。
//        StorageServer storageServer = null;
//        //6、获得StorageClient对象。
//        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//
//        //7、直接调用StorageClient对象方法上传文件即可。
//        String[] result = storageClient.upload_file("F:\\redis_demo\\src\\main\\resources\\logo.png", "png", null);
//        StringBuilder sb = new StringBuilder("http://192.168.112.130/");
//        sb.append(result[0]).append("/").append(result[1]);
//        System.out.println("图片访问地址: "+sb.toString());
//    }


}
