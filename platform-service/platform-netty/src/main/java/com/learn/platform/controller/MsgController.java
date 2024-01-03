package com.learn.platform.controller;

import com.learn.platform.entity.message.NettyMessage;
import com.learn.platform.server.UserHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MsgController
 * @Description TODO
 * @Author xue
 * @Date 2024/1/3 14:04
 */

@Slf4j
@RequestMapping("/msg")
@RestController
public class MsgController {

    @Autowired
    private UserHandlerService userHandlerService;


    @RequestMapping("/send")
    public void sendMsg(String msg){
        userHandlerService.sendMsg(msg);
    }

    @PostMapping("/message")
    public void sendMsg(@RequestBody NettyMessage message){
        userHandlerService.sendMsg(message);
    }
}
