package cn.mrcode.imooc.springsecurity.securitycore.validate.code.image;

import cn.mrcode.imooc.springsecurity.securitycore.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图形验证码
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/3 22:44
 */
public class ImageCode extends ValidateCode {
    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }


    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
