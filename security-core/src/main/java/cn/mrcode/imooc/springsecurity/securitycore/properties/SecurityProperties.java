package cn.mrcode.imooc.springsecurity.securitycore.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/3 15:28
 * @date 2018/8/3 15:28
 * @since 1.0
 */
@ConfigurationProperties(prefix = "imooc.security")
public class SecurityProperties {
    /** imooc.security.browser 路径下的配置会被映射到该配置类中 */
    private BrowserProperties browser = new BrowserProperties();
    private ValidateCodeProperties code = new ValidateCodeProperties();
    private SocialProperties social = new SocialProperties();
    private OAuth2Properties oauth2 = new OAuth2Properties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }

    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2Properties oauth2) {
        this.oauth2 = oauth2;
    }
}
