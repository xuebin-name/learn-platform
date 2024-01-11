package com.learn.platform.entity.es.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName EsUser
 * @Description TODO
 * @Author xue
 * @Date 2024/1/11 11:02
 */
@Data
public class EsUser implements Serializable {
    @Serial
    private static final long serialVersionUID = -2027027300394779380L;

    private String name;

    private String age;

    private String highLight;
}
