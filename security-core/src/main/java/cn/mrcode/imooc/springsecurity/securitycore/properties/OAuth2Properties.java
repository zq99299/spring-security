package cn.mrcode.imooc.springsecurity.securitycore.properties;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/11 15:23
 */
public class OAuth2Properties {
    private OAuth2ClientProperties[] clients = {};

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }
}
