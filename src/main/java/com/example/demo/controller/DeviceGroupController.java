package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.DeviceGroup;
import com.example.demo.entity.Programs;
import com.example.demo.mapper.DeviceGroupMapper;
import com.example.demo.mapper.ProgramsMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2022-07-01
 */
@RestController
@RequestMapping("/deviceGroup")
public class DeviceGroupController {

    @Resource
    DeviceGroupMapper deviceGroupMapper;

    //新增
    @PostMapping
    public Result<?> save(@RequestBody DeviceGroup deviceGroup){
        deviceGroupMapper.insert(deviceGroup);
        return Result.success();
    }

    //获取or查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam (defaultValue = "10") Integer pageSize,
                              @RequestParam (defaultValue = "") String search){
        LambdaQueryWrapper<DeviceGroup> wrapper =  Wrappers.<DeviceGroup>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(DeviceGroup::getGroupName,search);
        }
        Page<DeviceGroup> programsPage = (Page<DeviceGroup>) deviceGroupMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);

        return Result.success(programsPage);
    }
    //修改
    @PutMapping("/update")
    public Result<?> update(@RequestBody DeviceGroup deviceGroup){
        deviceGroupMapper.updateById(deviceGroup);
        return Result.success();
    }
}
