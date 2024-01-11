package com.learn.platform.entity.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName SearchReq
 * @Description es search请求
 * @Author xue
 * @Date 2024/1/11 9:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchReq implements Serializable {
    @Serial
    private static final long serialVersionUID = 4684428050895257549L;
    /**
     * 索引名称
     */
    private String indexName;
    /**
     * 查询内容
     */
    private String query;
    /**
     * 查询的列
     */
    private String field;
}

