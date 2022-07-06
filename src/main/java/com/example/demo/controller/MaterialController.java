package com.example.demo.controller;



import cn.hutool.core.io.FileUtil;
//import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
//import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Material;
//import lombok.Value;
import com.example.demo.mapper.MaterialMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2022-06-29
 */
@RestController
@RequestMapping("/material")
public class MaterialController {

    @Resource
    MaterialMapper materialMapper;

    @Value("${file.path}")
    private String fileUploadPath;

    //上传
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {

        Material upload = new Material();
        upload.setFileName(file.getOriginalFilename());
        upload.setType(FileUtil.extName(upload.getFileName()));
        upload.setSize(file.getSize());

        //去表里查询
    Material material = materialMapper.selectOne(Wrappers.<Material>lambdaQuery().eq(Material::getFileName,upload.getFileName()));
        if(material != null){
        return "文件重复!";
    }

        //获取项目classes/static的地址
        String staticPath = ResourceUtils.getURL("classpath:").getPath() + "static";
        staticPath = java.net.URLDecoder.decode(staticPath, "utf-8");
//        String fileName = file.getOriginalFilename();  //获取文件名

        //配置一个随机uuid
        String uuid = IdUtil.fastSimpleUUID();
        //拼写新的文件名
        String newName = uuid+StrUtil.DOT+upload.getType();
        // 图片存储目录及图片名称
        String url_path = File.separator + newName;

        //图片保存路径
        String savePath = fileUploadPath + File.separator + url_path;
        System.out.println("图片保存地址："+savePath);
        // 访问路径=静态资源路径+文件目录路径
//        String visitPath ="static/" + url_path;
//        System.out.println("图片访问uri："+visitPath);

        File saveFile = new File(savePath);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
        } catch (IOException e) {
            e.printStackTrace();
        }
            //存储数据库
        upload.setCreateDate(LocalDateTime.now());
//        String url = "http://localhost:3000/material/"+uuid;
        String url = newName;
        upload.setUrl(url);
        materialMapper.insert(upload);

        return upload.getUrl();
    }

//    下载
    @GetMapping("/{url}")
    public void download(@PathVariable String url, HttpServletResponse response) throws IOException{
        //获取项目classes/static的地址
        String staticPath = ResourceUtils.getURL("classpath:").getPath() + "static";
        staticPath = java.net.URLDecoder.decode(staticPath, "utf-8");
        //根据文件标识获取文件
//        File uploadFile = new File(fileUploadPath + url);
        // 图片存储目录及图片名称
        String url_path = "imgs" + File.separator + url;
        String savePath = staticPath + File.separator + url_path;
        //获取文件
        File uploadFile = new File(savePath);
        //设置输出流格式
        ServletOutputStream os = response.getOutputStream();
//        String ss = URLEncoder.encode(url, "utf-8");
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(url,"UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    //获取or  搜索
    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam (defaultValue = "10") Integer pageSize,
                              @RequestParam (defaultValue = "") String search,
                              @RequestParam (defaultValue = "0") Integer isDelete){
        LambdaQueryWrapper<Material> wrapper =  Wrappers.<Material>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Material::getFileName,search);
//            wrapper.and(i->i.like(Material::getFileName,search).like(Material::getIsDelete,isDelete));
//            wrapper.and(i->i.like(Material::getIsDelete,isDelete));
        }

        Page<Material> filePage = (Page<Material>) materialMapper.selectPage(new Page<>(pageNum,pageSize),wrapper.eq(Material::getIsDelete,0));
        return Result.success(filePage);
    }


    //修改
    @PutMapping("/update")
    public Result<?> banded(@RequestBody Material material){
        materialMapper.updateById(material);
        return Result.success();
    }

    //逻辑删除
    @PutMapping("/delete")
    public Result<?> logicalDelete(@RequestBody Material material1){
        Material material = materialMapper.selectById(material1.getId());
        material.setIsDelete(1);
        materialMapper.updateById(material);
        return Result.success();
    }


    //真正删除
    @PutMapping("/realDelete")
    public String realDelete(@RequestParam String url) throws FileNotFoundException, UnsupportedEncodingException {
        //获取项目classes/static的地址
        String staticPath = ResourceUtils.getURL("classpath:").getPath() + "static";
        staticPath = java.net.URLDecoder.decode(staticPath, "utf-8");

        String deletePath = staticPath+File.separator+ "imgs" +File.separator+ url;
        return deletePath;
//        return Result.success();
    }
}
