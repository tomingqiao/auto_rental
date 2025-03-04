package com.coder.rental.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
public interface IAutoInfoService extends IService<AutoInfo> {

    Page<AutoInfo> search(int start, int size, AutoInfo autoInfo);

    boolean delete(String ids);

    Integer getIdByNum(String autoNum);
}
