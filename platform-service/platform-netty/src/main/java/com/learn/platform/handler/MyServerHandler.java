package com.learn.platform.handler;

import com.alibaba.fastjson2.JSONObject;
import com.learn.platform.config.UserChannel;
import com.learn.platform.entity.message.NettyMessage;
import com.learn.platform.service.netty.UserChatService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyServerHandler
 * @Description netty消息处理类
 * @Author xue
 * @Date 2023/12/28 15:40
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class MyServerHandler extends ChannelInboundHandlerAdapter {

   //private Map<String, Channel> userChannelMap = new ConcurrentHashMap<>(16);

    @DubboReference
    @Resource
    private UserChatService userChatService;

    /**
     * 通道激活 处理上限逻辑
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道激活");
        Channel channel = ctx.channel();
        UserChannel.groupAdd(channel);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道关闭");
        UserChannel.remove(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if("ping".equals(msg)){
            return;
        }
        log.info("通道读取");
        String jsonStr = JSONObject.toJSONString(msg);
        log.info("读取到数据 {}", jsonStr);
        NettyMessage message = JSONObject.parseObject(msg.toString(),NettyMessage.class);
        log.info("message={}",JSONObject.toJSONString(message));
        UserChannel.put(message.getSendUserId(),ctx.channel());
        userChatService.saveUserMessage(message);
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
