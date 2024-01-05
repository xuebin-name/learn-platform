package com.learn.platform.server;

import com.learn.platform.config.UserChannel;
import com.learn.platform.entity.message.NettyMessage;
import com.learn.platform.service.netty.UserChatService;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserHandlerService
 * @Description TODO
 * @Author xue
 * @Date 2024/1/2 17:23
 */
@Slf4j
@Service
public class UserHandlerService {

    @DubboReference
    private UserChatService userChatService;
    public void sendMsg(String msg){
        ChannelGroup groups = UserChannel.getGroups();
        groups.writeAndFlush(new TextWebSocketFrame(msg));
    }

    public void sendMsg(NettyMessage message){
        NettyMessage send = new NettyMessage();

        BeanUtils.copyProperties(message,send);

        UserChannel.getChannel(message.getReceiveUserId()).writeAndFlush(message.getMessage());
    }

    public void chatMessage(NettyMessage message){
        log.info("执行消息保存业务方法");
        userChatService.saveUserMessage(message);
    }

}
