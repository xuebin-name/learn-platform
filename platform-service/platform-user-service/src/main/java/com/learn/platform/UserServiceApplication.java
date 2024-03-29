package com.learn.platform;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName UserServiceApplication
 * @Description TODO
 * @Author xue
 * @Date 2023/12/18 14:33
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
        System.out.println("==============================================================");
        System.out.println("====================user-service服务 启动成功===================");
        System.out.println("==============================================================");
    }
}
