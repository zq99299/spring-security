package cn.mrcode.imooc.springsecurity.sso.ssoserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/7 16:02
 * @date 2018/8/7 16:02
 * @since 1.0
 */

@Configuration
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
    }
}
