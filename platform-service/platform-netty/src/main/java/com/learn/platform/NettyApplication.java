package com.learn.platform;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName NettyApplication
 * @Description TODO
 * @Author xue
 * @Date 2023/12/26 10:03
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class NettyApplication {


    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
        System.out.println("==============================================================");
        System.out.println("====================netty-service服务 启动成功===================");
        System.out.println("==============================================================");
    }


}
