package com.learn.platform.dao.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.learn.platform.entity.es.EsCommonReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName PlatformEsDaoImpl
 * @Description TODO
 * @Author xue
 * @Date 2023/12/21 17:12
 */
@Slf4j
@Service
public class PlatformEsDaoImpl {


    @Autowired
    ElasticsearchClient elasticsearchClient;

    /**
     * 创建索引
     *
     * @param reqList 索引列表
     * @throws IOException 异常
     */
    public boolean createIndex(List<EsCommonReq> reqList) throws IOException {
        if (CollectionUtils.isEmpty(reqList)) {
            return false;
        }
        boolean result = true;
        for (EsCommonReq userIndex : reqList) {
            BooleanResponse exists = elasticsearchClient.indices().exists(ExistsRequest.of(a -> a.index(userIndex.getIndex())));
            if (exists.value()) {
                continue;
            }
            CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(a -> a.index(userIndex.getIndex()));
            result = result && createIndexResponse.acknowledged();
        }
        return result;
    }


    public boolean createIndex(EsCommonReq req) throws IOException {
        if (ObjectUtils.isEmpty(req)) {
            return false;
        }
        BooleanResponse exists = elasticsearchClient.indices().exists(ExistsRequest.of(a -> a.index(req.getIndex())));
        if (exists.value()) {
            log.info("当前索引已存在");
        }
        CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(a -> a.index(req.getIndex()));
        return createIndexResponse.acknowledged();
    }
}
