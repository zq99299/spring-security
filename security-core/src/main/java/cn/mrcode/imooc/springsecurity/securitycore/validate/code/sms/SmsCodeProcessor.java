package cn.mrcode.imooc.springsecurity.securitycore.validate.code.sms;

import cn.mrcode.imooc.springsecurity.securitycore.validate.code.AbstractValidateCodeProcessor;
import cn.mrcode.imooc.springsecurity.securitycore.validate.code.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证处理器
 */
@Component("smsCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor {
    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    public void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}
