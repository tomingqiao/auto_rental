package com.coder.rental.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Dept;
import com.coder.rental.entity.Role;
import com.coder.rental.service.IPermissionService;
import com.coder.rental.service.IRoleService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
@RequestMapping("/rental/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @Resource
    private IPermissionService permissionService;

    @PostMapping("/{start}/{size}")
    @PreAuthorize("hasAuthority('sys:role:select')")
    public Result search(@PathVariable int start, @PathVariable int size, @RequestBody Role role){
        Page<Role> Page = new Page<>(start, size);
        return Result.success(roleService.selectList(Page, role));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sys:role:edit')")
    public Result update(@RequestBody Role role){
        return roleService.updateById(role)? Result.success() : Result.fail();
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public Result delete(@PathVariable String ids){
        return roleService.delete(ids)? Result.success() : Result.fail();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:role:add')")
    public Result save(@RequestBody Role role){
        return roleService.save(role)? Result.success() : Result.fail();
    }

    @GetMapping("/hasUser/{id}")
    public Result hasUser(@PathVariable Integer id){
        return roleService.hasUser(id)? Result.success().setMessage("有用户使用此角色") : Result.success().setMessage("无用户使用此角色");
    }

    @GetMapping("/permissionTree")
    @PreAuthorize("hasAuthority('sys:role:assignPermissions')")
    public Result selectPermissionTree(Integer userId,Integer roleId){
        return Result.success(permissionService.selectPermissionTree(userId,roleId));
    }

    @GetMapping("/{roleId}/{permissionIds}")
    @PreAuthorize("hasAuthority('sys:role:assignPermissions')")
    public Result assignPermission(@PathVariable Integer roleId,@PathVariable String permissionIds){
        List<Integer> list = Arrays.stream(permissionIds.split(",")).map(Integer::parseInt).toList();
        return roleService.assignPermission(roleId,list)? Result.success() : Result.fail();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('sys:user:bindRole')")
    public Result list(){
        return Result.success(roleService.list());
    }

    @GetMapping("exportExcel")
    @PreAuthorize("hasAuthority('sys:role:export')")
    public Result exportExcel(HttpServletResponse response) throws IOException {
        List<Role> list = roleService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("deleted", "是否删除");
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("角色信息表", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition","attachment;filename=test"+fileName+".xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return Result.success();
    }
}
