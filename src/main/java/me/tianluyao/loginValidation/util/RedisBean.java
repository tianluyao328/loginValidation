package me.tianluyao.loginValidation.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author tianluyao
 * @Description 采用静态变量而并非自动注入，是因为在拦截器加载的时候，自动注入还没有完成，如果在拦截器里使用自动注入，则会出现NPE
 * @Date 8:51 2022/5/25
 **/
@Component
public class RedisBean {

    @Autowired
    private RedisTemplate emRedisTemplate;

    /**
     * @Author tianluyao
     * @Description 实际使用的就是此静态变量
     * @Date 8:53 2022/5/25
     **/
    public static RedisTemplate redis;

    @PostConstruct
    public void getRedisTemplate(){
        redis = this.emRedisTemplate;
    }
}
