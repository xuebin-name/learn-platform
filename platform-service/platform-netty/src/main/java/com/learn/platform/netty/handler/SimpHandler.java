package com.learn.platform.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName SimpHandler
 * @Description TODO
 * @Author xue
 * @Date 2023/12/28 16:05
 */
@Slf4j
public class SimpHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        log.info("触发读取方法 {}",s);
    }
}
