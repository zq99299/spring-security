package cn.mrcode.imooc.springsecurity.securitycore.properties;

/**
 * 图形验证码
 * @author zhuqiang
 * @version 1.0.1 2018/8/4 10:03
 * @date 2018/8/4 10:03
 * @since 1.0
 */
public class SmsCodeProperties {
    private int length = 6;  // 验证码长度
    private int expireIn = 60;  // 过期时间
    private String url;  // 要验证的接口url路径，逗号隔开

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
