package cn.mrcode.imooc.springsecurity.securitycore.validate.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 图形验证码
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/3 22:44
 */
public class ImageCode {
    private BufferedImage image;
    private String code;
    private LocalDateTime expireTime; // 过期时间

    /**
     * @param image
     * @param code
     * @param expireIn 过期时间，单位秒
     */
    public ImageCode(BufferedImage image, String code, int expireIn) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpried() {
        return this.expireTime.isBefore(LocalDateTime.now());
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
