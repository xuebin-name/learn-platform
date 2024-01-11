package com.learn.platform.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
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
    public ElasticsearchClient elasticsearchClient(){
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

        RestClient client = RestClient.builder(new HttpHost(host, port))
                .setHttpClientConfigCallback(httpAsyncClientBuilder ->
                        httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
                .build();
        ElasticsearchTransport transport = new RestClientTransport(client, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }






}
