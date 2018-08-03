package cn.mrcode.imooc.springsecurity.securitycore.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 图片验证服务异常
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/3 23:30
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
