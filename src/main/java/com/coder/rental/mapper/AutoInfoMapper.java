package com.coder.rental.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
public interface AutoInfoMapper extends BaseMapper<AutoInfo> {

    Page<AutoInfo> searchByPage(Page<AutoInfo> page, AutoInfo autoInfo);

    List<AutoInfo> toBeMaintain();
}
