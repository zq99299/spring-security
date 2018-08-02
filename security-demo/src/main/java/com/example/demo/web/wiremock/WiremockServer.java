package com.example.demo.web.wiremock;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2018/8/2 23:12
 */
public class WiremockServer {
    public static void main(String[] args) throws IOException {
        configureFor(9999); // 独立服务的端口，在本机就不用写ip了
        removeAllMappings(); // 清除所有的配置，因为每次更新都需要重新写入配置信息
        mock("/order/1", "01");
    }

    private static void mock(String url, String fileName) throws IOException {
        // 编写一个测试桩
        // get请求，严格匹配一个url地址
        ClassPathResource json01 = new ClassPathResource("mock/" + fileName + ".json");
        // https://mvnrepository.com/artifact/commons-io/commons-io
//        compile group: 'commons-io', name: 'commons-io', version: '2.6'
        String body = org.apache.commons.io.FileUtils.readFileToString(json01.getFile(), "utf-8");
        stubFor(get(urlPathEqualTo(url))
                // 定义该地址返回的数据和http状态
                .willReturn(aResponse()
                        .withBody(body) // 返回内容
                        .withStatus(200) // http状态码
                        // 添加响应头，防止中文乱码
                        .withHeader("Content-Type", "application/json;charset=UTF-8"))
        );
    }
}
