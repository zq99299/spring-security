package cn.mrcode.imooc.springsecurity.securitycore.validate.code.sms;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/4 14:46
 * @date 2018/8/4 14:46
 * @since 1.0
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("短信模拟发送：" + mobile + " -> " + code);
    }
}
