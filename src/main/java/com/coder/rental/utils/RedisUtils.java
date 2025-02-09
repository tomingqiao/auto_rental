package com.coder.rental.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key,String value,Long timeout){
        stringRedisTemplate.opsForValue().set(key,value,timeout, TimeUnit.SECONDS);
    }

    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void del(String key){
        stringRedisTemplate.delete(key);
    }
}
