package cn.mrcode.imooc.springsecurity.securitybrowser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/3 9:16
 * @date 2018/8/3 9:16
 * @since 1.0
 */
// 自定义数据源来获取数据
// 这里只要是存在一个自定义的 UserDetailsService ，那么security将会使用该实例进行配置
@Component
public class MyUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(getClass());

    // 可以从任何地方获取数据
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查找用户信息
        logger.info("登录用户名", username);
        // 写死一个密码，赋予一个admin权限
        return new User(username, "{noop}123456",
                        AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
