package com.example.demo.web.config;

import com.example.demo.web.filter.TimeFilter;
import com.example.demo.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/2 14:50
 * @date 2018/8/2 14:50
 * @since 1.0
 */
@Configuration
// org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter 5.0+已过时
// 使用了jdk8 的接口默认方法
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private TimeInterceptor timeInterceptor;

    @Bean
    public FilterRegistrationBean timeFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TimeFilter());
        // 可以自定义拦截路径
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可以添加多个不同的拦截器
        registry.addInterceptor(timeInterceptor);
    }
}
