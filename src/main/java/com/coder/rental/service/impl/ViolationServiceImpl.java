package com.coder.rental.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Violation;
import com.coder.rental.mapper.ViolationMapper;
import com.coder.rental.service.IViolationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class ViolationServiceImpl extends ServiceImpl<ViolationMapper, Violation> implements IViolationService {

    @Override
    public Page<Violation> searchByPage(int start, int size, Violation violation) {
        Page<Violation> page = new Page<>(start, size);
        QueryWrapper<Violation> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(violation.getAutoNum()), "auto_num", violation.getAutoNum());
        queryWrapper.like(StrUtil.isNotEmpty(violation.getReason()), "reason", violation.getReason());
        queryWrapper.like(StrUtil.isNotEmpty(violation.getLocation()), "location", violation.getLocation());
        queryWrapper.eq(ObjectUtil.isNotEmpty(violation.getStatus()), "status", violation.getStatus());
        queryWrapper.le(ObjectUtil.isNotEmpty(violation.getHighViolationTime()), "violation_time", violation.getHighViolationTime());
        queryWrapper.ge(ObjectUtil.isNotEmpty(violation.getLowViolationTime()), "violation_time", violation.getLowViolationTime());
        queryWrapper.le(ObjectUtil.isNotEmpty(violation.getHighFine()), "fine", violation.getHighFine());
        queryWrapper.ge(ObjectUtil.isNotEmpty(violation.getLowFine()), "fine", violation.getLowFine());
        queryWrapper.orderByAsc("violation_time");
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean delete(String ids) {
        List<Integer> list = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        if (!list.isEmpty()){
            return baseMapper.deleteBatchIds(list) > 0;
        }
        return false;
    }
}
