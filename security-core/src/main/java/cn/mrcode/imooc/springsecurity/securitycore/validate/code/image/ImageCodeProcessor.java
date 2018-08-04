package cn.mrcode.imooc.springsecurity.securitycore.validate.code.image;

import cn.mrcode.imooc.springsecurity.securitycore.validate.code.AbstractValidateCodeProcessor;
import cn.mrcode.imooc.springsecurity.securitycore.validate.code.ValidateCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

/**
 * 图像处理
 */
@Component("imageCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor {
    @Override
    public void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        ImageCode imageCode = (ImageCode) validateCode;
        HttpServletResponse response = request.getResponse();
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }
}
