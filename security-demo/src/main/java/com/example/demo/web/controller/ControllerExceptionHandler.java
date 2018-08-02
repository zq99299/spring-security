package com.example.demo.web.controller;

import com.example.demo.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/2 14:26
 * @date 2018/8/2 14:26
 * @since 1.0
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(UserNotExistException.class)
    @ResponseBody  // 用json方式返回
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)   // 返回http状态码
    public Map<String, Object> handleUserNotExistException(UserNotExistException ux) {
        Map<String, Object> resullt = new HashMap<>();
        resullt.put("id", ux.getId());
        resullt.put("message", ux.getMessage());
        return resullt;
    }
}
