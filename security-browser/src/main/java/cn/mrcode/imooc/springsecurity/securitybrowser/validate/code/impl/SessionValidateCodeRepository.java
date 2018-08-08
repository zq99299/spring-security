package cn.mrcode.imooc.springsecurity.securitybrowser.validate.code.impl;

import cn.mrcode.imooc.springsecurity.securitycore.validate.code.ValidateCode;
import cn.mrcode.imooc.springsecurity.securitycore.validate.code.ValidateCodeRepository;
import cn.mrcode.imooc.springsecurity.securitycore.validate.code.ValidateCodeType;
import cn.mrcode.imooc.springsecurity.securitycore.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/8 14:06
 * @date 2018/8/8 14:06
 * @since 1.0
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {
    /** 操作session的工具类 */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    /** 验证码放入session的时候前缀 */
    public final static String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE";

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        sessionStrategy.setAttribute(request, getSessionKey(validateCodeType), code);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String sessionKey = getSessionKey(validateCodeType);
        // 拿到创建 create() 存储到session的code验证码对象
        return (ValidateCode) sessionStrategy.getAttribute(request, sessionKey);
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        sessionStrategy.removeAttribute(request, getSessionKey(validateCodeType));
    }

    /**
     * 构建验证码放入session时的key; 在保存的时候也使用该key
     * {@link AbstractValidateCodeProcessor#save(org.springframework.web.context.request.ServletWebRequest, cn.mrcode.imooc.springsecurity.securitycore.validate.code.ValidateCode)}
     * @param validateCodeType
     * @return
     */
    private String getSessionKey(ValidateCodeType validateCodeType) {
        return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }
}
