package com.example.demo.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/2 15:58
 * @date 2018/8/2 15:58
 * @since 1.0
 */
@Component
@Aspect
public class TimeAspect {
    // 环绕通知 还有其他类型的注解
    // 这里的表达式在官网可以学习怎么使用
    // https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop
    @Around("execution(* com.example.demo.web.controller.UserController.*(..))")
    public Object doAccessCheck(ProceedingJoinPoint point) throws Throwable {
        Instant start = Instant.now();
        Object proceed = point.proceed();  // 类似于调用过滤器链一样
        System.out.println("耗时：" + Duration.between(start, Instant.now()).toMillis());
        Object[] args = point.getArgs();
        for (Object arg : args) {
            System.out.println(arg);
        }
        return proceed;
    }
}
