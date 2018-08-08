package cn.mrcode.imooc.springsecurity.securitycore.validate.code.impl;

import cn.mrcode.imooc.springsecurity.securitycore.validate.code.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/4 15:33
 * @date 2018/8/4 15:33
 * @since 1.0
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {
    /** 由使用方注入具体实现 */
    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    /**
     * <pre>
     * 收集系统中所有 {@link ValidateCodeGenerator} 接口的实现
     * spring开发技巧-依赖查找：
     *  spring会查找所有ValidateCodeGenerate的实现
     *  beanName做为key，实现作为value注入这里
     * </pre>
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerates;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 图片和短信验证码的逻辑一致，提取成公共的
     * @param request
     */
    @Override
    public void validate(ServletWebRequest request) {
        // 拿到自己的类型
        ValidateCodeType type = getValidateCodeType();
        C codeInSession = (C) validateCodeRepository.get(request, type);
        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), type.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }
        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (codeInSession.isExpried()) {
            validateCodeRepository.remove(request, type);
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        validateCodeRepository.remove(request, type);
    }

    /**
     * 根据请求的url获取校验码的类型:
     * ValidateCodeProcessorHolder : 中持有所有本类的子类型，获取getClass能拿到具体的实例类名
     * @return
     * @see ValidateCodeProcessorHolder
     */
    private ValidateCodeType getValidateCodeType() {
        // 处理器 命名规则：ImageValidateCodeProcessor，拿到前缀即可
        String type = StringUtils.substringBefore(getClass().getSimpleName(), ValidateCodeProcessor.class.getSimpleName());
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    private C generate(ServletWebRequest request) throws Exception {
        String type = getValidateCodeType().toString().toLowerCase();
        // 可见这里类名的设计也很有技巧
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerates.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request.getRequest());
    }

    private void save(ServletWebRequest request, C validateCode) {
        // 不保存图片对象到redis session中，无法序列化
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        validateCodeRepository.save(request, code, getValidateCodeType());
    }

    public abstract void send(ServletWebRequest request, C validateCode) throws Exception;
}
