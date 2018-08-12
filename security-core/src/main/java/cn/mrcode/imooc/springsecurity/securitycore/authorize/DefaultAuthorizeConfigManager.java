package cn.mrcode.imooc.springsecurity.securitycore.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/12 21:21
 */
@Component
public class DefaultAuthorizeConfigManager implements AuthorizeConfigManager {
    @Autowired
    private Set<AuthorizeConfigProvider> providers;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizeConfigProvider provider : providers) {
            provider.config(config);
        }
        // 除了上面配置的，其他的都需要登录后才能访问
        config.anyRequest().authenticated();
    }
}
