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

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
