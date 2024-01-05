package com.learn.platform.entity.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName IndexReq
 * @Description es用户索引
 * @Author xue
 * @Date 2024/1/5 16:29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndexReq implements Serializable {
    @Serial
    private static final long serialVersionUID = -1256057673048531544L;

    private String indexName;
}
