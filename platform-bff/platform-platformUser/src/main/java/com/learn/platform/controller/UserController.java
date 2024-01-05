package com.learn.platform.controller;

import com.learn.platform.annotation.WebLog;
import com.learn.platform.entity.common.PlatformResult;
import com.learn.platform.entity.po.PlatformUser;
import com.learn.platform.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * @ClassName UserController
 * @Description 用户controller
 * @Author xue
 * @Date 2023/12/15 15:02
 */
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController implements Serializable {
    private static final long serialVersionUID = 8323383919435006388L;

    @DubboReference
    private UserService userService;

    @Value("${testValue}")
    private String testValue;

    @GetMapping("/test")
    public PlatformResult<String> test(){
        return PlatformResult.success(testValue);
    }

    /**
     * 根据id获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/byId")
    @WebLog(description = "根据id获取用户信息")
    public PlatformResult<PlatformUser> selectUser(Integer id) {
        return PlatformResult.success(userService.selectById(id));
    }

    /**
     * 新增用户
     *
     * @param platformUser 用户信息
     * @return 是否成功
     */
    @PostMapping("/insert")
    @WebLog(description = "新增用户")
    public PlatformResult<Void> insert(@RequestBody PlatformUser platformUser) {
        userService.insert(platformUser);
        return PlatformResult.success();
    }
}
