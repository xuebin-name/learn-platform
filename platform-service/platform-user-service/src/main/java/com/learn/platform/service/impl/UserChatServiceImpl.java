package com.learn.platform.service.impl;

import com.learn.platform.entity.message.NettyMessage;
import com.learn.platform.entity.message.PMessage;
import com.learn.platform.service.dao.UserMessageDao;
import com.learn.platform.service.netty.UserChatService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @ClassName UserChatServiceImpl
 * @Description TODO
 * @Author xue
 * @Date 2024/1/2 15:28
 */
@Async
@Service
@DubboService
public class UserChatServiceImpl implements UserChatService {

    @Autowired
    private UserMessageDao userMessageDao;

    public void sendMessage(NettyMessage message){

    }


    @Override
    public void saveUserMessage(NettyMessage message){
        PMessage pMessage = new PMessage();
        BeanUtils.copyProperties(message,pMessage);
        pMessage.setMessageId(UUID.randomUUID().toString());
        pMessage.setMessageStatus(0);
        userMessageDao.saveUserMessage(pMessage);
    }
}
