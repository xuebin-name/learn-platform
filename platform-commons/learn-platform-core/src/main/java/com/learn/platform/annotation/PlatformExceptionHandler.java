package com.learn.platform.annotation;

import com.learn.platform.entity.common.PlatformResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName PlatformExceptionHandler
 * @Description TODO
 * @Author xue
 * @Date 2024/2/4 14:40
 */
@Slf4j
@ControllerAdvice
public class PlatformExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public PlatformResult<String> exceptionHandler(HttpServletRequest request,Exception e){
        String errMsg = String.format("来至 %s 的请求发生以下错误", request.getRequestURL());
        log.error(errMsg,e);
        return PlatformResult.fail();
    }

    @ResponseBody
    @ExceptionHandler(value = IllegalAccessException.class)
    public PlatformResult<String> illegalAccessExceptionHandler(HttpServletRequest request,Exception e){
        String errMsg = String.format("来至 %s 的请求发生以下错误", request.getRequestURL());
        log.error(errMsg,e);
        return PlatformResult.fail();
    }
}
