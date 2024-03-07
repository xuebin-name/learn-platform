package com.learn.platform.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName KafkaProducer
 * @Description kafka 生产者
 * @Author xue
 * @Date 2024/3/5 16:01
 */
@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, String message) {
        log.info("kafka 生产者发送消息 topic:{}, message:{}", topic, message);
        kafkaTemplate.send(topic, message);
    }
}
