package com.learn.platform.netty.handler;

import com.alibaba.fastjson2.JSONObject;
import com.learn.platform.entity.message.NettyMessage;
import com.learn.platform.netty.config.NettyConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName MyServerHandler
 * @Description TODO
 * @Author xue
 * @Date 2023/12/28 15:40
 */
@Slf4j
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private NettyConfig nettyConfig;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道激活");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道关闭");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("通道读取");
        String jsonStr = JSONObject.toJSONString(msg);
        log.info("读取到数据 {}", jsonStr);
        NettyMessage message = JSONObject.parseObject(msg.toString(),NettyMessage.class);
        log.info("message={}",JSONObject.toJSONString(message));
        nettyConfig.putChannel(message.getChannelId(),ctx.channel());
        super.channelRead(ctx, message);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("通道读取完成");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("通道触发事件");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("通道异常");
        super.exceptionCaught(ctx, cause);
    }
}
