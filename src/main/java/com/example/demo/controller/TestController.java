package com.example.demo.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/test")

public class TestController {
    @ResponseBody
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws FileNotFoundException, UnsupportedEncodingException {

        //获取项目classes/static的地址
        String staticPath = ResourceUtils.getURL("classpath:").getPath() + "static";
        staticPath = java.net.URLDecoder.decode(staticPath, "utf-8");
        String fileName = file.getOriginalFilename();  //获取文件名



        // 图片存储目录及图片名称
        String url_path = "imgs" + File.separator + fileName;
        //图片保存路径
        String savePath = staticPath + File.separator + url_path;
        System.out.println("图片保存地址："+savePath);
        // 访问路径=静态资源路径+文件目录路径
        String visitPath ="static/" + url_path;
        System.out.println("图片访问uri："+visitPath);

        File saveFile = new File(savePath);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
        } catch (IOException e) {
            e.printStackTrace();
        }

        return visitPath;
    }

}
