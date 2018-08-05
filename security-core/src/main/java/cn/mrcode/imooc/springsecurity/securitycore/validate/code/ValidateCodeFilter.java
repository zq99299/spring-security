package cn.mrcode.imooc.springsecurity.securitycore.validate.code;

import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityConstants;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 验证码验证过滤器：目前融合了短信和图形验证码的验证功能
 * OncePerRequestFilter spring提供的，保证在一个请求中只会被调用一次
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/3 23:24
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    // 应用方设置的，目前浏览器项目中有一个 MyAuthenticationFailureHandler
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * org.springframework.beans.factory.InitializingBean 保证在其他属性都设置完成后，有beanFactory调用
     * 但是在这里目前还是需要初始化处调用该方法
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        //为登录表单添加处理图片验证码处理器映射
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        // 为手机登录地址添加验证码验证的映射
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
    }

    /**
     * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
     * @param urlString
     * @param type
     */
    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType validateCodeType = getValidateCodeType(request);
        if (validateCodeType == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            validateCodeProcessorHolder.findValidateCodeProcessor(validateCodeType)
                    .validate(new ServletWebRequest(request, response));
        } catch (ValidateCodeException exception) {
            authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
            return;
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 获取请求地址对于的处理器，如果未找到则标识不需要验证
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                    break;
                }
            }
        }
        return result;
    }
}
