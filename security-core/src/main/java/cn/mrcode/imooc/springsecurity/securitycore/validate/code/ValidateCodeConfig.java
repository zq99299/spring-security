package cn.mrcode.imooc.springsecurity.securitycore.validate.code;

import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import cn.mrcode.imooc.springsecurity.securitycore.validate.code.image.ImageValidateCodeGenerator;
import cn.mrcode.imooc.springsecurity.securitycore.validate.code.sms.DefaultSmsCodeSender;
import cn.mrcode.imooc.springsecurity.securitycore.validate.code.sms.SmsValidateCodeGenerator;
import cn.mrcode.imooc.springsecurity.securitycore.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/4 11:36
 * @date 2018/8/4 11:36
 * @since 1.0
 */
@Configuration
public class ValidateCodeConfig {
    @Autowired
    private SecurityProperties securityProperties;

    // spring 容器中如果存在imageCodeGenerate的bean就不会再初始化该bean了
    // 条件注解
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        ImageValidateCodeGenerator imageCodeGenerate = new ImageValidateCodeGenerator(securityProperties.getCode().getImage());
        return imageCodeGenerate;
    }

    // 这里由于产生了多个ValidateCodeGenerate的实现类
    // 所以需要使用name来区分
    // 在注入的时候也需要用其他手段与该name相同的id注入才可以
    // 当然还有其他的方式。可能可以使用：不同的子接口来分离短信和图形接口
    // 比如 @Qualifier("imageCodeGenerate") 或则什么的参数名和这个相同
    @Bean("smsValidateCodeGenerator")
    @ConditionalOnMissingBean(name = "smsValidateCodeGenerator")
    // 注意方法名称：如果没有指定bean则按方法名称作为beanName返回
    public ValidateCodeGenerator smsCodeGenerate() {
        SmsValidateCodeGenerator smsValidateCodeGenerator = new SmsValidateCodeGenerator(securityProperties.getCode().getSms());
        return smsValidateCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(DefaultSmsCodeSender.class)
    public SmsCodeSender defaultSmsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}
