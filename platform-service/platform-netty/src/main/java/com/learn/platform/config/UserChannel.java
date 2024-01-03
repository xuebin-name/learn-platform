package com.learn.platform.config;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName UserChannel
 * @Description TODO
 * @Author xue
 * @Date 2024/1/2 15:16
 */
public class UserChannel {

    private static final Map<String, Channel> userChannels = new ConcurrentHashMap<>(16);

    @Getter
    private static final ChannelGroup groups = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //private static final

    public static void put(String key,Channel channel){
        userChannels.put(key,channel);
    }

    public static Channel getChannel(String key){
        return userChannels.get(key);
    }

    public static void groupAdd(Channel channel){
        groups.add(channel);
    }

    public static void remove(Channel channel){
        userChannels.remove(channel);
    }

}
