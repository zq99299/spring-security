package cn.mrcode.imooc.springsecurity.securitycore.qq.connet;

import cn.mrcode.imooc.springsecurity.securitycore.qq.api.QQ;
import cn.mrcode.imooc.springsecurity.securitycore.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 服务提供商:
 * 官网地址可以获取 authorizeUrl 和 accessTokenUrl
 * http://wiki.connect.qq.com/%E5%BC%80%E5%8F%91%E6%94%BB%E7%95%A5_server-side
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/6 1:20
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
    public static final String authorizeUrl = "https://graph.qq.com/oauth2.0/authorize";
    public static final String accessTokenUrl = "https://graph.qq.com/oauth2.0/token";
    private String appId;

    /**
     * Create a new {@link OAuth2ServiceProvider}.
     */
    public QQServiceProvider(String appId, String secret) {
        // OAuth2Operations 有一个默认实现类，可以使用这个默认实现类
        // oauth2的一个流程服务
        super(new QQAuth2Template(appId, secret, authorizeUrl, accessTokenUrl));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
