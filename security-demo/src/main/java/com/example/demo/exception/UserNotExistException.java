package com.example.demo.exception;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/2 14:20
 * @date 2018/8/2 14:20
 * @since 1.0
 */
public class UserNotExistException extends RuntimeException {
    private String id;

    public UserNotExistException(String id) {
        super("user not exist");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
