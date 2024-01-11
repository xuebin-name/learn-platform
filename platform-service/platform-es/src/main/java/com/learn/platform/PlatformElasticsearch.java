package com.learn.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName PlatformElasticsearch
 * @Description es服务端
 * @Author xue
 * @Date 2023/12/20 14:41
 */
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchClientAutoConfiguration.class,
        org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration.class,})
public class PlatformElasticsearch {

    public static void main(String[] args) {
        SpringApplication.run(PlatformElasticsearch.class, args);
        System.out.println("=======================================================================");
        System.out.println("====================PlatformElasticsearch服务 启动成功===================");
        System.out.println("=======================================================================");
    }
}
