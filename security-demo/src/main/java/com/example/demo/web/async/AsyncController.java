package com.example.demo.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/2 17:33
 * @date 2018/8/2 17:33
 * @since 1.0
 */
@RestController
public class AsyncController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/order")
    public String order() {
        logger.info("主线程开始");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("主线程返回");
        return "success";
    }
}
