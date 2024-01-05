package com.learn.platform.entity.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName EsCommonReq
 * @Description es 公共请求实体
 * @Author xue
 * @Date 2023/12/21 17:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsCommonReq implements Serializable {
    @Serial
    private static final long serialVersionUID = -7735877813301738161L;
    /**
     * 索引id
     */
    private String id;

    private String index;
    /**
     * json请求体
     */
    private String jsonEntity;
}
