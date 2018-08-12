package cn.mrcode.imooc.springsecurity.securitycore.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 自定义权限控制接口
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/12 21:09
 */
public interface AuthorizeConfigProvider {
    /**
     * @param config
     * @see HttpSecurity#authorizeRequests()
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
