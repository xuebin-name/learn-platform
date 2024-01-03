package com.learn.platform.decode;

import com.alibaba.fastjson2.JSONObject;
import com.learn.platform.entity.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @ClassName NettyServerEncode
 * @Description netty服务 编码器
 * @Author xue
 * @Date 2023/12/27 9:25
 */
@Slf4j
public class NettyServerEncode extends MessageToMessageEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        log.info("编码器---------------------");
        String jsonString = JSONObject.toJSONString(msg);
        log.info("需要编码的消息 msg={}", jsonString);
        NettyMessage nettyMessage = JSONObject.parseObject(jsonString, NettyMessage.class);
        out.add(nettyMessage);
    }
}
