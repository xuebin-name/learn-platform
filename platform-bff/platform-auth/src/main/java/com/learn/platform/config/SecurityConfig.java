package com.learn.platform.config;

import com.learn.platform.service.AuthEntryPointImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

/**
 * @ClassName SecurityConfig
 * @Description Security 6.0配置类
 * @Author xue
 * @Date 2023/12/22 17:47
 */
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthEntryPointImpl authEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //配置web授权访问,"/login","/register","/upLogin"这些统统无需权限
       /* httpSecurity.authorizeHttpRequests()
                //访问存放路径，允许全部访问
                .requestMatchers("/login","/register","/upLogin").permitAll()
                //成功的链接,也就是后续访问的链接放在这个里,我这里是成功页需要USER权限才能访问
                .requestMatchers("/success").hasRole("USER").and()
                *//**
                 * 自定义登陆页面是loginSelf.html页(这里看你模板引擎怎么配),表单提交的处理链接是"/upLogin"
                 * 就是<form action="/upLogin"></form>这样,这个"/upLogin"不需要自己处理,就自己决定
                 * usernameParameter和passwordParameter,就是表单提交所带的参数,
                 * 这里我是"username"和"password";
                 *//*
                .formLogin().loginPage("/loginSelf").loginProcessingUrl("/upLogin")
                .usernameParameter("username").passwordParameter("password");*/
                //httpSecurity.authorizeHttpRequests(authorize->{authorize.anyRequest().permitAll();}).oauth2Login();
                httpSecurity.authorizeHttpRequests()
                        .anyRequest().authenticated()
                        .and().exceptionHandling().authenticationEntryPoint(authEntryPoint)
                        .and().oauth2Login().permitAll().and().csrf().disable();
        return httpSecurity.build();
    }

    public AuthenticationProvider authenticationProvider(){
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    protected AuthenticationManager authenticationManager(){
        return new ProviderManager(Arrays.asList(authenticationProvider()));
    }

    @Bean
    protected SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    /**
     * 设置密码加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
