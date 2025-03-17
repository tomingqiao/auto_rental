package com.coder.rental.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
public interface ICustomerService extends IService<Customer> {

    Page<Customer> searchByPage(int start, int size, Customer customer);

    boolean delete(String ids);

    Customer selectByTel(String tel);
}
