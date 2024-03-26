package com.learn.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @ClassName CacheApplication
 * @Description TODO
 * @Author xue
 * @Date 2024/3/26 11:25
 */
@EnableCaching
@SpringBootApplication
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class,args);
        System.out.println("==============================================================");
        System.out.println("======================cache-service 服务 启动成功====================");
        System.out.println("==============================================================");
    }
}
