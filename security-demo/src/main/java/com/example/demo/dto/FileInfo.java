package com.example.demo.dto;

/**
 * ${desc}
 * @author zhuqiang
 * @version 1.0.1 2018/8/2 16:45
 * @date 2018/8/2 16:45
 * @since 1.0
 */
public class FileInfo {
    private String absolutePath;

    public FileInfo() {
    }

    public FileInfo(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
