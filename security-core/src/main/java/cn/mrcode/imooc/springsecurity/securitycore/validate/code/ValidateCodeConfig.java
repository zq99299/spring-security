package cn.mrcode.imooc.springsecurity.securitycore.validate.code;

import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
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

    @Bean
    // spring 容器中如果存在imageCodeGenerate的bean就不会再初始化该bean了
    // 条件注解
    @ConditionalOnMissingBean/*(name = "imageCodeGenerate")*/
    public ValidateCodeGenerate imageCodeGenerate() {
        ImageCodeGenerate imageCodeGenerate = new ImageCodeGenerate(securityProperties.getCode().getImage());
        return imageCodeGenerate;
    }
}
