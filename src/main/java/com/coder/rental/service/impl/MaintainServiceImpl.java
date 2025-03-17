package com.coder.rental.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoInfo;
import com.coder.rental.entity.Maintain;
import com.coder.rental.mapper.AutoInfoMapper;
import com.coder.rental.mapper.MaintainMapper;
import com.coder.rental.service.IMaintainService;
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
public class MaintainServiceImpl extends ServiceImpl<MaintainMapper, Maintain> implements IMaintainService {

    @Resource
    private AutoInfoMapper autoInfoMapper;

    @Override
    public Page<Maintain> search(Page<Maintain> page, Maintain maintain) {
        QueryWrapper<Maintain> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(maintain.getAutoNum()), "auto_num", maintain.getAutoNum());
        queryWrapper.like(StrUtil.isNotEmpty(maintain.getItem()), "item", maintain.getItem());
        queryWrapper.like(StrUtil.isNotEmpty(maintain.getLocation()), "location", maintain.getLocation());
        queryWrapper.le(ObjectUtil.isNotEmpty(maintain.getHighMaintainTime()), "maintain_time", maintain.getHighMaintainTime());
        queryWrapper.ge(ObjectUtil.isNotEmpty(maintain.getLowMaintainTime()), "maintain_time", maintain.getLowMaintainTime());
        queryWrapper.le(ObjectUtil.isNotEmpty(maintain.getHighCost()), "cost", maintain.getHighCost());
        queryWrapper.ge(ObjectUtil.isNotEmpty(maintain.getLowCost()), "cost", maintain.getLowCost());
        queryWrapper.orderByAsc("maintain_time");
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean delete(String ids) {
        List<Integer> list = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        if (!list.isEmpty()){
            List<Maintain> maintains = baseMapper.selectBatchIds(list);
            List<Integer> autoIdlist = maintains.stream().map(Maintain::getAutoId).toList();
            List<AutoInfo> autoInfoList = autoInfoMapper.selectBatchIds(autoIdlist);
            autoInfoList.forEach(autoInfo -> {
                autoInfo.setActualNum(autoInfo.getActualNum()-1);
                autoInfoMapper.updateById(autoInfo);
            });
            return baseMapper.deleteBatchIds(list) > 0;
        }
        return false;
    }

}
