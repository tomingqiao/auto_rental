package com.coder.rental.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coder.rental.entity.Dept;
import com.coder.rental.entity.User;
import com.coder.rental.service.IUserService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 叶发通
 * @since 2025-01-19
 */
@RestController
@RequestMapping("/rental/user")
public class UserController {
    @Resource
    private IUserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public Result<List<User>> list(){
        return Result.success(userService.list());
    }

    @PostMapping("/{start}/{size}")
    public Result search(@PathVariable int start, @PathVariable int size, @RequestBody User user){
        return Result.success(userService.searchByPage(new Page<>(start, size), user));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sys:user:add')")
    public Result save(@RequestBody User user){
        if(userService.selectByUsername(user.getUsername())!=null){
            return Result.fail().setMessage("用户名已存在");
        }else{
            user.setPassword(StrUtil.format("{}{}","{bcrypt}",passwordEncoder.encode(user.getPassword())));
            user.setIsAdmin(0);
            if(StrUtil.isEmpty(user.getAvatar())){
                user.setAvatar("https://www.keaitupian.cn/cjpic/frombd/0/253/1152107840/119779555.jpg");
            }
            return userService.save(user)?Result.success():Result.fail();
        }

    }

    @PutMapping
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public Result update(@RequestBody User user){
        User user1=userService.selectByUsername(user.getUsername());
        if(user1 != null &&!Objects.equals(user1.getId(), user.getId())){
            return Result.fail().setMessage("用户名已存在");
        }else {
            return userService.updateById(user)?Result.success():Result.fail();
        }
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result delete(@PathVariable String ids){
        return userService.delete(ids)?Result.success():Result.fail();
    }

    @GetMapping("/role/{id}")
    @PreAuthorize("hasAuthority('sys:user:bindRole')")
    public Result selectRoleIdByUserId(@PathVariable Integer id){
        return Result.success(userService.selectRoleIdByUserId(id));
    }

    @GetMapping("/bind/{userId}/{roleIds}")
    @PreAuthorize("hasAuthority('sys:user:bindRole')")
    public Result bindRole(@PathVariable Integer userId, @PathVariable String roleIds){
        List<Integer> list = Arrays.stream(roleIds.split(",")).map(Integer::parseInt).toList();
        return userService.bindRole(userId, list)?Result.success():Result.fail();
    }

    @GetMapping("exportExcel")
    @PreAuthorize("hasAuthority('sys:user:export')")
    public Result exportExcel(HttpServletResponse response) throws IOException {
        List<User> list = userService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("deleted", "是否删除");
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息表", StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition","attachment;filename=test"+fileName+".xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return Result.success();
    }
}
