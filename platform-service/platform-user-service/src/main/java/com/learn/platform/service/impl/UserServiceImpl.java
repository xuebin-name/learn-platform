package com.learn.platform.service.impl;

import com.learn.platform.entity.dto.LoginUserDTO;
import com.learn.platform.entity.po.PlatformUser;
import com.learn.platform.service.user.UserService;
import com.learn.platform.service.dao.UserDao;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 用户业务处理service
 */
@Service
@DubboService
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 根据自增id获取用户信息
     *
     * @param id 自增id
     * @return 用户信息
     */
    @Override
    public PlatformUser selectById(Integer id) {
        return userDao.selectById(id);
    }

    /**
     * 根据平台唯一标识获取用户信息
     *
     * @param platformId 平台唯一标识
     * @return PlatformUser 用户信息
     */
    @Override
    public PlatformUser selectByPlatformId(String platformId) {
        return userDao.selectByPlatformId(platformId);
    }

    /**
     * 根据指定条件获取用户
     *
     * @param platformUser 置顶条件信息
     * @return 用户信息
     */
    @Override
    public PlatformUser selectUserByUserInfo(PlatformUser platformUser) {
        return userDao.selectOne(platformUser);
    }

    @Override
    public LoginUserDTO selectUserByUserName(String userName) {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        PlatformUser platformUser = userDao.selectUserByUserName(userName);
        BeanUtils.copyProperties(platformUser,loginUserDTO);


        return loginUserDTO;
    }

    /**
     * 新增用户
     *
     * @param platformUser 用户信息
     */
    @Override
    public void insert(PlatformUser platformUser) {
        platformUser.setPlatformId(UUID.randomUUID().toString());
        userDao.insert(platformUser);
    }

}
