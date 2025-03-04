package com.coder.rental.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoBrand;
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
public interface IAutoBrandService extends IService<AutoBrand> {
    Page<AutoBrand> searchByPage(Page<AutoBrand> page, AutoBrand autoBrand);

    List<AutoBrand> selectByMakerId(Integer id);
}
