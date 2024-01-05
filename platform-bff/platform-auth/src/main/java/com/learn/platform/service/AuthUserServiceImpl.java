package com.learn.platform.service;

import com.learn.platform.entity.dto.LoginUserDTO;
import com.learn.platform.entity.dto.RoleDTO;
import com.learn.platform.service.user.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName AuthUserServiceImpl
 * @Description 鉴权用户处理业务service
 * @Author xue
 * @Date 2024/1/5 11:36
 */
@Service
public class AuthUserServiceImpl implements UserDetailsService {
    @DubboReference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LoginUserDTO loginUserDTO = userService.selectUserByUserName(userName);
        new org.springframework.security.core.userdetails.User(loginUserDTO.getUserName()
                , loginUserDTO.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(loginUserDTO.getRoles()) );
        return null;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Collection<RoleDTO> roles){
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        for (RoleDTO role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPermissions().stream()
                    .map(p -> new SimpleGrantedAuthority(p.getName()))
                    .forEach(authorities::add);
        }

        return authorities;
    }
}
