package com.learn.platform.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @ClassName NettyServerOutHandler
 * @Description 出站处理器
 * @Author xue
 * @Date 2023/12/27 10:34
 */
public class NettyServerOutHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        ChannelHandlerContext read = ctx.read();
        read.executor().next();
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        super.write(ctx, msg, promise);
    }
}
