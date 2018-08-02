package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义注解
 * @author zhuqiang
 * @version 1.0.1 2018/8/2 12:32
 * @date 2018/8/2 12:32
 * @since 1.0
 */
// 绑定一个注解类型，用于验证的值是什么
// 如果Object换成String，那么则只能把注解放在String类型上
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {
    // 实现ConstraintValidator接口后，会被spring扫描管理，所以可以直接使用注入服务
    // 在idea 2017.3.2 版本中，可以看到该行代码前面有被管理到的一个标志图标
//    @Autowired
//    private UserController userController;

    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        System.out.println("MyConstraintValidator初始化");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 校验逻辑
        System.out.println(value + " : " + context);
        return true;  // 返回false 表示验证不通过
    }
}
