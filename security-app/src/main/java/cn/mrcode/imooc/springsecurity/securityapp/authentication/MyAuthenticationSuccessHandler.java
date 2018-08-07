package cn.mrcode.imooc.springsecurity.securitycore.authentication;

import cn.mrcode.imooc.springsecurity.securitycore.properties.LoginType;
import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.config.annotation.configuration.ClientDetailsServiceConfiguration;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

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
     * 授权服务器：自动配置的
     * @see ClientDetailsServiceConfiguration#clientDetailsService()
     */
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

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
        /**
         * @see BasicAuthenticationFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
         *  */
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Basic ")) {
            // 不被认可的客户端异常
            throw new UnapprovedClientAuthenticationException("没有Authorization请求头");
        }

        // 解析请Authorization 获取client信息
        // client-id: myid
        // client-secret: myid
        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;
        String clientId = tokens[0];
        String clientSecret = tokens[1];
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        // 判定提交的是否与查询的匹配

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
        }

        /**  @see DefaultOAuth2RequestFactory#createTokenRequest(java.util.Map, org.springframework.security.oauth2.provider.ClientDetails)
         * requestParameters,不同的授权模式有不同的参数，这里自定义的模式，没有参数
         * String clientId,
         * Collection<String> scope, 给自己的前段使用，默认用所有的即可
         * String grantType 自定义
         *
         * 在这里我就有一个疑问了：这个token应该代表的是不同的用户，这里使用我们配置的同一个client？那么获取到的不就是相同的token？
         * 难道说是根据用户名和密码创建的？以后明白了再来填坑
         * */

        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_SORTED_MAP, clientId, clientDetails.getScope(), "costom");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        /**
         * @see org.springframework.security.oauth2.provider.token.AbstractTokenGranter#getOAuth2Authentication(org.springframework.security.oauth2.provider.ClientDetails, org.springframework.security.oauth2.provider.TokenRequest)
         * */
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(accessToken));
    }

    /**
     * Decodes the header into a username and password.
     * @throws BadCredentialsException if the Basic header is not present or is not valid
     *                                 Base64
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }
}