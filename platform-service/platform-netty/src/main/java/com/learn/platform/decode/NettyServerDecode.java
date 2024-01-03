package com.learn.platform.decode;

import com.alibaba.fastjson2.JSONObject;
import com.learn.platform.entity.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @ClassName NettyServerDecode
 * @Description netty服务解码器
 * @Author xue
 * @Date 2023/12/26 15:33
 */
@Slf4j
public class NettyServerDecode extends MessageToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        log.info("解码器---------------------------------------");
        String jsonString = JSONObject.toJSONString(msg);
        log.info("需要解码的消息 msg={}", jsonString);
        NettyMessage nettyMessage = JSONObject.parseObject(jsonString, NettyMessage.class);
        out.add(nettyMessage);
    }

}
