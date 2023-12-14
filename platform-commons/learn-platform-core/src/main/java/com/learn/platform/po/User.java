package com.learn.platform.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.learn.platform.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = -3472015802900109834L;
    /**
     * id
     */

    private Integer id;
    /**
     * 平台唯一表示
     */
    private String platformId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String attr;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 个性签名
     */
    private String personalSignature;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别 0：女，1：男
     */
    private Integer sex;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS, timezone = "GMT+8")
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date gmtCreate;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS, timezone = "GMT+8")
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date gmtUpdate;

}
