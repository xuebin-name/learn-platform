package com.learn.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisService
 * @Description redis数据操作服务
 * @Author xue
 * @Date 2023/12/22 11:47
 */
@Component
public class RedisService {


    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 获取 key
     *
     * @param <T> 返回值类型
     * @param key 键
     * @return 对应的value值
     */
    public <T> T getObject(String key) {
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * 设置带有过期时间的key
     *
     * @param key  键
     * @param time 过期时间 单位s
     * @return Boolean
     */
    public boolean expire(final String key, final long time) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, time, TimeUnit.SECONDS));
    }

    /**
     * 自定义设置过期时间
     *
     * @param key  键
     * @param time 时间
     * @param unit 单位
     * @return Boolean
     */
    public boolean expire(final String key, final long time, final TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, time, unit));
    }

    /**
     * 设置基本对象
     *
     * @param key   键
     * @param value 值
     * @param <T>   对象类型
     */
    public <T> void putObject(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Boolean  setNx(String key,Object value){
        return redisTemplate.opsForValue().setIfAbsent(key,value);
    }

    public Boolean setEx(String key ,Object value ,long timeOut){
        return redisTemplate.opsForValue().setIfAbsent(key,value,timeOut,TimeUnit.SECONDS);
    }

    public Boolean setEx(String key ,Object value ,long timeOut,TimeUnit unit){
        return redisTemplate.opsForValue().setIfAbsent(key,value,timeOut,unit);
    }
}
