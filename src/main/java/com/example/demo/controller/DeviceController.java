package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Device;
import com.example.demo.mapper.DeviceMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  设备
 * </p>
 *
 * @author xiaoka
 * @since 2022-06-29
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Resource
    DeviceMapper deviceMapper;

    //更新
    @PutMapping("/update")
    public Result<?> update(@RequestBody Device device){
        deviceMapper.updateById(device);
        return Result.success();
    }
//    @DeleteMapping("/{id}")
//    public Result<?> delete(@PathVariable Long id){
//        adminMapper.deleteById(id);
//        return Result.success();
//    }

    //禁用or启用
//    @PutMapping("/banded")
//    public Result<?> banded(@RequestBody Admin admin){
//        adminMapper.updateById(admin);
//        return Result.success();
//    }

    //查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam (defaultValue = "10") Integer pageSize,
                              @RequestParam (defaultValue = "") String search){
        LambdaQueryWrapper<Device> wrapper =  Wrappers.<Device>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Device::getDeviceName,search);
        }
        Page<Device> devicePage = (Page<Device>) deviceMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);

        return Result.success(devicePage);
    }
}