package cn.mrcode.imooc.springsecurity.securityapp;

import cn.mrcode.imooc.springsecurity.securityapp.social.AppSignUpUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
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

    @Autowired
    private AppSignUpUtils appSignUpUtils;

    @GetMapping(value = "/social/signUp")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ConnectionData signUp(HttpServletRequest request) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        // 这里还不能直接放 Connection 因为这个里面包含了很多对象
        ConnectionData connectionData = connection.createData();
        logger.info(ReflectionToStringBuilder.toString(connection, ToStringStyle.JSON_STYLE));
        appSignUpUtils.saveConnection(new ServletWebRequest(request), connectionData);
        // 注意：如果真的在客户端无session的情况下，这里是复发获取到providerSignInUtils中的用户信息的
        // 因为302重定向，是客户端重新发起请求，如果没有cookie的情况下，就不会有相同的session
        // 教程中这里应该是一个bug
        // 为了进度问题，先默认可以获取到
        // 最后要调用这一步：providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
        // 那么在demo注册控制器中这一步之前，就要把这里需要的信息获取到
        // 跟中该方法的源码，转换成使用redis存储
        return connectionData;
    }
}
