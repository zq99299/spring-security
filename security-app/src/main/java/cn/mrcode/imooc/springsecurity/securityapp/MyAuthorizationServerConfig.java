package cn.mrcode.imooc.springsecurity.securityapp;

import cn.mrcode.imooc.springsecurity.securitycore.properties.OAuth2ClientProperties;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Arrays;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/7 10:52
 * @date 2018/8/7 10:52
 * @since 1.0
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final AuthenticationManager authenticationManager;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    public TokenStore tokenStore;

    public MyAuthorizationServerConfig(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder inMemory = clients.inMemory();
        OAuth2ClientProperties[] clientsInCustom = securityProperties.getOauth2().getClients();
        for (OAuth2ClientProperties p : clientsInCustom) {
            inMemory.withClient(p.getClientId())
                    .secret(p.getClientSecret())
                    .redirectUris(p.getRedirectUris())
                    .authorizedGrantTypes(p.getAuthorizedGrantTypes())
                    .accessTokenValiditySeconds(p.getAccessTokenValiditySeconds())
                    .scopes(p.getScopes());
        }
        logger.info(Arrays.toString(clientsInCustom));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager);
        endpoints.tokenStore(tokenStore);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 这里使用什么密码需要 根据上面配置client信息里面的密码类型决定
        // 目前上面配置的是无加密的密码
        security.passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
