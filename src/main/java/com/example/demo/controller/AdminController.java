package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Login;
import com.example.demo.mapper.AdminMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 *  admin
 * </p>
 *
 * @author xiaoka
 * @since 2022-06-28
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminMapper adminMapper;
    //新增or注册
    @PostMapping
    public Result<?> save(@RequestBody Admin admin){
//        if(admin.getId() == null){
//            admin.setPwd("123456");
//        }
        adminMapper.insert(admin);
        return Result.success();
    }
    //更新
    @PutMapping("/update")
    public Result<?> update(@RequestBody Admin admin){
        adminMapper.updateById(admin);
        return Result.success();
    }
//    @DeleteMapping("/{id}")
//    public Result<?> delete(@PathVariable Long id){
//        adminMapper.deleteById(id);
//        return Result.success();
//    }

    //禁用or启用
    @PutMapping("/banded")
    public Result<?> banded(@RequestBody Admin admin){
        adminMapper.updateById(admin);
        return Result.success();
    }

    //查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam (defaultValue = "10") Integer pageSize,
                              @RequestParam (defaultValue = "") String search){
        LambdaQueryWrapper<Admin> wrapper =  Wrappers.<Admin>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Admin::getAccountName,search);
        }
        Page<Admin> userPage = (Page<Admin>) adminMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(userPage);
    }
    //登录
    @PostMapping("/login")
    public Result<?> login(@RequestBody Login login){
//        System.out.print(login.getAccountName());
//        System.out.print(login.getPassword());
        Admin res = adminMapper.selectOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getAccountName,login.getAccountName()).eq(Admin::getPassword,login.getPassword()));
        if(res == null){
            return Result.error("-1","用户名密码错误！");
        }
        else if(Objects.equals(res.getEnable(), "禁用")){
            return Result.error("-1","该用户已被禁用!");
        }
        return Result.success(res);
    }
}
