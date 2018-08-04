package com.example.demo.code;

import cn.mrcode.imooc.springsecurity.securitycore.validate.code.ImageCode;
import cn.mrcode.imooc.springsecurity.securitycore.validate.code.ValidateCodeGenerate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/4 13:07
 * @date 2018/8/4 13:07
 * @since 1.0
 */
//@Component/*("imageCodeGenerate")*/
public class MyImageCodeGenerate implements ValidateCodeGenerate {
    @Override
    public ImageCode generate(HttpServletRequest request) throws IOException {
        System.out.println("自定义验证码生成");
        return null;
    }
}
