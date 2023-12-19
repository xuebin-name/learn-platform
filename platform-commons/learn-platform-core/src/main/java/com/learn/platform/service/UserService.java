package com.learn.platform.service;


import com.learn.platform.entity.po.User;

/**
 * 用户业务接口声明
 */
public interface UserService {
    /**
     * 根据自增id获取用户信息
     *
     * @param id 自增id
     * @return 用户信息
     */
    User selectById(Integer id);

    /**
     * 根据平台唯一标识获取用户信息
     *
     * @param platformId 平台唯一标识
     * @return User 用户信息
     */
    User selectByPlatformId(String platformId);

    /**
     * 根据指定条件获取用户
     *
     * @param user 置顶条件信息
     * @return 用户信息
     */
    User selectUserByUserInfo(User user);

    /**
     * 新增用户
     *
     * @param user 用户信息
     */
    void insert(User user);
}
