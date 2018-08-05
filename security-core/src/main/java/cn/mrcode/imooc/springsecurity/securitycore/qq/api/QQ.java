package cn.mrcode.imooc.springsecurity.securitycore.qq.api;

import java.io.IOException;

/**
 * qq
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/6 0:44
 */
public interface QQ {
    QQUserInfo getUserInfo() throws IOException;
}
