package com.learn.platform.service.dao;

import com.learn.platform.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    /**
     * 根据指定条件获取用户
     * @param user
     * @return
     */
    User selectOne(User user);

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    User selectById(@Param("id") Integer id);

    /**
     * 根据平台唯一表示获取用户
     * @param platformId
     * @return
     */
    User selectByPlatformId(@Param("platformId") String platformId);


}
