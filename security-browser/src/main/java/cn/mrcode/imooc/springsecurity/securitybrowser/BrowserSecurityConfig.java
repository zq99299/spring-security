package cn.mrcode.imooc.springsecurity.securitybrowser;

import cn.mrcode.imooc.springsecurity.securitycore.authentication.AbstractChannelSecurityConfig;
import cn.mrcode.imooc.springsecurity.securitycore.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityConstants;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SessionProperties;
import cn.mrcode.imooc.springsecurity.securitycore.social.SpringSocialConfig;
import cn.mrcode.imooc.springsecurity.securitycore.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

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
    /**
     * @see SpringSocialConfig#imoocSocialSecurityConfig()
     */
    @Autowired
    private SpringSocialConfigurer imoocSocialSecurityConfig;

    /**
     * @see BrowserSecurityBeanConfig
     */
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;
    /**
     * @see BrowserSecurityBeanConfig
     */
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

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
        SessionProperties session = securityProperties.getBrowser().getSession();
        http
                .apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfigs)
                .and()
                .apply(imoocSocialSecurityConfig)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository)
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(session.getMaximumSessions()) //限制同一个用户只能有一个session登录
                .maxSessionsPreventsLogin(session.isMaxSessionsPreventsLogin())  // 当session达到最大后，阻止后登录的行为
                .expiredSessionStrategy(sessionInformationExpiredStrategy)  // 失效后的策略。定制型更高，失效前的请求还能拿到
                .and()
                .and()
                .logout()
//                .logoutUrl("/singout")  // 退出请求路径
                // 与logoutSuccessUrl互斥，有handler则logoutSuccessUrl失效
                // 通过处理器增加配置了页面则跳转到页面，没有则输出json
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .and()
                // 对请求授权配置：注意方法名的含义，能联想到一些
                .authorizeRequests()
                // 放行这个路径
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*", // 图形验证码接口
                        securityProperties.getBrowser().getSignUpUrl(),  // 注册页面
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".json",
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".html",
                        "/user/regist", // 注册请求，后面会介绍怎么把这个只有使用方知道放行的配置剥离处理
                        // org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController
                        // BasicErrorController 类提供的默认错误信息处理服务
                        "/error",
                        "/connect/*",
                        "/auth/*",
                        "/signin"
                )
                .permitAll()
                // 该路径，只允许有 ADMIN 角色的人访问
                .antMatchers(HttpMethod.GET, "/user/*").hasRole("ADMIN")
                .anyRequest()
                // 对任意请求都必须是已认证才能访问
                .authenticated()
                .and()
                .csrf()
                .disable()
        ;
    }
}
