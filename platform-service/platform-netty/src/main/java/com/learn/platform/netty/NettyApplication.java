package com.learn.platform.netty;

import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName NettyApplication
 * @Description TODO
 * @Author xue
 * @Date 2023/12/26 10:03
 */
@SpringBootApplication
public class NettyApplication implements CommandLineRunner {

    @Autowired
    private ChannelFuture channelFuture;
    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
        System.out.println("==============================================================");
        System.out.println("====================netty-service服务 启动成功===================");
        System.out.println("==============================================================");
    }

    @Override
    public void run(String... args) throws Exception {
        channelFuture.sync();
    }
}
