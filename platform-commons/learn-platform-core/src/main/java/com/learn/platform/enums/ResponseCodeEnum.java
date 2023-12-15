package com.learn.platform.enums;

import lombok.Getter;

@Getter
public enum ResponseCodeEnum {
    /**
     * 成功状态码
     */
    SUCCESS(200,"处理成功"),

    FAIL(500,"系统异常"),

    ;

    private final int code;

    private final String message;

    ResponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
