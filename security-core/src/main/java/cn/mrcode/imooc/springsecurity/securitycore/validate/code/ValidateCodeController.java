package cn.mrcode.imooc.springsecurity.securitycore.validate.code;

import cn.mrcode.imooc.springsecurity.securitycore.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码服务
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/3 22:48
 */
@RestController
public class ValidateCodeController {
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeGenerate validateCodeGenerate;

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = validateCodeGenerate.generate(request);
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }
}
