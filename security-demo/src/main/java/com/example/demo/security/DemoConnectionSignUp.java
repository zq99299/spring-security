package com.example.demo.security;

import cn.mrcode.imooc.springsecurity.securitycore.social.SpringSocialConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * 第三方登录，默认注册用户
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/6 20:04
 * @see SpringSocialConfig#connectionSignUp  该对象存在则会在该地方被使用
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String execute(Connection<?> connection) {
        logger.info("根据社交用户信息默认创建用户并返回用户唯一标识");
        return connection.getDisplayName();
    }
}
