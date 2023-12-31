package com.learn.platform.service.dao;

import com.learn.platform.entity.po.PlatformUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    /**
     * 根据指定条件获取用户
     *
     * @param platformUser 置顶条件信息
     * @return 用户信息
     */
    PlatformUser selectOne(PlatformUser platformUser);

    /**
     * 根据用户名获取用户
     *
     * @param userName 用户名
     * @return PlatformUser
     */
    PlatformUser selectUserByUserName(String userName);

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    PlatformUser selectById(@Param("id") Integer id);

    /**
     * 根据平台唯一表示获取用户
     *
     * @param platformId
     * @return
     */
    PlatformUser selectByPlatformId(@Param("platformId") String platformId);

    /**
     * 新增用户
     *
     * @param platformUser 用户信息
     */
    void insert(PlatformUser platformUser);

    /**
     * 批量新增用户
     *
     * @param platformUserList 用户列表
     */
    void batchInsert(List<PlatformUser> platformUserList);

    /**
     * 更新用户信息
     *
     * @param platformUser 用户信息
     * @return 影响行数
     */
    int update(PlatformUser platformUser);


}
