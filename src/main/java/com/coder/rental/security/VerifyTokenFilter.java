package com.coder.rental.security;

import cn.hutool.core.util.StrUtil;
import com.coder.rental.utils.JwtUtils;
import com.coder.rental.utils.RedisUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class VerifyTokenFilter extends OncePerRequestFilter {

    @Value("${request.login-url}")
    private String loginUrl;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private CustomerUserDetailsService customerUserDetailsService;

    @Resource
    private LoginFailHandle loginFailHandle;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url=request.getRequestURI();
        if (!StrUtil.equals(url,loginUrl)){
            try {
                validateToken(request,response);
            } catch (AuthenticationException e) {
                loginFailHandle.onAuthenticationFailure(request,response,e);
            }
        }
        doFilter(request,response,filterChain);
    }

    private void validateToken(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //校验token
        String token=request.getHeader("token");
        if (StrUtil.isEmpty(token)){
            token=request.getParameter("token");
        }
        if (StrUtil.isEmpty(token)){
            throw new CustomerAuthenticationException("token不能为空");
        }
        //如果token存在，需要和redis进行校验
        String tokenKey="token:"+token;
        String tokenValue=redisUtils.get(tokenKey);
        if (StrUtil.isEmpty(tokenValue)){
            throw new CustomerAuthenticationException("token已过期");
        }
        if (!StrUtil.equals(token,tokenValue)){
            throw new CustomerAuthenticationException("token校验失败");
        }
        String username= JwtUtils.parseToken(token).getClaim("username").toString();
        if (StrUtil.isEmpty(username)){
            throw new CustomerAuthenticationException("token解析失败");
        }
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        if (userDetails==null){
            throw new CustomerAuthenticationException("用户不存在");
        }
        //创建并设置认证信息
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //将认证信息放入上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
