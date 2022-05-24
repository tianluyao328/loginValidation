package me.tianluyao.loginValidation.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RedisBean {

    @Autowired
    private RedisTemplate emRedisTemplate;

    public static RedisTemplate redis;

    @PostConstruct
    public void getRedisTemplate(){
        redis = this.emRedisTemplate;
    }
}
