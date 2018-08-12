package cn.mrcode.imooc.springsecurity.securitycore.authorize;

import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityConstants;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * app和browser通用静态权限配置
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/12 21:12
 */
@Component
@Order(Integer.MIN_VALUE)
public class CommonAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPEN_ID,
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                securityProperties.getBrowser().getLoginPage(),
                securityProperties.getBrowser().getSignUpUrl(),
                securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".json",
                securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".html"
        ).permitAll();
        // 退出成功处理，没有默认值，所以需要判定下
        String signOutUrl = securityProperties.getBrowser().getSignOutUrl();
        if (signOutUrl != null) {
            config.antMatchers(signOutUrl).permitAll();
        }
    }
}
