package com.example.login.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setData(String key, String value, Long expiredTime){
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.DAYS);
    }

    public String getData(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key){
        redisTemplate.delete(key);
    }
}