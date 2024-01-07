package com.learn.platform.service.Impl;

import cn.hutool.http.Method;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.alibaba.fastjson2.JSONObject;
import com.learn.platform.service.es.PlatformEsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @ClassName PlatformEsServiceImpl
 * @Description es业务类
 * @Author xue
 * @Date 2023/12/21 10:12
 */
@Slf4j
@DubboService
@Service
public class PlatformEsServiceImpl implements PlatformEsService {


    @Autowired
    RestClient restClient;
    @Autowired
    ElasticsearchClient elasticsearchClient;

    /**
     * 创建索引
     *
     * @param indexName 索引名称
     * @return Response 响应结果
     * @throws Exception 异常
     */
    public Response createIndex(String indexName) throws Exception {
        Request request = new Request(Method.POST.name(), "/" + indexName);
        return restClient.performRequest(request);

    }

    /**
     * 创建索引
     *
     * @param indexName 索引名称
     * @return boolean 是否成功
     */
    public boolean createIndexList(String indexName)throws Exception {
        CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(index->index.index(indexName));
        return createIndexResponse.acknowledged();
    }

    /**
     * 获取缩影内容
     * @param indexName 索引名称
     * @throws Exception
     */
    public GetIndexResponse getindex(String indexName) throws Exception {
        GetIndexResponse response = elasticsearchClient.indices().get(req->req.index(indexName));
        log.info("获取索引结果 response={}", JSONObject.toJSONString(response));
        return response;
    }


    public void search(String field,String query) throws IOException {
        MatchQuery matchQuery = new MatchQuery.Builder()
                .field(field)
                .query(query)
                .build();
        Query query1 = new Query.Builder().match(matchQuery).build();
        SearchRequest searchRequest = new SearchRequest.Builder()
                .query(query1)
                .build();
        SearchResponse<Object> response = elasticsearchClient.search(searchRequest, Object.class);
        log.info("查询结果 result={}",JSONObject.toJSONString(response));
    }

    public boolean existsIndex(String indexName) throws IOException {
        BooleanResponse exists = elasticsearchClient.indices().exists(a -> a.index(indexName));
        return exists.value();

    }
}
