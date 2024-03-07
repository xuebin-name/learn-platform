package com.learn.platform.service;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @ClassName RocketProducer
 * @Description rocketmq 生产者
 * @Author xue
 * @Date 2024/3/6 13:43
 */
@Component
public class RocketProducer {
    private static final Logger log = LoggerFactory.getLogger(RocketProducer.class);
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void send(String topic,String msg){
        Message<String> message = MessageBuilder.withPayload(msg).build();
        rocketMQTemplate.send(topic,message);
    }
}
