package com.coder.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoInfo;
import com.coder.rental.entity.Order;
import com.coder.rental.mapper.AutoInfoMapper;
import com.coder.rental.mapper.OrderMapper;
import com.coder.rental.service.IAutoInfoService;
import com.coder.rental.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Value("${auto.info.maintain-mileage}")
    private Integer maintainMileage;

    @Resource
    private AutoInfoMapper autoInfoMapper;
    @Override
    public boolean add(Order order) {
        AutoInfo auto = autoInfoMapper.selectById(order.getAutoId());
        if (auto.getStatus()==0){
            auto.setStatus(1);
        }
        autoInfoMapper.updateById(auto);
        String num = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
        order.setOrderNum(num);
        order.setRentalTime(LocalDateTime.now());
        return baseMapper.insert(order)>0;
    }

    @Override
    public Page<Order> selectUnfinished(int start, int size, Order order) {
        Page<Order> page = new Page<>(start,size);
        return baseMapper.selectUnfinished(page,order);
    }

    @Override
    public boolean updateOrder(Order order) {
        AutoInfo autoInfo = autoInfoMapper.selectById(order.getAutoId());
        if (autoInfo.getStatus()==1){
            autoInfo.setStatus(0);
        }
        autoInfo.setMileage(order.getReturnMileage());
        int i = autoInfo.getMileage() / maintainMileage;
        autoInfo.setExpectedNum(i);
        return baseMapper.updateById(order)>0&&autoInfoMapper.updateById(autoInfo)>0;
    }

    @Override
    public Page<Order> search(Page<Order> page, Order order) {
        return baseMapper.searchByPage(page,order);
    }

    @Override
    public long returnDeposit(Integer autoId) {
        return baseMapper.countViolation(autoId);
    }

}
