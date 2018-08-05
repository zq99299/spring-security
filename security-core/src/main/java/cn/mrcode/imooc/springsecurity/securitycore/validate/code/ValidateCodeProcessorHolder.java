package cn.mrcode.imooc.springsecurity.securitycore.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 处理器持有者，用来管理所有验证码类型的处理器
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/5 20:40
 */
@Component
public class ValidateCodeProcessorHolder {
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String beanName = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor processor = validateCodeProcessors.get(beanName);
        if (processor == null) {
            throw new ValidateCodeException("验证码处理器 " + beanName + " 不存在");
        }
        return processor;
    }
}
