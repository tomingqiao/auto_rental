package com.coder.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coder.rental.entity.AutoBrand;
import com.coder.rental.mapper.AutoBrandMapper;
import com.coder.rental.service.IAutoBrandService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
@Service
public class AutoBrandServiceImpl extends ServiceImpl<AutoBrandMapper, AutoBrand> implements IAutoBrandService {

    @Override
    public Page<AutoBrand> searchByPage(Page<AutoBrand> page, AutoBrand autoBrand) {
        return baseMapper.searchByPage(page, autoBrand);
    }

    @Override
    public List<AutoBrand> selectByMakerId(Integer id) {
        QueryWrapper<AutoBrand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mid", id);
        return baseMapper.selectList(queryWrapper);
    }
}
