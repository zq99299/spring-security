package cn.mrcode.imooc.springsecurity.securitycore.authentication.mobile;

import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信验证码验证： 直接仿照UsernamePasswordAuthenticationFilter
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/5 9:29
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    // ~ Static fields/initializers
    // =====================================================================================

    private String mobileParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
    private boolean postOnly = true;

    // ~ Constructors
    // ===================================================================================================

    public SmsCodeAuthenticationFilter() {
        // 拦截该路径，如果是访问该路径，则标识是需要短信登录
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, "POST"));
    }

    // ~ Methods
    // ========================================================================================================

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);

        if (mobile == null) {
            mobile = "";
        }

        mobile = mobile.trim();

        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

        // Allow subclasses to set the "details" property
        // 把request里面的一些信息copy近token里面
        // 后面认证成功的时候还需要copy这信息到新的token
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    protected void setDetails(HttpServletRequest request,
                              SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }
}
