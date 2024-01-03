package com.learn.platform.service.netty;

import com.learn.platform.entity.message.NettyMessage;

/**
 * @ClassName UserChatService
 * @Description 用户聊天处理类
 * @Author xue
 * @Date 2024/1/2 15:27
 */
public interface UserChatService {

    void saveUserMessage(NettyMessage message);
}
