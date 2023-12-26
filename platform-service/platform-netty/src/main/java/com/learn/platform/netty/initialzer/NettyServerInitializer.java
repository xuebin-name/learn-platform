package com.learn.platform.netty.initialzer;

import com.learn.platform.netty.decode.NettyServerDecode;
import com.learn.platform.netty.handler.NettyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @ClassName NettyServerInitialzer
 * @Description 自定义初始化处理器
 * @Author xue
 * @Date 2023/12/26 10:25
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 日志打印
        socketChannel.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
        // 基于换行符号
        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 设置解码
        socketChannel.pipeline().addLast(new NettyServerDecode());
        // 设置编码NettyServerDecode
        socketChannel.pipeline().addLast(new NettyServerDecode());
        //添加自定义接受数据处理方法
        socketChannel.pipeline().addLast(new NettyServerHandler());


    }
}
