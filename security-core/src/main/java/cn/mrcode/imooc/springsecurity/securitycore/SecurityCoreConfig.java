package cn.mrcode.imooc.springsecurity.securitycore;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/3 15:33
 * @date 2018/8/3 15:33
 * @since 1.0
 */

import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 让SecurityProperties这个配置类生效
 * EnableConfigurationProperties 的作用是标明加载哪一个类
 * 这效果和直接在目标类上写上@Configuration效果一样
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
