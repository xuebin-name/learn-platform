package com.learn.platform.netty.config;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.nio.channels.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName NettyConfig
 * @Description netty配置类
 * @Author xue
 * @Date 2023/12/26 10:03
 */
@Data
@Component
public class NettyConfig {
    //保存用户对应通道
    public static final Map<String, Channel> channels = new ConcurrentHashMap<>(16);
}
