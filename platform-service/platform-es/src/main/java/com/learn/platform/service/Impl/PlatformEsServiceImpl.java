package com.learn.platform.service.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.alibaba.fastjson2.JSONObject;
import com.learn.platform.entity.IndexReq;
import com.learn.platform.entity.es.DocReq;
import com.learn.platform.entity.es.SearchReq;
import com.learn.platform.entity.es.dto.EsUser;
import com.learn.platform.service.es.PlatformEsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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


    /*  @Autowired
      RestClient restClient;*/
    @Autowired
    ElasticsearchClient elasticsearchClient;

    /**
     * 创建索引
     *
     * @param req 索引名称
     * @return Response 响应结果
     * @throws Exception 异常
     */
    public CreateIndexResponse createIndex(IndexReq req) throws Exception {
        boolean value = elasticsearchClient.indices().exists(ExistsRequest.of(a -> a.index(req.getIndexName()))).value();
        if (value) {
            throw new IllegalArgumentException("当前索引已存在");
        }
        CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(CreateIndexRequest.of(a -> a.index(req.getIndexName())
                .mappings(m -> m.properties(req.getMappings()))
        ));

        return createIndexResponse;
    }

    /**
     * 批量添加数据
     *
     * @param indexName
     * @param list
     */
    public void addDataForIndex(String indexName, List list) {
        List<BulkOperation> bulkOperations = new ArrayList<>();
        try {
            for (Object o : list) {
                bulkOperations.add(new BulkOperation.Builder().create(d -> d.document(o).id(UUID.randomUUID().toString().toUpperCase())).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取索引内容
     *
     * @param indexName 索引名称
     * @throws Exception
     */
    public GetIndexResponse getindex(String indexName) throws Exception {
        GetIndexResponse response = elasticsearchClient.indices().get(req -> req.index(indexName));
        log.info("获取索引结果 response={}", JSONObject.toJSONString(response));
        log.info("结果集{}", response.result());
        return response;
    }

    /**
     * 删除索引
     *
     * @param indexName
     * @throws IOException
     */
    public void deleteIndex(String indexName) throws IOException {
        DeleteIndexResponse delete = elasticsearchClient.indices().delete(DeleteIndexRequest.of(a -> a.index(indexName)));
        log.info("删除索引结果{}", delete.acknowledged());
    }

    /**
     * 创建文档
     *
     * @param req
     * @throws IOException
     */
    public void createDoc(DocReq req) throws IOException {
        CreateResponse response = elasticsearchClient.create(CreateRequest.of(a -> a.index(req.getIndexName()).document(req.getEsUser()).id(UUID.randomUUID().toString())));
        log.info("创建结果{}", response.result());
    }

    public Object updateDoc(DocReq req) throws IOException {
        UpdateResponse<EsUser> update = elasticsearchClient.update(
                UpdateRequest.of(a -> a.index(req.getIndexName())
                        .id(req.getId())
                        .doc(req.getEsUser())
                )
                , EsUser.class
        );
        return update.get().source();
    }

    /**
     * 删除文档
     *
     * @param req
     * @throws IOException
     */
    public void deleteDoc(DocReq req) throws IOException {
        DeleteResponse delete = elasticsearchClient.delete(DeleteRequest.of(a -> a.index(req.getIndexName()).id(req.getId())));
        log.info("删除结果{}", delete.result().jsonValue());
    }

    /**
     * 查询
     *
     * @param req 请求参数
     * @return
     * @throws IOException
     */
    public Object search(SearchReq req) throws IOException {
        MatchQuery matchQuery = new MatchQuery.Builder()
                .field(req.getField())
                .query(req.getQuery())
                .build();
        Query query1 = new Query.Builder().match(matchQuery).build();
        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(req.getIndexName())
                .query(query1)
                .highlight(Highlight.of(a -> a.fields(req.getField(), HighlightField.of(b -> b.matchedFields(Collections.singletonList(req.getQuery()))))))
                .build();
        SearchResponse<EsUser> response = elasticsearchClient.search(searchRequest, EsUser.class);
        log.info("查询结果 result={}", response.hits());
        for (Hit<EsUser> hit : response.hits().hits()) {
            EsUser source = hit.source();
            assert source != null;
            Map<String, List<String>> highlight = hit.highlight();
            if (ObjectUtils.isNotEmpty(highlight)) {
                source.setHighLight(highlight.get(req.getField()).get(0));
            }
        }
        return response.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
    }

    public boolean existsIndex(String indexName) throws IOException {
        BooleanResponse exists = elasticsearchClient.indices().exists(a -> a.index(indexName));
        return exists.value();

    }
}
