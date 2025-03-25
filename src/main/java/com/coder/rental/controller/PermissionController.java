package com.coder.rental.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.coder.rental.entity.Dept;
import com.coder.rental.entity.Permission;
import com.coder.rental.service.IPermissionService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
@RestController
@RequestMapping("/rental/permission")
public class PermissionController {

    @Resource
    IPermissionService permissionService;

    @GetMapping
    public Result list() {
        return Result.success(permissionService.selectList());
    }

    @GetMapping("/tree")
    public Result tree(){
        return Result.success(permissionService.selectTree());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:permission:add')")
    public Result save(@RequestBody Permission permission) {
        if(permission.getPermissionType()!=2){
            String routeUrl = permission.getRouteUrl();
            permission.setRouteName(routeUrl.substring(routeUrl.lastIndexOf("/")+1));
            permission.setRoutePath(routeUrl.substring(routeUrl.lastIndexOf("/")));
        }
        return permissionService.save(permission) ? Result.success() : Result.fail();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sys:permission:edit')")
    public Result update(@RequestBody Permission permission) {
        if(permission.getPermissionType()!=2){
            String routeUrl = permission.getRouteUrl();
            permission.setRouteName(routeUrl.substring(routeUrl.lastIndexOf("/")+1));
            permission.setRoutePath(routeUrl.substring(routeUrl.lastIndexOf("/")));
        }
        return permissionService.updateById(permission) ? Result.success() : Result.fail();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:permission:delete')")
    public Result delete(@PathVariable Integer id) {
        return permissionService.removeById(id) ? Result.success() : Result.fail();
    }

    @GetMapping("/hasChildren/{id}")
    public Result hasChildren(@PathVariable Integer id) {
        return permissionService.hasChildren(id)?Result.success().setMessage("有子菜单")
                :Result.success().setMessage("无子菜单");
    }

    @GetMapping("exportExcel")
    @PreAuthorize("hasAuthority('sys:permission:export')")
    public Result exportExcel(HttpServletResponse response) throws IOException {
        List<Permission> list = permissionService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("deleted", "是否删除");
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("菜单信息表", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition","attachment;filename=test"+fileName+".xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return Result.success();
    }
}
