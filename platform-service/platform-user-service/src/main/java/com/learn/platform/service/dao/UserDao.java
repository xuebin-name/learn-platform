package com.learn.platform.service.dao;

import com.learn.platform.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    /**
     * 根据指定条件获取用户
     *
     * @param user 置顶条件信息
     * @return 用户信息
     */
    User selectOne(User user);

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    User selectById(@Param("id") Integer id);

    /**
     * 根据平台唯一表示获取用户
     *
     * @param platformId
     * @return
     */
    User selectByPlatformId(@Param("platformId") String platformId);

    /**
     * 新增用户
     *
     * @param user 用户信息
     */
    void insert(User user);

    /**
     * 批量新增用户
     *
     * @param userList 用户列表
     */
    void batchInsert(List<User> userList);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 影响行数
     */
    int update(User user);


}
