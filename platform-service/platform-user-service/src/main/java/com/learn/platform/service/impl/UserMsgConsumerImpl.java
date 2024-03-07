package com.learn.platform.service.impl;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @ClassName UserMsgConsumerImpl
 * @Description TODO
 * @Author xue
 * @Date 2024/3/5 16:58
 */
@Slf4j
@Service
public class UserMsgConsumerImpl {
    /**
     * kafka 消费
     * @param consumer
     * @param ack
     * @param topic
     */
    @KafkaListener(topics = "11", groupId = "11")
    public void consumer(ConsumerRecord<String, String> consumer, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("进入Kafka消费者 topic={}", topic);
        String value = consumer.value();
        log.info("Kafka消费者 value={}", value);
        ack.acknowledge();
    }



    /**
     * rabbit mq 消费
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = "myrabbit-test")
    @RabbitHandler
    public void rabbitConsumer(Message message, Channel channel) throws IOException {
        log.info("进入rabbit消费者 message={}", message);
        String queue = "";
        channel.basicConsume(queue, false, (consumerTag, delivery) -> {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            String msg = new String(message.getBody());
            log.info("消息体 body={}", msg);
            try {
                channel.basicAck(deliveryTag, false);
            } catch (IOException e) {
                log.error("消费失败");
                channel.basicReject(deliveryTag, true);
            }
        }, consumerTag -> {
        });
        if(!channel.isOpen()){

        }

    }
}
