/**
 *
 */
package cn.mrcode.imooc.springsecurity.securitycore.properties;

/**
 * 没有默认值；由使用方注入
 * @author zhailiang
 */
public class QQProperties {
    /**
     * Application id.
     */
    private String appId;

    /**
     * Application secret.
     */
    private String appSecret;
    private String providerId = "qq";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

}
