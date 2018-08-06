package cn.mrcode.imooc.springsecurity.securitybrowser.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * session并发登录失效策略
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/6 21:28
 */
public class MySessionInformationExpiredStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {
    /**
     * @param invalidSessionUrl
     */
    public MySessionInformationExpiredStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
