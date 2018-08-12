package cn.mrcode.imooc.springsecurity.sso.ssoserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/12 14:16
 */
@Component
public class SsoUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = passwordEncoder.encode("123456");
        logger.info("用户名 {}，数据库密码{}", username, password);
        User admin = new User(username,
//                              "{noop}123456",
                password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        return admin;
    }
}
