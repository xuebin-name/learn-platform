package com.learn.platform.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ElasticsearchConfig
 * @Description es 配之类
 * @Author xue
 * @Date 2023/12/21 10:58
 */
@Configuration
public class ElasticsearchConfig {


    @Value("${spring.elasticsearch.uris}")
    private String[] uris;
    @Value("${spring.elasticsearch.connection-timeout}")
    private Integer connectionTimeout;
    @Value("${spring.elasticsearch.port}")
    private Integer port;
    @Value("${spring.elasticsearch.host}")
    private String host;
    @Value("${spring.elasticsearch.username}")
    private String userName;
    @Value("${spring.elasticsearch.password}")
    private String password;


    @Bean
    public RestClient restClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
        HttpHost httpHost = new HttpHost(host, port, "http");
        RestClientBuilder builder = RestClient.builder(httpHost)
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    //禁用抢占式身份验证
                    httpClientBuilder.disableAuthCaching();
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                });
        return builder.build();
    }






}
