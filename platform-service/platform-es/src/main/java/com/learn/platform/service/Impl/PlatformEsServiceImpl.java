package com.learn.platform.service.Impl;

import com.learn.platform.service.es.PlatformEsService;
import org.apache.dubbo.config.annotation.DubboService;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
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

    public void createIndex(String indexName) throws Exception {
        Request request = new Request("PUT", "/" + indexName);
        restClient.performRequestAsync(request, new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                System.out.println("success"+response);
            }

            @Override
            public void onFailure(Exception exception) {
                System.out.println("failure"+exception);
            }
        });
    }

}
