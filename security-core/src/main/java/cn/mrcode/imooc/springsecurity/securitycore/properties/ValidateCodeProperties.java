package cn.mrcode.imooc.springsecurity.securitycore.properties;

/**
 * 用来封装验证码相关的配置
 * @author zhuqiang
 * @version 1.0.1 2018/8/4 10:06
 * @date 2018/8/4 10:06
 * @since 1.0
 */
public class ValidateCodeProperties {
    private ImageCodeProperties image = new ImageCodeProperties();
    // 后面还会新增短信验证码的配置

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
