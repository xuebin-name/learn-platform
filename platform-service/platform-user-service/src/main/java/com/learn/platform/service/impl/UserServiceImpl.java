package com.learn.platform.service.impl;

import com.learn.platform.po.User;
import com.learn.platform.service.UserService;
import com.learn.platform.service.dao.UserDao;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户业务处理service
 */
@Service
@DubboService
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    /**
     * 根据自增id获取用户信息
     *
     * @param id 自增id
     * @return 用户信息
     */
    @Override
    public User selectById(Integer id) {
        return userDao.selectById(id);
    }

    /**
     * 根据平台唯一标识获取用户信息
     *
     * @param platformId 平台唯一标识
     * @return User 用户信息
     */
    @Override
    public User selectByPlatformId(String platformId) {
        return userDao.selectByPlatformId(platformId);
    }

    /**
     * 根据指定条件获取用户
     *
     * @param user 置顶条件信息
     * @return 用户信息
     */
    @Override
    public User selectUserByUserInfo(User user) {
        return userDao.selectOne(user);
    }

    /**
     * 新增用户
     *
     * @param user 用户信息
     */
    @Override
    public void insert(User user) {
        userDao.insert(user);
    }
}
