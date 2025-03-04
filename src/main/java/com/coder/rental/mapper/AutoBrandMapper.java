package com.coder.rental.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.AutoBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
public interface AutoBrandMapper extends BaseMapper<AutoBrand> {
    Page<AutoBrand> searchByPage(@Param("page") Page<AutoBrand> page, @Param("autoBrand") AutoBrand autoBrand);
}
