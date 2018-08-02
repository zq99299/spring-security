package com.example.demo.web.async;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;


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

    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;

    //    @RequestMapping("/order")
//    public Callable<String> order() throws InterruptedException {
//        logger.info("主线程开始");
//        Callable<String> result = () -> {
//            logger.info("副线程开始");
//            TimeUnit.SECONDS.sleep(1);
//            logger.info("副线程返回");
//            return "success";
//        };
//        return result;
//    }
    @RequestMapping("/order")
    public DeferredResult<String> order() {
        logger.info("主线程开始");
        String orderNumber = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNumber);
        DeferredResult<String> deferredResult = new DeferredResult<>();
        // holder 只是用来存储 DeferredResult
        // 方便监听器线程拿到 DeferredResult
        deferredResultHolder.getMap().put(orderNumber, deferredResult);
        logger.info("主线程返回");
        return deferredResult;
    }
}
