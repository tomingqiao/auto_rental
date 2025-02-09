package com.coder.rental.service.impl;

import com.coder.rental.entity.Order;
import com.coder.rental.mapper.OrderMapper;
import com.coder.rental.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
