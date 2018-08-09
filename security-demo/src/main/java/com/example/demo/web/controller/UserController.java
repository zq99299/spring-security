package com.example.demo.web.controller;

import cn.mrcode.imooc.springsecurity.securityapp.social.AppSignUpUtils;
import com.example.demo.dto.User;
import com.example.demo.dto.UserQueryCondition;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/1 23:05
 */
@RestController
@RequestMapping("/user")
public class UserController {
//    @Autowired
//    private ProviderSignInUtils providerSignInUtils;
    @Autowired
    private AppSignUpUtils appSignUpUtils;

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "用户查询服务")  // 方法描述
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


    // 获取用户详情
    // 在路径中使用参数
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@ApiParam(value = "用户id") @PathVariable String id) {
        System.out.println("进入 getInfo 服务");
//        throw new UserNotExistException(id);
        User user = new User();
        user.setUsername("mrcode");
        return user;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(err -> {
                System.out.println(err.getDefaultMessage());
            });
        }
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        user.setId("1");
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User update(@PathVariable() String id, @Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(err -> {
                FieldError fieldError = (FieldError) err;
                System.out.println(fieldError.getField() + " : " + err.getDefaultMessage());
            });
        }
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        user.setId(id);
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        System.out.println("id:" + id);
    }

    @GetMapping("/me")
    public Authentication getCurrentUser(@AuthenticationPrincipal UserDetails userDetails, Authentication authentication) {
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @PostMapping("/regist")
    public void regist(User user, HttpServletRequest request) {

        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识。
        String userId = user.getUsername();
        appSignUpUtils.doPostSignUp(userId, new ServletWebRequest(request));
    }
}
