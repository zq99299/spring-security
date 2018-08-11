package cn.mrcode.imooc.springsecurity.securityapp;

import cn.mrcode.imooc.springsecurity.securitycore.MyRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

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
    public TokenStore tokenStore() {
        return new MyRedisTokenStore(redisConnectionFactory);
    }
}
