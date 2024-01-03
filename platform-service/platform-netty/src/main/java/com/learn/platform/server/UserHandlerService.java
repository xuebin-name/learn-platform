package com.learn.platform.server;

import com.learn.platform.config.UserChannel;
import com.learn.platform.entity.message.NettyMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserHandlerService
 * @Description TODO
 * @Author xue
 * @Date 2024/1/2 17:23
 */

@Service
public class UserHandlerService {


    public void sendMsg(String msg){
        UserChannel.getGroups().writeAndFlush(msg);
    }

    public void sendMsg(NettyMessage message){
        NettyMessage send = new NettyMessage();

        BeanUtils.copyProperties(message,send);

        UserChannel.getChannel(message.getReceiveUserId()).writeAndFlush(message.getMessage());
    }

}
