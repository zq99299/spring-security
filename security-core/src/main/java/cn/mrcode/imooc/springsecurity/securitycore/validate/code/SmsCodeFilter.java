package cn.mrcode.imooc.springsecurity.securitycore.validate.code;

import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 图片验证码验证过滤器
 * OncePerRequestFilter spring提供的，保证在一个请求中只会被调用一次
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/3 23:24
 */
public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {
    // 在初始化本类的地方进行注入
    // 一般在配置security http的地方进行添加过滤器
    private AuthenticationFailureHandler failureHandler;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    // 由初始化的地方传递进来
    private SecurityProperties securityProperties;
    // 存储所有需要拦截的url
    private Set<String> urls;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * org.springframework.beans.factory.InitializingBean 保证在其他属性都设置完成后，有beanFactory调用
     * 但是在这里目前还是需要初始化处调用该方法
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String url = securityProperties.getCode().getImage().getUrl();
        String[] configUrl = StringUtils.split(url, ",");
        urls = Stream.of(configUrl).collect(Collectors.toSet());
        urls.add("/authentication/sms"); // 登录请求
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 为登录请求，并且为post请求
        boolean action = false;
        for (String url : urls) {
            // org.springframework.util.AntPathMatcher 能匹配spring中的url模式
            // 支持通配符路径那种
            if (pathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }
        if (action) {
            try {
                validate(request);
            } catch (ValidateCodeException e) {
                failureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) throws ServletRequestBindingException {
        String SESSION_KEY = ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS";
        // 拿到之前存储的imageCode信息
        ServletWebRequest swr = new ServletWebRequest(request);
        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(swr, SESSION_KEY);
        // 又是一个spring中的工具类，
        // 试问一下，如果不看源码怎么可能知道有这些工具类可用？
        String codeInRequest = ServletRequestUtils.getStringParameter(request, "smsCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }
        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(swr, SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(swr, SESSION_KEY);
    }

    public AuthenticationFailureHandler getFailureHandler() {
        return failureHandler;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
