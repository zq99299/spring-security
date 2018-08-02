package cn.mrcode.imooc.springsecurity.securitybrowser;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/3 0:53
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @GetMapping
    public String getInfo() {
        return "ok";
    }
}
