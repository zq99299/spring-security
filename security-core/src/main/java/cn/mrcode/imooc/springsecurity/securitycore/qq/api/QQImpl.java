package cn.mrcode.imooc.springsecurity.securitycore.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(getClass());
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
        logger.info(result);
        // "81F03E50B76D6D829F5A4875941567A6" 获取到的是这样的
        // 注意获取的时候要去掉两头的引号
        this.openid = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(URL_GET_USER_INFO, appId, openid);
        String result = getRestTemplate().getForObject(url, String.class);
        logger.info(result);
        QQUserInfo qqUserInfo = null;
        try {
            // 感觉这个 objectMapper真不好用啊，返回的json对象里面多了一个 constellation 字段，
            // userInfo对象里面没有这个字段也报错
            qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
            qqUserInfo.setOpenId(openid);
        } catch (IOException e) {
            throw new RuntimeException("获取QQ用户信息失败", e);
        }
        // 获得code，交换token，然后会调用该方法获取信息
        // 却返回了错误 {"ret":-22,"msg":"openid is invalid"}
        return qqUserInfo;
    }
}
