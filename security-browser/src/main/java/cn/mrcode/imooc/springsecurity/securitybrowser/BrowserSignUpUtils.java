package cn.mrcode.imooc.springsecurity.securitybrowser;

import cn.mrcode.imooc.springsecurity.securitycore.social.SignUpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/12 17:16
 */
@Component
public class BrowserSignUpUtils implements SignUpUtils {
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Override
    public void saveConnection(ServletWebRequest request, ConnectionData connectionData) {
        // 浏览器环境下不用处理
    }

    @Override
    public void doPostSignUp(String userId, ServletWebRequest request) {
        providerSignInUtils.doPostSignUp(userId, request);
    }
}
