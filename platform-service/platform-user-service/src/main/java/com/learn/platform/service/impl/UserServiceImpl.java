package com.learn.platform.service.impl;

import com.learn.platform.po.User;
import com.learn.platform.service.UserService;
import com.learn.platform.service.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;


public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public User selectByPlatformId(String platformId) {
        return userDao.selectByPlatformId(platformId);
    }
}
