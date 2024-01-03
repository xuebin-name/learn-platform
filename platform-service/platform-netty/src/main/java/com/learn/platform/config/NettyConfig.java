package com.learn.platform.config;

import com.learn.platform.handler.MyWebSocketHandler;
import com.learn.platform.listern.NettyListenerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * @ClassName NettyConfig
 * @Description netty配置类
 * @Author xue
 * @Date 2023/12/26 10:03
 */

@Slf4j
@Configuration
public class NettyConfig {


    @Value("${netty.port}")
    private Integer nettyPort;
    @Value("${netty.address}")
    private String address;

    @Autowired
    private NettyServerInitializer nettyServerInitializer;

    @Autowired
    private NettyListenerHandler nettyListenerHandler;

    private NioEventLoopGroup bossGroup;

    private NioEventLoopGroup workerGroup;

    private ChannelFuture channelFuture;

    @Autowired
    private MyWebSocketHandler myWebSocketHandler;

    @Autowired
    private NettyWebSocketInitializer nettyWebSocketInitializer;


    @PostConstruct
    public void bind() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            channelFuture = serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    /*.option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(nettyServerInitializer)*/
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    //.childHandler(nettyServerInitializer)
                    .childHandler(nettyWebSocketInitializer)
                    .bind(new InetSocketAddress(address, nettyPort));
            channelFuture.sync();
            channelFuture.addListener(nettyListenerHandler->log.info("监听器启动"));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void shutDown() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        log.info("netty---服务关闭");
    }
}
