package com.example.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

/**
 * <pre>
 * 自定义注解8
 * </pre>
 * @author zhuqiang
 * @version 1.0.0
 * @date 2018/8/2 12:33
 * @since 1.0.0
 */
@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)  // 注释信息保留在运行时
@Constraint(validatedBy = MyConstraintValidator.class)  // 用于什么类来校验这个注解，也就是有该注解的时候执行什么逻辑
public @interface MyConstraint {
    // 下面三个属性是必须的，要使用hibernate.validator这个引擎来使用的话
    // 具体的可以去深入
    // 这里可以随意仿照一个 之前用到的注解中的源码，就能看到都有下面三个属性
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
