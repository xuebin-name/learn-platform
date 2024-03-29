package com.learn.platform.entity.common;

import com.learn.platform.entity.enums.ResponseCodeEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName PlatformResult
 * @Description 平台通用返回参数
 * @Author xue
 * @Date 2023/12/15 15:27
 */
@Data
public class PlatformResult<T> implements Serializable {


    @Serial
    private static final long serialVersionUID = 5738196694720700717L;
    private int code;

    private String message;

    private T data;

    public static <T> PlatformResult<T> success() {
        return result(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(), null);
    }

    public static <T> PlatformResult<T> success(T data) {
        return result(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(), data);
    }
    public static <T> PlatformResult<T> success(T data,String message) {
        return result(ResponseCodeEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> PlatformResult<T> fail(T data) {
        return result(ResponseCodeEnum.FAIL.getCode(), ResponseCodeEnum.FAIL.getMessage(), data);
    }

    public static <T> PlatformResult<T> fail() {
        return result(ResponseCodeEnum.FAIL.getCode(), ResponseCodeEnum.FAIL.getMessage(), null);
    }

    public static <T> PlatformResult<T> fail(T data,String message) {
        return result(ResponseCodeEnum.FAIL.getCode(), message, data);
    }

    public PlatformResult() {
    }

    public PlatformResult(ResponseCodeEnum codeEnum, String message, T data) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
        this.data = data;
    }

    public static <T> PlatformResult<T> result(int code, String message, T data) {
        PlatformResult<T> result = new PlatformResult<>();
        result.setCode(code);
        result.setData(data);
        result.setMessage(message);
        return result;
    }
}
