package com.coder.rental.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    //密钥
    public static final String SECRET_KEY = "tomingqiao";
    //过期时间
    public static final long EXPIRE_TIME = 1000L*60*60*24;

    public static String createToken(Map<String,Object> payload){
        DateTime now = DateTime.now();
        DateTime newTime=new DateTime(now.getTime()+EXPIRE_TIME);
        //设置签发时间
        payload.put(JWTPayload.ISSUED_AT,now);
        //设置过期时间
        payload.put(JWTPayload.EXPIRES_AT,newTime);
        //设置生效时间，确保token在签发后立即生效
        payload.put(JWTPayload.NOT_BEFORE,now);
        return JWTUtil.createToken(payload, SECRET_KEY.getBytes());
    }

    public static JWTPayload parseToken(String token){
        JWT jwt= JWTUtil.parseToken(token);
        if(!jwt.setKey(SECRET_KEY.getBytes()).verify()){
            throw new RuntimeException("token异常");
        }
        if(!jwt.validate(0)){
            throw new RuntimeException("token已过期");
        }
        return jwt.getPayload();
    }

/*
    public static void main(String[] args) {
//        Map<String,Object> payload = new HashMap<String,Object>();
//        payload.put("id",1);
//        payload.put("username","tomingqiao");
//
//        String token = createToken(payload);
//        System.out.println(token);
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE3MzczMDk4OTUsImlkIjoxLCJleHAiOjE3MzczMTE2OTUsImlhdCI6MTczNzMwOTg5NSwidXNlcm5hbWUiOiJ0b21pbmdxaWFvIn0.eqA1Xr1qERI90Sqiude3L31Hj1UIwgVk4R1CT1OhZkY";
        JWTPayload payload = parseToken(token);
        System.out.println(payload.getClaim("id"));
        System.out.println(payload.getClaim("username"));

    }
*/
}
