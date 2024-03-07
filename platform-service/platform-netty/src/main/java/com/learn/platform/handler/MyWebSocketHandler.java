package com.learn.platform.handler;

import com.alibaba.fastjson2.JSONObject;
import com.learn.platform.config.UserChannel;
import com.learn.platform.entity.message.NettyMessage;
import com.learn.platform.server.UserHandlerService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private UserHandlerService userHandlerService;
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        UserChannel.groupAdd(ctx.channel());
        ctx.channel().writeAndFlush("链接成功");
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
            UserChannel.groupAdd(ctx.channel());
            WebSocketServerProtocolHandler.HandshakeComplete handshakeComplete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String s = handshakeComplete.requestUri();
            log.info("请求 remote={}- id={} - uri={}",ctx.channel().remoteAddress(),ctx.channel().id(),s);
            super.userEventTriggered(ctx,evt);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("连接断开 剩余客户端 {}",UserChannel.getGroups().size());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof IdleStateEvent){
            return;
        }
        if(msg instanceof TextWebSocketFrame){
            //解析非心跳数据
            TextWebSocketFrame message = (TextWebSocketFrame) msg;
            log.info("业务处理消息 message={}",message.text());
            NettyMessage nettyMessage = JSONObject.parseObject(message.text(), NettyMessage.class);
            try {
                userHandlerService.chatMessage(nettyMessage);
            }catch (Exception e){
                log.error("业务处理异常");
                log.error(e.getMessage());

            }
        }
        /*if (msg instanceof DefaultHttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            String uri = request.uri();
            String id = uri.split("/")[2];
            log.info(" 读取到id={}",id);
            request.setUri("/ws");
        }*/
        super.channelRead(ctx, msg);
    }





    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("异常处理 error={}",cause.getMessage());
        ctx.close();
    }
}
