package com.learn.platform.entity.message;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName PMessage
 * @Description TODO
 * @Author xue
 * @Date 2024/1/2 15:42
 */
@Data
public class PMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 2213918950589539326L;

    private Integer id;

    private String messageId;
    private String messageType;
    private Integer messageStatus;
    private String message;
    private String sendUserId;
    private String receiveUserId;
    private Date gmtCreate;
    private Date gmtUpdate;

}
