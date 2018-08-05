package cn.mrcode.imooc.springsecurity.securitybrowser;

import cn.mrcode.imooc.springsecurity.securitycore.authentication.AbstractChannelSecurityConfig;
import cn.mrcode.imooc.springsecurity.securitycore.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityConstants;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import cn.mrcode.imooc.springsecurity.securitycore.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/3 0:05
 */

// WebSecurityConfigurerAdapter 适配器类。专门用来做web应用的安全配置
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 数据源是需要在使用处配置数据源的信息
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;
    // 之前已经写好的 MyUserDetailsService
    @Autowired
    private UserDetailsService userDetailsService;

    // 由下面的  .apply(smsCodeAuthenticationSecurityConfigs)方法添加这个配置
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfigs;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        // org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer.tokenRepository
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 该对象里面有定义创建表的语句
        // 可以设置让该类来创建表
        // 但是该功能只用使用一次，如果数据库已经存在表则会报错
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    // 有三个configure的方法，这里使用http参数的
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 最简单的修改默认配置的方法
        // 在v5+中，该配置（表单登录）应该是默认配置了
        // basic登录（也就是弹框登录的）应该是v5-的版本默认

        applyPasswordAuthenticationConfig(http);
        http
                .apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfigs)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository)
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                // 对请求授权配置：注意方法名的含义，能联想到一些
                .authorizeRequests()
                // 放行这个路径
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*", // 图形验证码接口
                        // org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController
                        // BasicErrorController 类提供的默认错误信息处理服务
                        "/error"
                )
                .permitAll()
                .anyRequest()
                // 对任意请求都必须是已认证才能访问
                .authenticated()
                .and()
                .csrf()
                .disable()
        ;
    }
}
