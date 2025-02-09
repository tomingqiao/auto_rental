package com.coder.rental.service.impl;

import com.coder.rental.entity.Customer;
import com.coder.rental.mapper.CustomerMapper;
import com.coder.rental.service.ICustomerService;
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
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
