package com.learn.platform;

import cn.hutool.http.Method;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.DefaultTransportOptions;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportOptions;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.alibaba.fastjson2.JSONObject;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws IOException {
        String jsonString = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";
        Request request = new Request("POST", "/my_index/_doc/1");
        RestClient client = RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")).build();
        request.setEntity(new NStringEntity(jsonString, ContentType.APPLICATION_JSON));
        Response response = client.performRequest(request);
        System.out.println(response);
    }

    public void testget() throws IOException {
        Request request = new Request(Method.GET.name(), "/my_index/_doc/1");

        RestClient client = RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")).build();
        Response response = client.performRequest(request);
        System.out.println(JSONObject.toJSONString(response));

    }



    public void testEsClient(){
        HttpHost httpHost = new HttpHost("127.0.0.1", 9200, "http");
        RestClient restClient = RestClient.builder(httpHost).build();
        JsonpMapper mapper = new JacksonJsonpMapper();
        ElasticsearchTransport transport = new RestClientTransport(restClient,mapper);
        TransportOptions transportOptions = new DefaultTransportOptions();
        ElasticsearchClient elasticsearchClient = new ElasticsearchClient(transport,transportOptions);

    }

}
