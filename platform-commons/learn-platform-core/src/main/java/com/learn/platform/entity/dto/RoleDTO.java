package com.learn.platform.entity.dto;

import com.learn.platform.entity.po.Permission;
import com.learn.platform.entity.po.Role;
import lombok.*;

import java.io.Serial;
import java.util.List;

/**
 * @ClassName RoleDTO
 * @Description TODO
 * @Author xue
 * @Date 2024/1/5 14:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO extends Role {
    @Serial
    private static final long serialVersionUID = -1404051136281153459L;

    private List<Permission> permissions;
}
