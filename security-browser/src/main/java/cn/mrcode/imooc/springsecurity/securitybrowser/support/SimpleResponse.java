package cn.mrcode.imooc.springsecurity.securitybrowser.support;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/3 15:07
 * @date 2018/8/3 15:07
 * @since 1.0
 */
public class SimpleResponse {
    private Object content;

    public SimpleResponse(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
