package com.learn.platform.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName Permission
 * @Description TODO
 * @Author xue
 * @Date 2024/1/5 14:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission implements Serializable {
    @Serial
    private static final long serialVersionUID = -8124870006076119266L;

    private Integer id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 地址
     */
    private String uri;
    /**
     * 方法
     */
    private String method;

    private Date gmtCreate;

    private Date gmtUpdate;
}
