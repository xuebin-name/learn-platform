package com.learn.platform.entity.es;

import com.learn.platform.entity.es.dto.EsUser;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName DocReq
 * @Description TODO
 * @Author xue
 * @Date 2024/1/12 14:23
 */
@Data
public class DocReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 4277099889204752389L;
    private String indexName;

    private String id;

    private EsUser esUser;
}
