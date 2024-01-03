package com.learn.platform.handler;

import com.alibaba.fastjson2.JSONObject;
import com.learn.platform.entity.message.NettyMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyWebSocketHandler
 * @Description TODO
 * @Author xue
 * @Date 2024/1/3 10:36
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class MyWebSocketHandler extends SimpleChannelInboundHandler<NettyMessage> {

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("连接首先进入");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, NettyMessage message) throws Exception {
        log.info("收到消息{}", JSONObject.toJSONString(message));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("连接之后进入");
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            channelGroup.add(ctx.channel());
            WebSocketServerProtocolHandler.HandshakeComplete handshakeComplete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String s = handshakeComplete.requestUri();
            log.info("请求 remote={}- id={} - uri={}",ctx.channel().remoteAddress(),ctx.channel().id(),s);
            super.userEventTriggered(ctx,evt);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("连接断开 剩余客户端 {}",channelGroup.size());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof DefaultHttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            String uri = request.uri();
            String id = uri.split("/")[2];
            log.info(" 读取到id={}",id);
            request.setUri("/ws");
        }
        super.channelRead(ctx, msg);
    }





    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("异常处理 error={}",cause.getMessage());
        ctx.close();
    }
}
