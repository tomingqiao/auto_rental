package com.coder.rental.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Maintain;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
public interface IMaintainService extends IService<Maintain> {

    Page<Maintain> search(Page<Maintain> page, Maintain maintain);

    boolean delete(String ids);

}
