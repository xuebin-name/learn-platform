package com.learn.platform.netty.decode;

import com.alibaba.fastjson2.JSONObject;
import com.learn.platform.entity.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @ClassName NettyServerDecode
 * @Description TODO
 * @Author xue
 * @Date 2023/12/26 15:33
 */
@Slf4j
public class NettyServerDecode extends MessageToMessageEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        String jsonString = JSONObject.toJSONString(msg);
        log.info("需要解码的消息 msg={}", jsonString);
        NettyMessage nettyMessage = JSONObject.parseObject(jsonString, NettyMessage.class);
        out.add(nettyMessage);
    }
}
