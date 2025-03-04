package com.coder.rental.mapper;

import com.coder.rental.entity.Role;
import com.coder.rental.entity.User;
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
public interface UserMapper extends BaseMapper<User> {
    List<Integer> selectRoleIdByUserId(Integer userId);
}
