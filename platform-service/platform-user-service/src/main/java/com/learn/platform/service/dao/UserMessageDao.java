package com.learn.platform.service.dao;

import com.learn.platform.entity.message.PMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMessageDao {

    void saveUserMessage(PMessage pMessage);


}
