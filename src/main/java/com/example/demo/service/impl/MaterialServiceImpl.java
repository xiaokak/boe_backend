package com.example.demo.service.impl;

import com.example.demo.entity.Material;
import com.example.demo.mapper.MaterialMapper;
import com.example.demo.service.MaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoka
 * @since 2022-06-29
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {

}
