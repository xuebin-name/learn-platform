package com.learn.platform.controller;

import com.alibaba.fastjson2.JSONObject;
import com.learn.platform.annotation.WebLog;
import com.learn.platform.entity.po.User;
import com.learn.platform.service.RedisService;
import com.learn.platform.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserCacheController
 * @Description TODO
 * @Author xue
 * @Date 2023/12/22 13:59
 */
@Slf4j
@RestController
@RequestMapping("/user/cache")
public class UserCacheController {

    @Autowired
    private RedisService redisService;

    @DubboReference
    private UserService userService;

    @WebLog
    @GetMapping("/cacheUser")
    public void setKey(Integer id){
        User user = userService.selectById(id);
        redisService.putObject(user.getPlatformId(),user);
    }
    @WebLog
    @GetMapping("/getUser")
    public Object getCacheUser(String platformId){
        Object object = redisService.getObject(platformId);
        log.info(JSONObject.toJSONString(object));
        return object;
    }
}
