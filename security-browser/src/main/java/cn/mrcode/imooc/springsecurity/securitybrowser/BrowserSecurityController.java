package cn.mrcode.imooc.springsecurity.securitybrowser;

import cn.mrcode.imooc.springsecurity.securitybrowser.support.SimpleResponse;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityConstants;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当需要身份认证的时候跳转到该类，进行分发处理
 * @author zhuqiang
 * @version 1.0.1 2018/8/3 14:58
 * @date 2018/8/3 14:58
 * @since 1.0
 */
@RestController
public class BrowserSecurityController {
    Logger logger = LoggerFactory.getLogger(getClass());
    // 工具类？
    private RequestCache requestCache = new HttpSessionRequestCache();

    // spring的跳转策略
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 当需要身份认证时跳转到这里
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requirAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转认证的请求：" + targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }
}
