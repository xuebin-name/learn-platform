package com.learn.platform.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName RabbitProducer
 * @Description TODO
 * @Author xue
 * @Date 2024/3/6 15:20
 */
@Slf4j
@Component
public class RabbitProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String message){
        log.info("rabbit mq 发送消息 message:{}", message);
        amqpTemplate.convertAndSend(message);
    }

    public void send(String queue,String message){
        log.info("rabbit mq 发送消息 指定的队列名称queue={}, message:{}", queue,message);
        amqpTemplate.convertAndSend(queue,message);
    }
}
