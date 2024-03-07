package com.learn.platform.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName RocketMqListener
 * @Description TODO
 * @Author xue
 * @Date 2024/3/6 17:50
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "test-topic", consumerGroup = "my-producer-group")
public class RocketMqListener implements RocketMQListener {
    @Override
    public void onMessage(Object o) {
        log.info("receive message: {}", o);
    }
}
