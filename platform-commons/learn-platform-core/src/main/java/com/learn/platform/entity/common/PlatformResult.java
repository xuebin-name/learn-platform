package com.learn.platform.entity.common;

import com.learn.platform.entity.enums.ResponseCodeEnum;
import lombok.Data;

/**
 * @ClassName PlatformResult
 * @Description 平台通用返回参数
 * @Author xue
 * @Date 2023/12/15 15:27
 */
@Data
public class PlatformResult<T> {

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
