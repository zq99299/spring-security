package com.example.demo;

import cn.mrcode.imooc.springsecurity.securitybrowser.SecurityBrowserApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@RestController
@EnableSwagger2
public class DemoApplication {

    public static void main(String[] args) {
        Class[] applications = new Class[2];
        applications[0] = SecurityBrowserApplication.class;
        applications[1] = DemoApplication.class;
        SpringApplication.run(applications, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security";
    }
}
