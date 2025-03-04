package com.coder.rental.service;

import com.coder.rental.entity.Dept;
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
public interface IDeptService extends IService<Dept> {

    List<Dept> searchList(Dept dept);

    List<Dept> selectTree();

    boolean hasChildren(Integer id);
}
