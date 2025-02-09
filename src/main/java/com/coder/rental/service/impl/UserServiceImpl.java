package com.coder.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coder.rental.entity.User;
import com.coder.rental.mapper.UserMapper;
import com.coder.rental.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User selectByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<String> selectRoleNameByUserId(int id) {
        return baseMapper.selectRoleNameByUserId(id);
    }
}
