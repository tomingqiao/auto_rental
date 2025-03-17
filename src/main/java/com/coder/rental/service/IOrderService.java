package com.coder.rental.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
public interface IOrderService extends IService<Order> {

    boolean add(Order order);

    Page<Order> selectUnfinished(int start, int size, Order order);

    boolean updateOrder(Order order);

    Page<Order> search(Page<Order> page, Order order);

    long returnDeposit(Integer autoId);
}
