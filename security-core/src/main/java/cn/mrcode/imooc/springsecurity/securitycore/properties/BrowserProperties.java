package cn.mrcode.imooc.springsecurity.securitycore.properties;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/3 15:29
 * @date 2018/8/3 15:29
 * @since 1.0
 */
public class BrowserProperties {
    /** 登录页面路径 */
    private String loginPage = "/imocc-signIn.html";


    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
