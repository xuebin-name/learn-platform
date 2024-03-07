package com.learn.platform.service.impl;

import com.learn.platform.service.KafkaProducer;
import com.learn.platform.service.RabbitProducer;
import com.learn.platform.service.RocketProducer;
import com.learn.platform.service.user.UserMsgService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserMsgServiceImpl
 * @Description TODO
 * @Author xue
 * @Date 2024/3/5 16:13
 */
@Service
@DubboService
public class UserMsgServiceImpl implements UserMsgService {
    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private RabbitProducer rabbitProducer;

    @Autowired
    private RocketProducer rocketProducer;

    @Override
    public void send(String topic, String message) {
        kafkaProducer.send(topic, message);
    }



    @Override
    public void sendRabbit(String message){
        rabbitProducer.send(message);
    }

    @Override
    public void sendRabbit(String queue, String message) {
        rabbitProducer.send(queue,message);
    }

    @Override
    public void sendRocket(String topic, String msg){
        rocketProducer.send(topic,msg);
    }
}
