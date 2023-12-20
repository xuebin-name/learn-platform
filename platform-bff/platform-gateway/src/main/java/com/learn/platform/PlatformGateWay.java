package com.learn.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关服务
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PlatformGateWay {
    public static void main(String[] args) {
        SpringApplication.run(PlatformGateWay.class, args);
        System.out.println("==============================================================");
        System.out.println("==================platform-gateway 服务 启动成功================");
        System.out.println("==============================================================");
    }
}
