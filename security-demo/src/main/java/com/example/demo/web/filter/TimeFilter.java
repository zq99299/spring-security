package com.example.demo.web.filter;

import javax.servlet.*;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/2 14:42
 * @date 2018/8/2 14:42
 * @since 1.0
 */
//@Component  // 生效需要让spring容器接管
public class TimeFilter implements Filter {
    // 初始化
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("TimeFilter init");
    }

    // 执行
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Instant start = Instant.now();
        chain.doFilter(request, response);
        System.out.println("耗时：" + Duration.between(start, Instant.now()).toMillis());
    }

    // 销毁
    @Override
    public void destroy() {
        System.out.println("TimeFilter destroy");
    }
}