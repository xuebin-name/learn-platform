package com.learn.platform.config;

import com.learn.platform.handler.HeartBeatHandler;
import com.learn.platform.handler.MyWebSocketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName NettyWebSocketInitializer
 * @Description TODO
 * @Author xue
 * @Date 2024/1/3 10:30
 */
@Component
public class NettyWebSocketInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private MyWebSocketHandler myWebSocketHandler;

    @Autowired
    private HeartBeatHandler heartBeatHandler;
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //socketChannel.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
        socketChannel.pipeline().addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
        //http请求解码编码器
        pipeline.addLast(new HttpServerCodec());

        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws/alarmHost",true));
        pipeline.addLast(heartBeatHandler);
        pipeline.addLast(myWebSocketHandler);
    }
}
