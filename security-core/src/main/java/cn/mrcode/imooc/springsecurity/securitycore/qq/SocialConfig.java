/**
 *
 */
package cn.mrcode.imooc.springsecurity.securitycore.qq;

import cn.mrcode.imooc.springsecurity.securitycore.qq.config.QQAutoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author zhailiang
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        repository.setTablePrefix("imooc_");
        return repository;
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public SpringSocialConfigurer imoocSocialSecurityConfig() {
        // 默认配置类，进行组件的组装
        // 包括了过滤器SocialAuthenticationFilter 添加到security过滤链中
        SpringSocialConfigurer springSocialConfigurer = new SpringSocialConfigurer();
        return springSocialConfigurer;
    }

    //https://docs.spring.io/spring-social/docs/1.1.x-SNAPSHOT/reference/htmlsingle/#creating-connections-with-connectcontroller
    // 必须要添加一个处理器
//    @Bean
//    public ConnectController connectController(
//            ConnectionFactoryLocator connectionFactoryLocator,
//            ConnectionRepository connectionRepository) {
//        return new ConnectController(connectionFactoryLocator, connectionRepository);
//    }
}
