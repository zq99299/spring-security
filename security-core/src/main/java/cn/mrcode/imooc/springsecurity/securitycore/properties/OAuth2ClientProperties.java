package cn.mrcode.imooc.springsecurity.securitycore.properties;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/11 15:20
 */
public class OAuth2ClientProperties {
    private String clientId;
    private String clientSecret;
    private String[] authorizedGrantTypes = {};
    private String[] redirectUris = {}; // 信任的回调域
    private String[] scopes = {};
    private int accessTokenValiditySeconds; // token有效期

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String[] getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String[] redirectUris) {
        this.redirectUris = redirectUris;
    }

    public String[] getScopes() {
        return scopes;
    }

    public void setScopes(String[] scopes) {
        this.scopes = scopes;
    }

    public int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public String[] getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String[] authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }
}
