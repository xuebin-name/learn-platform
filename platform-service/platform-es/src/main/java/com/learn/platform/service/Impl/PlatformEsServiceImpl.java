package com.learn.platform.service.Impl;

import cn.hutool.http.Method;
import com.learn.platform.service.es.PlatformEsService;
import org.apache.dubbo.config.annotation.DubboService;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName PlatformEsServiceImpl
 * @Description es业务类
 * @Author xue
 * @Date 2023/12/21 10:12
 */
@DubboService
@Service
public class PlatformEsServiceImpl implements PlatformEsService {


    @Autowired
    RestClient restClient;

    /**
     * 创建索引
     * @param indexName 索引名称
     * @return Response 响应结果
     * @throws Exception 异常
     */
    public Response createIndex(String indexName) throws Exception {
        Request request = new Request(Method.POST.name(), "/" + indexName);
        return restClient.performRequest(request);

    }

}
