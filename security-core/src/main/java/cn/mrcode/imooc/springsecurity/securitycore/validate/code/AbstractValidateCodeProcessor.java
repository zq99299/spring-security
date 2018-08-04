package cn.mrcode.imooc.springsecurity.securitycore.validate.code;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/4 15:33
 * @date 2018/8/4 15:33
 * @since 1.0
 */
public abstract class AbstractValidateCodeProcessor implements ValidateCodeProcessor {
    /** 操作session的工具类 */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * <pre>
     * 收集系统中所有 {@link ValidateCodeGenerate} 接口的实现
     * spring开发技巧-依赖查找：
     *  spring会查找所有ValidateCodeGenerate的实现
     *  beanName做为key，实现作为value注入这里
     * </pre>
     */
    @Autowired
    private Map<String, ValidateCodeGenerate> validateCodeGenerates;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        ValidateCode validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    private ValidateCode generate(ServletWebRequest request) throws Exception {
        String type = getProcessorType(request);
        ValidateCodeGenerate codeGenerate = validateCodeGenerates.get(type + "CodeGenerate");
        return codeGenerate.generate(request.getRequest());
    }

    private void save(ServletWebRequest request, ValidateCode validateCode) {
        sessionStrategy.setAttribute(request, SESSION_KEY_PREFIX + getProcessorType(request).toUpperCase(), validateCode);
    }

    public static String getProcessorType(ServletWebRequest request) {
        // /code/sms
        // /code/image
        // 该工具类获取后面的
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }

    public abstract void send(ServletWebRequest request, ValidateCode validateCode) throws Exception;
}
