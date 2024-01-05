package com.learn.platform;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName UserApplication
 * @Description 用户服务主启动类
 * @Author xue
 * @Date 2023/12/15 16:16
 */

@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
        System.out.println("==============================================================");
        System.out.println("======================user-bff 服务 启动成功====================");
        System.out.println("==============================================================");
    }
}
