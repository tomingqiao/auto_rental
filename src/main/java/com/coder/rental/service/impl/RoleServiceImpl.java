package com.coder.rental.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Role;
import com.coder.rental.entity.RolePermission;
import com.coder.rental.entity.User;
import com.coder.rental.entity.UserRole;
import com.coder.rental.mapper.RoleMapper;
import com.coder.rental.mapper.RolePermissionMapper;
import com.coder.rental.mapper.UserMapper;
import com.coder.rental.mapper.UserRoleMapper;
import com.coder.rental.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Page<Role> selectList(Page<Role> page, Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(role.getRoleName()), "role_name", role.getRoleName());
        queryWrapper.orderByAsc("create_time");
        Integer createrId = role.getCreaterId();
        User user = userMapper.selectById(createrId);
        if(user!=null&&user.getIsAdmin()!=1){
            queryWrapper.eq("creater_id", createrId);
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean hasUser(Integer id) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", id);
        return userRoleMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public boolean delete(String ids) {
        List<Integer> list = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        if (!list.isEmpty()){
            for (Integer id : list) {
                if (!hasUser(id)){
                    rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("role_id", id));
                    baseMapper.deleteById(id);
                }
            }
        }
        return true;
    }

    @Override
    public boolean assignPermission(Integer roleId, List<Integer> permissionIds) {
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        rolePermissionMapper.delete(queryWrapper);
        try{
            if (permissionIds!=null&&!permissionIds.isEmpty()){
                for (Integer permissionId : permissionIds) {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRoleId(roleId);
                    rolePermission.setPermissionId(permissionId);
                    rolePermissionMapper.insert(rolePermission);
                }
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
