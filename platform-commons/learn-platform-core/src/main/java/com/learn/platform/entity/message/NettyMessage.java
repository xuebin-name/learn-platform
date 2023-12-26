package com.learn.platform.entity.message;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @ClassName NettyMessage
 * @Description 消息实体
 * @Author xue
 * @Date 2023/12/26 15:15
 */
@Data
public class NettyMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 8976483484312866406L;

    /**
     *
     */
    private String messageId;

    /**
     *
     */
    private String sendUserId;

    /**
     *
     */
    private String receiveUserId;

    /**
     *
     */
    private String messageType;

    /**
     *
     */
    private String body;
}
