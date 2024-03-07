package com.learn.platform.service.user;

public interface UserMsgService {

    void send(String topic, String message);

    void sendRabbit(String message);

    void sendRabbit(String queue,String message);

    void sendRocket(String topic,String msg);
}
