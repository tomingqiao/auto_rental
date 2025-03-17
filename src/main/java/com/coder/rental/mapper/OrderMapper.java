package com.coder.rental.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
public interface OrderMapper extends BaseMapper<Order> {

    Page<Order> selectUnfinished(Page<Order> page, Order order);

    Page<Order> searchByPage(Page<Order> page, Order order);

    long countViolation(Integer autoId);
}
