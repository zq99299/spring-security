package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

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

    private String id;
    private String username;
    @NotBlank
    private String password;
    @JsonView(UserSimpleView.class)
    private Date birthday;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
