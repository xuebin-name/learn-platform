package com.learn.platform.listern;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName NettyListernHandler
 * @Description TODO
 * @Author xue
 * @Date 2023/12/26 14:25
 */
@Slf4j
@Component
public class NettyListenerHandler implements ChannelFutureListener {
    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        log.info("监听器操作完成------");
    }
}
