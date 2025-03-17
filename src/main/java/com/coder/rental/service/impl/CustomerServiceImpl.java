package com.coder.rental.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Customer;
import com.coder.rental.mapper.CustomerMapper;
import com.coder.rental.service.ICustomerService;
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
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Override
    public Page<Customer> searchByPage(int start, int size, Customer customer) {
        Page<Customer> page = new Page<>(start, size);
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(customer.getName()), "name", customer.getName());
        queryWrapper.like(StrUtil.isNotEmpty(customer.getTel()), "tel", customer.getTel());
        queryWrapper.like(StrUtil.isNotEmpty(customer.getIdNum()), "id_num", customer.getIdNum());
        queryWrapper.eq(ObjectUtil.isNotEmpty(customer.getStatus()), "status", customer.getStatus());
        queryWrapper.eq(ObjectUtil.isNotEmpty(customer.getGender()), "gender", customer.getGender());
        queryWrapper.le(ObjectUtil.isNotEmpty(customer.getHighAge()), "age", customer.getHighAge());
        queryWrapper.ge(ObjectUtil.isNotEmpty(customer.getLowAge()), "age", customer.getLowAge());
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

    @Override
    public Customer selectByTel(String tel) {
        if (StrUtil.isNotEmpty(tel)){
            QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("tel", tel);
            return baseMapper.selectOne(queryWrapper);
        }
        return null;
    }
}
