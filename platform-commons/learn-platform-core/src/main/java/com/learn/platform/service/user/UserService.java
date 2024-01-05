package com.learn.platform.service.user;


import com.learn.platform.entity.dto.LoginUserDTO;
import com.learn.platform.entity.po.PlatformUser;

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
    PlatformUser selectById(Integer id);

    /**
     * 根据平台唯一标识获取用户信息
     *
     * @param platformId 平台唯一标识
     * @return PlatformUser 用户信息
     */
    PlatformUser selectByPlatformId(String platformId);

    /**
     * 根据指定条件获取用户
     *
     * @param platformUser 置顶条件信息
     * @return 用户信息
     */
    PlatformUser selectUserByUserInfo(PlatformUser platformUser);

    LoginUserDTO selectUserByUserName(String userName);

    /**
     * 新增用户
     *
     * @param platformUser 用户信息
     */
    void insert(PlatformUser platformUser);
}
