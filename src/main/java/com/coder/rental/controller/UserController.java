package com.coder.rental.controller;

import com.coder.rental.entity.User;
import com.coder.rental.service.IUserService;
import com.coder.rental.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/rental/user")
public class UserController {
    @Resource
    private IUserService userService;

    @GetMapping
    public Result<List<User>> list(){
        return Result.success(userService.list());
    }
}
