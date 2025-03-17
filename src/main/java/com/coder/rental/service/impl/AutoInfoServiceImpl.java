package com.coder.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoInfo;
import com.coder.rental.mapper.AutoInfoMapper;
import com.coder.rental.service.IAutoInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
public class AutoInfoServiceImpl extends ServiceImpl<AutoInfoMapper, AutoInfo> implements IAutoInfoService {

    @Resource
    private AutoInfoMapper autoInfoMapper;

    @Override
    public Page<AutoInfo> search(int start, int size, AutoInfo autoInfo) {
        Page<AutoInfo> page = new Page<>(start, size);
        return autoInfoMapper.searchByPage(page, autoInfo);
    }

    @Override
    public boolean delete(String ids) {
        List<Integer> list = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        if (!list.isEmpty()){
            return autoInfoMapper.deleteBatchIds(list) > 0;
        }
        return false;
    }

    @Override
    public Integer getIdByNum(String autoNum) {
        QueryWrapper<AutoInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("auto_num", autoNum);
        return baseMapper.selectOne(queryWrapper).getId();
    }

    @Override
    public AutoInfo getByAutoNum(String autoNum) {
        QueryWrapper<AutoInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("auto_num", autoNum);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<AutoInfo> toBeMaintain() {
        return baseMapper.toBeMaintain();
    }
}
