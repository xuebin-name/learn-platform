package com.learn.platform.controlelr;

import com.learn.platform.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RedisController
 * @Description TODO
 * @Author xue
 * @Date 2024/3/26 11:33
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    RedisService redisService;

    @PostMapping("/setKey")
    public void setKey(@RequestParam("key") String key,@RequestParam("value") String value,@RequestParam("time") int time){
        redisService.setEx(key,value,time);
    }
}
