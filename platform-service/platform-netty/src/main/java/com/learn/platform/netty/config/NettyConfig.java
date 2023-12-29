package com.learn.platform.netty.config;

import com.learn.platform.netty.handler.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName NettyConfig
 * @Description netty配置类
 * @Author xue
 * @Date 2023/12/26 10:03
 */

@Slf4j
@Configuration
public class NettyConfig {
    //保存用户对应通道
    private static final Map<String, Channel> channels = new ConcurrentHashMap<>(16);

    @Value("${netty.port}")
    private Integer nettyPort;
    @Value("${netty.address}")
    private String address;

    public void putChannel(String key,Channel channel){
        channels.put(key,channel);
    }
    public Channel getChannel(String key){
        return channels.get(key);
    }

    public List<Channel> channels(){
        return channels.values().stream().toList();
    }
    @Bean
    public EventLoopGroup bossGroup(){
        return new NioEventLoopGroup();
    }
    @Bean
    public EventLoopGroup workerGroup() {
        return new NioEventLoopGroup();
    }

    @Bean
    public ServerBootstrap serverBootstrap() {
        return new ServerBootstrap();
    }

    @Bean
    public ChannelInitializer<SocketChannel> channelInitializer() {
        return new ChannelInitializer<>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                log.info("netty 设置初始化信息------------------------------start----");
                // 日志打印
                socketChannel.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                // 基于换行符号
                //socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                // 设置解码
                socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
                // 设置编码NettyServerDecode
                socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                //添加自定义接受数据处理方法
                socketChannel.pipeline().addLast(new MyServerHandler());
                //添加心跳机制检测
                //socketChannel.pipeline().addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
                //socketChannel.pipeline().addLast(new HeartBeatHandler());

                log.info("netty 设置初始化信息------------------------------end----");
            }
        };
    }

    @Bean
    public ChannelFuture bind(ServerBootstrap serverBootstrap, EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        return serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                //.childOption(ChannelOption.SO_KEEPALIVE, true)S
                .childHandler(channelInitializer())
                .bind(new InetSocketAddress(address,nettyPort))
                ;
    }
}
