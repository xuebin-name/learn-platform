package com.learn.platform.controller;

import com.learn.platform.annotation.WebLog;
import com.learn.platform.common.PlatformResult;
import com.learn.platform.po.User;
import com.learn.platform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserService userService;

    /**
     * 根据id获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/byId")
    @WebLog(description = "根据id获取用户信息")
    public PlatformResult<User> selectUser(Integer id) {
        return PlatformResult.success(userService.selectById(id));
    }

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return 是否成功
     */
    @PostMapping("/insert")
    @WebLog(description = "新增用户")
    public PlatformResult<Void> insert(@RequestBody User user) {
        userService.insert(user);
        return PlatformResult.success();
    }
}
