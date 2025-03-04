package com.coder.rental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coder.rental.entity.Permission;
import com.coder.rental.entity.User;
import com.coder.rental.mapper.PermissionMapper;
import com.coder.rental.mapper.UserMapper;
import com.coder.rental.service.IPermissionService;
import com.coder.rental.utils.RouteTreeUtils;
import com.coder.rental.vo.RolePermissionVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<Permission> selectPermissionListByUserId(Integer userId) {
        return baseMapper.selectPermissionListByUserId(userId);
    }

    @Override
    public List<Permission> selectList() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("order_num");
        List<Permission> permissions = baseMapper.selectList(queryWrapper);
        return RouteTreeUtils.buildMenuTree(permissions,0);
    }

    @Override
    public List<Permission> selectTree() {
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.ne("permission_type",2);
        queryWrapper.orderByAsc("order_num");
        List<Permission> permissions = baseMapper.selectList(queryWrapper);
        Permission permission = new Permission();
        permission.setPermissionLabel("顶级菜单").setId(0).setPid(-1);
        permissions.add(permission);
        return RouteTreeUtils.buildMenuTree(permissions,-1);
    }

    @Override
    public Boolean hasChildren(Integer id) {
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pid",id);
        return baseMapper.selectCount(queryWrapper)>0;
    }

    @Override
    public RolePermissionVo selectPermissionTree(Integer userId, Integer roleId) {
        User user=userMapper.selectById(userId);
        List<Permission> list=null;
        if(user!=null&&user.getIsAdmin()==1){
            list=baseMapper.selectList(null);
        }else {
            list=baseMapper.selectPermissionListByUserId(userId);
        }
        List<Permission> permissions = RouteTreeUtils.buildMenuTree(list, 0);
        List<Permission> rolePermissions=baseMapper.selectPermissionListByRoleId(roleId);

        List<Integer> list1 = new ArrayList<>(list.stream().map(Permission::getId).toList());
        List<Integer> list2 = rolePermissions.stream().map(Permission::getId).toList();
        list1.retainAll(list2);
        Object[] array = list1.toArray();
        RolePermissionVo rolePermissionVo = new RolePermissionVo();
        rolePermissionVo.setPermissionList(permissions).setCheckedList(array);
        return rolePermissionVo;
    }
}
