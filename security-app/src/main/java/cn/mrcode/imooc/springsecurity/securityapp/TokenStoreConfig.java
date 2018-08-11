package cn.mrcode.imooc.springsecurity.securityapp;

import cn.mrcode.imooc.springsecurity.securityapp.jwt.ImoocJwtTokenEnhancer;
import cn.mrcode.imooc.springsecurity.securitycore.MyRedisTokenStore;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/11 15:56
 */
@Configuration
public class TokenStoreConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConditionalOnProperty(prefix = "imooc.security.oauth2", name = "tokenStore", havingValue = "redis")
    public TokenStore tokenStore() {
        return new MyRedisTokenStore(redisConnectionFactory);
    }

    @Configuration
    // matchIfMissing ：当tokenStore没有值的时候是否生效
    // 当tokenStore = jwt的时候或则tokenStore没有配置的时候使用下面的配置
    @ConditionalOnProperty(prefix = "imooc.security.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenConfig {
        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());  // 设置密钥
            return converter;
        }

        @Bean
        // 不能使用该条件注解，因为JwtAccessTokenConverter也是一个TokenEnhancer
//        @ConditionalOnMissingBean(TokenEnhancer.class)
        // 而 ConditionalOnBean 是必须存在一个TokenEnhancer的时候，才被创建
        // 先不纠结这个问题了。就这样吧。也就是封装程度的问题
        @ConditionalOnBean(TokenEnhancer.class)
        public TokenEnhancer jwtTokenEnhancer() {
            return new ImoocJwtTokenEnhancer();
        }
    }
}
