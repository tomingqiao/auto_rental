package com.coder.rental.controller;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTPayload;
import com.coder.rental.entity.Permission;
import com.coder.rental.entity.User;
import com.coder.rental.security.CustomerAuthenticationException;
import com.coder.rental.service.IUserService;
import com.coder.rental.utils.JwtUtils;
import com.coder.rental.utils.RedisUtils;
import com.coder.rental.utils.Result;
import com.coder.rental.utils.RouteTreeUtils;
import com.coder.rental.vo.RouteVo;
import com.coder.rental.vo.TokenVo;
import com.coder.rental.vo.UserInfoVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/rental/auth/")
public class AuthController {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private IUserService userService;

    @PostMapping("/refreshToken")
    public Result refreshToken(HttpServletRequest request){
        //获取token
        String token=request.getHeader("token");
        if (StrUtil.isEmpty(token)){
            token=request.getParameter("token");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = JwtUtils.parseToken(token).getClaim("username").toString();
        String newToken="";
        if (StrUtil.equals(username,userDetails.getUsername())){
            Map<String, Object> map=new HashMap<>();
            map.put("username",userDetails.getUsername());
            newToken= JwtUtils.createToken(map);
        }else {
            throw new CustomerAuthenticationException("token数据异常");
        }
        NumberWithFormat claim = (NumberWithFormat) JwtUtils.parseToken(newToken).getClaim(JWTPayload.EXPIRES_AT);
        DateTime dateTime = (DateTime) claim.convert(DateTime.class, claim);
        long expireTime=dateTime.getTime();
        TokenVo tokenVo=new TokenVo();
        tokenVo.setToken(newToken).setExpireTime(expireTime);
        redisUtils.del("token:"+token);
        long nowTime= DateTime.now().getTime();
        String newTokenKey="token:"+newToken;
        redisUtils.set(newTokenKey,newToken,(expireTime-nowTime)/1000);
        return Result.success(tokenVo).setMessage("token刷新成功");
    }

    @GetMapping("/info")
    public Result getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null){
            return Result.fail().setMessage("认证信息为空");
        }
        User user = (User) authentication.getPrincipal();
        List<String> list=userService.selectRoleNameByUserId(user.getId());
        Object[] array = list.toArray();
        UserInfoVo userInfoVo=new UserInfoVo(user.getId(), user.getUsername(), user.getAvatar(),
                user.getNickname(), array);
        return Result.success(userInfoVo).setMessage("获取用户信息成功");
    }

    @GetMapping("/menuList")
    public Result getMenuList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null){
            return Result.fail().setMessage("认证信息为空");
        }
        User user = (User) authentication.getPrincipal();
        List<Permission> permissionList = user.getPermissionList();
        permissionList.removeIf(permission -> Objects.equals(permission.getPermissionType(), 2));
        List<RouteVo> routeVoList= RouteTreeUtils.buildRouteTree(permissionList,0);
        return Result.success(routeVoList).setMessage("获取用户菜单成功");
    }
}
