package com.example.demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 个性化的存储，应该在使用方控制
 * @author zhuqiang
 * @version 1.0.1 2018/8/3 9:16
 * @date 2018/8/3 9:16
 * @since 1.0
 */
// 自定义数据源来获取数据
// 这里只要是存在一个自定义的 UserDetailsService ，那么security将会使用该实例进行配置
@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 可以从任何地方获取数据
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查找用户信息
        logger.info("登录用户名:{}", username);
        // 写死一个密码，赋予一个admin权限
//        User admin = new User(username, "{noop}123456",
//                              AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return getUserDetails(username);
    }


    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("登录用户名:{}", userId);
        return getUserDetails(userId);
    }

    private SocialUser getUserDetails(String username) {
        String password = passwordEncoder.encode("123456");
        logger.info("数据库密码{}", password);
        SocialUser admin = new SocialUser(username,
//                              "{noop}123456",
                                          password,
                                          true, true, true, true,
                                          AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        return admin;
    }
}
