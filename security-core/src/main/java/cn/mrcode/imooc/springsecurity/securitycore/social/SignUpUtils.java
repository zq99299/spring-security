package cn.mrcode.imooc.springsecurity.securitycore.social;

import org.springframework.social.connect.ConnectionData;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 用户信息获取
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/12 17:14
 */
public interface SignUpUtils {
    void saveConnection(ServletWebRequest request, ConnectionData connectionData);

    void doPostSignUp(String userId, ServletWebRequest request);
}
