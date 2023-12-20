package com.learn.platform;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import okhttp3.*;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testGpt(){

        try {
            String proxyHost = "127.0.0.1"; // 代理主机
            int proxyPort = 7890; // 代理端口号

            // 创建代理对象
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

            // 创建 OkHttpClient 并设置代理
            OkHttpClient client = new OkHttpClient.Builder()
                    .proxy(proxy)
                    .build();

            // 构建请求体数据
            String requestBodyData = "{\"prompt\": \"Once upon a time\", \"max_tokens\": 50}";

            // 构建请求体
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(mediaType, requestBodyData);

            // 构建请求对象
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/models")
                    //.post(requestBody)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer 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")
                    .build();

            // 发送请求并获取响应
            Response response = client.newCall(request).execute();
            System.out.println(response);
            // 处理响应...
            if (response.isSuccessful()) {
                // 输出响应内容
                System.out.println("Response Body: " + response.body().string());
            } else {
                System.out.println("Request failed");
            }

            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        try {
            String proxyHost = "127.0.0.1"; // 代理主机
            int proxyPort = 7890; // 代理端口号
            String model = "gpt-3.5-turbo-16k";

            // 创建代理对象
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

            // 创建 OkHttpClient 并设置代理
            OkHttpClient client = new OkHttpClient.Builder()
                    .proxy(proxy)
                    .build();

            // 构建请求体数据
            String requestBodyData ="{\"prompt\": \"Once upon a time\", \"max_tokens\": 50, \"model\": \"gpt-3.5-turbo-16k\"}";

            // 构建请求体
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(mediaType, requestBodyData);

            // 构建请求对象
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .post(requestBody)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer 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")
                    .build();

            // 发送请求并获取响应
            Response response = client.newCall(request).execute();
            System.out.println(response);
            // 处理响应...
            if (response.isSuccessful()) {
                // 输出响应内容
                System.out.println("Response Body: " + response.body().string());
            } else {
                System.out.println("Request failed");
            }

            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
