package com.learn.platform.service.impl;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @ClassName CacheListenerService
 * @Description TODO
 * @Author xue
 * @Date 2024/3/26 10:16
 */
@Slf4j
@Component
public class CacheListenerService extends KeyExpirationEventMessageListener {


    public CacheListenerService(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("监听到key过期事件 message={}", JSON.toJSONString(message));
        String key = message.toString();
        log.info("key={}",key);
    }
}
