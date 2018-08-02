package com.example.demo.web.controller;

import com.example.demo.dto.FileInfo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/2 16:42
 * @date 2018/8/2 16:42
 * @since 1.0
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        File localFile = new File("fileUploadTest.txt");
        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        try (FileInputStream inputStream = new FileInputStream(new File("G:\\dev\\project\\mrcode\\example\\imooc\\spring-security\\security-demo", id));
             ServletOutputStream outputStream = response.getOutputStream();
        ) {
            // 声明响应类型
            response.setContentType("application/x-download");
            // 下载的文件名称
            response.addHeader("Content-Disposition", "attachment;filename-test.txt");
            IOUtils.copy(inputStream, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
