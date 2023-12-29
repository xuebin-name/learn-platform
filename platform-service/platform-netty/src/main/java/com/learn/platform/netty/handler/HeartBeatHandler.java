package com.learn.platform.netty.handler;

import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName HeartBeatHandler
 * @Description 心跳检测出机制处理
 * @Author xue
 * @Date 2023/12/27 14:30
 */
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("心跳机制获取到数据----");
        log.info("ceshi");
        super.channelRead(ctx, msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("触发心跳机制-------");
        log.info("心跳机制获取到数据----");
        log.info(JSONObject.toJSONString(evt));
        ctx.channel().read();
        //super.userEventTriggered(ctx, evt);
    }
}
