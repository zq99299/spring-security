package cn.mrcode.imooc.springsecurity.securitybrowser.logout;

import cn.mrcode.imooc.springsecurity.securitybrowser.support.SimpleResponse;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/7 0:18
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    private SecurityProperties securityProperties;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 当退出成功的时候，如果配置了一个页面，则跳转到页面，
        // 没有配置页面则打印session
        String signOutUrl = securityProperties.getBrowser().getSignOutUrl();
        if (StringUtils.isBlank(signOutUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        } else {
            response.sendRedirect(signOutUrl);
        }
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
