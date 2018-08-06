package cn.mrcode.imooc.springsecurity.securitycore.qq.config;

import cn.mrcode.imooc.springsecurity.securitycore.properties.QQProperties;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import cn.mrcode.imooc.springsecurity.securitycore.qq.connet.QQOAuth2ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;

/**
 * autoconfigure2.04中已经不存在social的自动配置类了
 * org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/6 9:20
 */
@Configuration
// 当配置了app-id的时候才启用
@ConditionalOnProperty(prefix = "imooc.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
                                       Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }

    public ConnectionFactory<?> createConnectionFactory() {
        QQProperties qq = securityProperties.getSocial().getQq();
        return new QQOAuth2ConnectionFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret());
    }
}
