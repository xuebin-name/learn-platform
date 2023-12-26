package com.learn.platform.netty.server;

import com.learn.platform.netty.initialzer.NettyServerInitializer;
import com.learn.platform.netty.listern.NettyListenerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @ClassName NettyServer
 * @Description netty 服务
 * @Author xue
 * @Date 2023/12/26 10:09
 */
@Slf4j
@Component
public class NettyServer {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @Getter
    private Channel channel;

    @Value("${netty.port}")
    private Integer nettyPort;
    @Value("${netty.address}")
    private String address;


    public void bind()  {
        ChannelFuture channelFuture = null;
        try {
            log.info("netty----开始启动------");
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)

                    .childHandler(new NettyServerInitializer());
            channelFuture = serverBootstrap.bind(new InetSocketAddress(address,nettyPort)).syncUninterruptibly();
            channelFuture.addListener(new NettyListenerHandler());
            channel = channelFuture.channel();
            log.info("netty----启动完毕------");
        }catch (Exception e){
            log.error("netty--启动错误--");
        }finally {
            if(null!= channelFuture && channelFuture.isSuccess()){
                log.info("netty--server---start--done-");
            }
        }
    }


    public void stop() {
        if(null == channel){
            return;
        }
        channel.close();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
