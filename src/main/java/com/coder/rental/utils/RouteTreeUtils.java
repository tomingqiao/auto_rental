package com.coder.rental.utils;

import com.coder.rental.entity.Permission;
import com.coder.rental.vo.RouteVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RouteTreeUtils {
    public static List<RouteVo> buildRouteTree(List<Permission> permissionList,int pid) {
        List<RouteVo> routeVoList=new ArrayList<>();
        Optional.ofNullable(permissionList).orElse(new ArrayList<>())
                .stream()
                .filter(permission -> permission!=null && permission.getPid() == pid)
                .forEach(permission -> {
                    RouteVo routeVo=new RouteVo();
                    routeVo.setPath(permission.getRoutePath());
                    routeVo.setName(permission.getRouteName());
                    if (permission.getPid()==0){
                        routeVo.setComponent("Layout");
                        routeVo.setAlwaysShow(true);
                    }else {
                        routeVo.setComponent(permission.getRouteUrl());
                        routeVo.setAlwaysShow(false);
                    }
                    routeVo.setMeta(routeVo.new Meta(permission.getPermissionLabel(),permission.getIcon(),permission.getPermissionCode().split(",")));
                    List<RouteVo> children=buildRouteTree(permissionList,permission.getId());
                    routeVo.setChildren(children);
                    routeVoList.add(routeVo);
                });
        return routeVoList;
    }
}
