package com.example.demo.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 模拟消息队列
 * @author zhuqiang
 * @version 1.0.1 2018/8/2 17:54
 * @date 2018/8/2 17:54
 * @since 1.0
 */
@Component
public class MockQueue {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String placeOrder; // 请求下单
    private String completeOrder;  // 下单完成

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) {
        // 模拟另外一个线程去执行耗时操作
        new Thread(() -> {
            logger.info("接到下单请求");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.completeOrder = "下单请求处理完毕";
            this.placeOrder = placeOrder;
            logger.info(completeOrder + " : " + placeOrder);
        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
