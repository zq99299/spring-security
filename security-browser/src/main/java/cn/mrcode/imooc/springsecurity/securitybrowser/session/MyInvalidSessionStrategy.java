package cn.mrcode.imooc.springsecurity.securitybrowser.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/6 22:00
 */
public class MyInvalidSessionStrategy extends AbstractSessionStrategy implements InvalidSessionStrategy {
    /**
     * @param invalidSessionUrl
     */
    public MyInvalidSessionStrategy(String invalidSessionUrl) {
        super(invalidSessionUrl);
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        onSessionInvalid(request, response);
    }

    @Override
    protected boolean isConcurrency() {
        return false;
    }
}
