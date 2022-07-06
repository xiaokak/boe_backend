package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Device;
import com.example.demo.entity.Programs;
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
@RequestMapping("/programs")
public class ProgramsController {

    @Resource
    ProgramsMapper programsMapper;

    //新增
    @PostMapping
    public Result<?> save(@RequestBody Programs programs){
        programs.setUpdateDate(LocalDateTime.now());
        programsMapper.insert(programs);
        return Result.success();
    }

    //获取or查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam (defaultValue = "10") Integer pageSize,
                              @RequestParam (defaultValue = "") String search){
        LambdaQueryWrapper<Programs> wrapper =  Wrappers.<Programs>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Programs::getProgramName,search);
        }
        Page<Programs> programsPage = (Page<Programs>) programsMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);

        return Result.success(programsPage);
    }
    //修改
    @PutMapping("/update")
    public Result<?> update(@RequestBody Programs programs){
        programs.setUpdateDate(LocalDateTime.now());
        programsMapper.updateById(programs);
        return Result.success();
    }
    //android获取
    @GetMapping("/android")
    public Programs getProgram(@RequestParam String id){
        Programs res = programsMapper.selectOne(Wrappers.<Programs>lambdaQuery().eq(Programs::getId,id));
        return res;
    }

}
