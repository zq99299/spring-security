package cn.mrcode.imooc.springsecurity.securitybrowser;

import cn.mrcode.imooc.springsecurity.securitycore.properties.LoginType;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * .formLogin().successHandler() 中需要的处理器类型
 * @author zhuqiang
 * @version 1.0.1 2018/8/3 16:29
 * @date 2018/8/3 16:29
 * @since 1.0
 */
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    //  com.fasterxml.jackson.databind.
    // spring 是使用jackson来进行处理返回数据的
    // 所以这里可以得到他的实例
    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @param request
     * @param response
     * @param authentication 封装了所有的认证信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");
        if (securityProperties.getBrowser().getLoginType() == LoginType.JSON) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            // 把本类实现父类改成 AuthenticationSuccessHandler 的子类 SavedRequestAwareAuthenticationSuccessHandler
            // 之前说spring默认成功是跳转到登录前的url地址
            // 就是使用的这个类来处理的
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
