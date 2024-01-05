package com.learn.platform.entity.dto;

import com.learn.platform.entity.po.PlatformUser;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName LoginUserDTO
 * @Description 登录用户dto
 * @Author xue
 * @Date 2024/1/5 14:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO extends PlatformUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 6929944296780841670L;

    List<RoleDTO> roles;
}
