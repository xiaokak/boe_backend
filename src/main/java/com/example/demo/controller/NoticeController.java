package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Notice;
import com.example.demo.entity.Programs;
import com.example.demo.mapper.NoticeMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaoka
 * @since 2022-07-03
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    NoticeMapper noticeMapper;

    @PostMapping
    public Result<?> save(@RequestBody Notice notice){
        noticeMapper.insert(notice);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam (defaultValue = "10") Integer pageSize,
                              @RequestParam (defaultValue = "") String search){
        LambdaQueryWrapper<Notice> wrapper =  Wrappers.<Notice>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Notice::getNoticeTitle,search);
        }
        Page<Notice> noticePage = (Page<Notice>) noticeMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);

        return Result.success(noticePage);
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody Notice notice){
        noticeMapper.updateById(notice);
        return Result.success();
    }

    //android获取
    @GetMapping("/android")
    public Notice getNotice(@RequestParam String id){
        Notice res = noticeMapper.selectOne(Wrappers.<Notice>lambdaQuery().eq(Notice::getId,id));
        return res;
    }
}
