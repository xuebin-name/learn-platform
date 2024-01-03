package com.learn.platform.config;

import com.learn.platform.handler.HeartBeatHandler;
import com.learn.platform.handler.MyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName NettyServerInitializer
 * @Description TODO
 * @Author xue
 * @Date 2024/1/2 17:44
 */

@Slf4j
@Component
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private MyServerHandler myServerHandler;
    @Autowired
    private HeartBeatHandler heartBeatHandler;
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //String delimiter = "@_";
        log.info("netty 设置初始化信息------------------------------start----");
        // 日志打印
        socketChannel.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
        socketChannel.pipeline().addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
        //限制每次传输的数据长度
        //socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024*8, Unpooled.wrappedBuffer(delimiter.getBytes())));
        // 设置解码
        socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 设置编码NettyServerDecode
        socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        //添加心跳机制检测
        socketChannel.pipeline().addLast(heartBeatHandler);
        //添加自定义接受数据处理方法
        socketChannel.pipeline().addLast(myServerHandler);

        log.info("netty 设置初始化信息------------------------------end----");
    }
}
