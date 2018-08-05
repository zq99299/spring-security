package cn.mrcode.imooc.springsecurity.securitycore.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * 构建与qq交互的api实例;完成的是第6步
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/6 0:46
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
    // http://wiki.connect.qq.com/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7openid_oauth2-0
    public final static String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    // 父类会自动携带accessToken
    public final static String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;
    private String openid;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        // 该语句代码查看继承类的源码得知
        // 默认是把accessToken放入请求头中
        // qqapi的文档确是放在参数中传递的
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPENID, accessToken);
        //callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
        String result = getRestTemplate().getForObject(url, String.class);
        System.out.println(result);
        this.openid = StringUtils.substringBetween("\"openid\":", "}");
    }

    @Override
    public QQUserInfo getUserInfo() throws IOException {
        String url = String.format(URL_GET_USER_INFO, appId, openid);
        String result = getRestTemplate().getForObject(url, String.class);
        System.out.println(result);
        QQUserInfo qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
        return qqUserInfo;
    }
}
