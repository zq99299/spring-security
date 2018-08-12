package cn.mrcode.imooc.springsecurity.sso.ssoclient1;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@Order(99)
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // 除了首页其他的都要登录才能访问
                .mvcMatchers("/").permitAll()
                .anyRequest().authenticated();
    }
}
