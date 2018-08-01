package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/1 23:06
 */
public class User {
    // 简单视图
    public interface UserSimpleView {
    }

    // 详细视图
    public interface UserDetailView extends UserSimpleView {
    }

    private String username;
    private String password;

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
