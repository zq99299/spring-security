package com.example.demo.web.controller;

import com.example.demo.dto.User;
import com.example.demo.dto.UserQueryCondition;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/1 23:05
 */
@RestController
public class UserController {
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> query(UserQueryCondition condition) {
        // compile group: 'org.apache.commons', name: 'commons-lang3', version: '3.7'
        // 一个反射工具类，这里把对象变成一个字符串，支持多种展示形式
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }
}
