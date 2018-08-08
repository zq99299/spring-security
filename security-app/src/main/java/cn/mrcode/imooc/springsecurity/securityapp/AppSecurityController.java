package cn.mrcode.imooc.springsecurity.securityapp;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理登录的控制器
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/8 23:56
 */
@RestController
public class AppSecurityController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @GetMapping(value = "/social/signUp")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Connection signUp(HttpServletRequest request) {
        Connection<?> connectionFromSession = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        logger.info(ReflectionToStringBuilder.toString(connectionFromSession, ToStringStyle.JSON_STYLE));
        return connectionFromSession;
    }
}
