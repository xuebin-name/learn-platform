package com.learn.platform.controller;

import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import com.learn.platform.entity.common.PlatformResult;
import com.learn.platform.service.Impl.PlatformEsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName $NAME.java
 * Description: Es 试图层
 * Author: xue
 * Date: 2024/1/6 10:50
 **/
@Slf4j
@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private PlatformEsServiceImpl platformEsService;

    /**
     * @param indexName 索引名称
     * @return 是否成功
     * @throws Exception
     */
    @PostMapping("/createIndex")
    public PlatformResult<Object> createIndex(String indexName) throws Exception {
        Response index = platformEsService.createIndex(indexName);
        return PlatformResult.success(index);
    }

    @GetMapping("/getIndex")
    public PlatformResult<Object> getIndex(String indexName) throws Exception {
        GetIndexResponse getindex = platformEsService.getindex(indexName);
        return PlatformResult.success(getindex);
    }
}
