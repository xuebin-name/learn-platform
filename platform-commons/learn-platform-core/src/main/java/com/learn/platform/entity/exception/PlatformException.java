package com.learn.platform.entity.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName PlatformException
 * @Description 自定义异常类
 * @Author xue
 * @Date 2024/2/4 14:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = -2778655468656486117L;

    private String code;

    private String message;


}
