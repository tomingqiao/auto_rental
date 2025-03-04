package com.coder.rental.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.RentalType;
import com.coder.rental.mapper.RentalTypeMapper;
import com.coder.rental.service.IRentalTypeService;
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
public class RentalTypeServiceImpl extends ServiceImpl<RentalTypeMapper, RentalType> implements IRentalTypeService {

    @Override
    public Page<RentalType> searchByPage(int start, int size, RentalType rentalType) {
        Page<RentalType> page = new Page<>(start, size);
        QueryWrapper<RentalType> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(rentalType.getTypeName()), "type_name", rentalType.getTypeName());
        queryWrapper.le(ObjectUtil.isNotEmpty(rentalType.getHighDiscount()), "type_discount", rentalType.getHighDiscount());
        queryWrapper.ge(ObjectUtil.isNotEmpty(rentalType.getLowDiscount()), "type_discount", rentalType.getLowDiscount());
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean delete(String ids) {
        List<Integer> list = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        if (!list.isEmpty()){
            for (Integer id : list) {
                baseMapper.deleteById(id);
            }
            return true;
        }
        return false;
    }
}
