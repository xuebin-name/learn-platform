package com.learn.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName UserApplication
 * @Description 用户服务主启动类
 * @Author xue
 * @Date 2023/12/15 16:16
 */

@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
        System.out.println("=====================用户服务启动成功=====================");
    }
}
