package com.coder.rental.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.User;
import com.coder.rental.entity.UserRole;
import com.coder.rental.mapper.UserMapper;
import com.coder.rental.mapper.UserRoleMapper;
import com.coder.rental.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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

    @Resource
    private UserRoleMapper userRoleMapper;


    @Override
    public User selectByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Integer> selectRoleIdByUserId(int id) {
        return baseMapper.selectRoleIdByUserId(id);
    }

    @Override
    public Page<User> searchByPage(Page<User> page, User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (user.getDeptId()!=null&&user.getDeptId()==0){
            return baseMapper.selectPage(page, null);
        }
        queryWrapper.eq(ObjectUtil.isNotEmpty(user.getDeptId()),"dept_id",user.getDeptId());
        queryWrapper.like(StrUtil.isNotEmpty(user.getUsername()), "username", user.getUsername());
        queryWrapper.like(StrUtil.isNotEmpty(user.getNickname()), "nickname", user.getNickname());
        queryWrapper.like(StrUtil.isNotEmpty(user.getRealname()), "realname", user.getRealname());
        queryWrapper.like(StrUtil.isNotEmpty(user.getPhone()), "phone", user.getPhone());
        queryWrapper.like(StrUtil.isNotEmpty(user.getEmail()), "email", user.getEmail());
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean delete(String ids) {
        List<Integer> list = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        try{
            if (!list.isEmpty()){
                list.forEach(id->{
                    if(baseMapper.selectById(id).getIsAdmin()==1){
                        throw new RuntimeException("超级管理员不能删除");
                    }
                    baseMapper.deleteById(id);
                    userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", id));
                });
            }
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean bindRole(Integer userId, List<Integer> list) {
        userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", userId));
        if (!list.isEmpty()){
            for (Integer roleId : list) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRoleMapper.insert(userRole);
            }
            return true;
        }
        return false;
    }
}
