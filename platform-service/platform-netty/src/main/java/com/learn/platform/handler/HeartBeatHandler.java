package com.learn.platform.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName HeartBeatHandler
 * @Description 心跳检测出机制处理
 * @Author xue
 * @Date 2023/12/27 14:30
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof TextWebSocketFrame){
            TextWebSocketFrame frame = (TextWebSocketFrame)msg;
            log.info("心跳包 msg={}", frame.text());
            ctx.writeAndFlush(new TextWebSocketFrame("pong"));
            log.info("回复心跳包 heart={}-","pong");
        }else {
            //传递消息到下一个处理器
            ctx.fireChannelRead(msg);
        }
    }

    /**
     * 心跳检测
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("触发心跳机制-------");
        String remote = ctx.channel().remoteAddress().toString();
        log.info("心跳 remote = {}", remote);
        if(evt instanceof TextWebSocketFrame){
            TextWebSocketFrame frame = (TextWebSocketFrame) evt;
            log.info("心跳包信息{}",frame.text());
            if("ping".equals(frame.text())){
                ctx.writeAndFlush(new TextWebSocketFrame("pong"));
            }else {
                ctx.writeAndFlush(new TextWebSocketFrame("收到消息"+frame.text()));
                ctx.fireChannelRead(evt);
            }

        }
        /*if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("客户端 :" + remote + "读超时");
                ctx.disconnect();
            }else {
                ctx.writeAndFlush("pong");
            }
        }*/

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务端 心跳处理异常 error={}",cause.getMessage());
        ctx.fireExceptionCaught(cause);
    }
}
