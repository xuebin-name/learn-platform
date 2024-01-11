package com.learn.platform.controller;

import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import com.learn.platform.annotation.WebLog;
import com.learn.platform.entity.common.PlatformResult;
import com.learn.platform.entity.IndexReq;
import com.learn.platform.entity.es.SearchReq;
import com.learn.platform.service.Impl.PlatformEsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    @WebLog
    @PostMapping("/createIndex")
    public PlatformResult<Object> createIndex(@RequestBody IndexReq req) throws Exception {
        return PlatformResult.success(platformEsService.createIndex(req));
    }
    @WebLog
    @GetMapping("/getIndex")
    public PlatformResult<Object> getIndex(String indexName) throws Exception {
        GetIndexResponse getindex = platformEsService.getindex(indexName);
        log.info("返回数据{}",getindex.result().get(indexName));
        return PlatformResult.success(getindex.result().get(indexName));
    }


    @PostMapping("/search")
    @ResponseBody
    @WebLog
    public PlatformResult<Object> search(@RequestBody SearchReq req) throws IOException {
        return PlatformResult.success(platformEsService.search(req));
    }



}
