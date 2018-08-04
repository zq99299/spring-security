package cn.mrcode.imooc.springsecurity.securitycore.properties;

/**
 * 图形验证码
 * @author zhuqiang
 * @version 1.0.1 2018/8/4 10:03
 * @date 2018/8/4 10:03
 * @since 1.0
 */
public class ImageCodeProperties extends SmsCodeProperties {
    private int width = 67;
    private int height = 23;

    public ImageCodeProperties() {
        setLength(4);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
