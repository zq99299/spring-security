package cn.mrcode.imooc.springsecurity.securitycore.validate.code.sms;

/**
 * <pre>
 *
 * </pre>
 * @author zhuqiang
 * @version 1.0.0
 * @date 2018/8/4 14:46
 * @since 1.0.0
 */
public interface SmsCodeSender {
    void send(String mobile, String code);
}
