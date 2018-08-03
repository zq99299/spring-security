package cn.mrcode.imooc.springsecurity.securitybrowser;

import cn.mrcode.imooc.springsecurity.securitycore.SecurityCoreApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityBrowserApplication {

    public static void main(String[] args) {
        Class[] applications = new Class[2];
        applications[0] = SecurityCoreApplication.class;
        applications[1] = SecurityBrowserApplication.class;
        SpringApplication.run(applications, args);
    }
}
