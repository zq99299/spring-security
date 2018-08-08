package cn.mrcode.imooc.springsecurity.securitycore.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验验证码处理器，封装不同校验码的处理逻辑
 * @version 1.0.1 2018/8/4 15:30
 * @date 2018/8/4 15:30
 */
public interface ValidateCodeProcessor {
    /**
     * 创建校验码：创建、存入session、发送
     * org.springframework.web.context.request.ServletWebRequest 工具类可以存放request和response
     * @param request
     */
    void create(ServletWebRequest request) throws Exception;

    void validate(ServletWebRequest request);
}
