package com.updown.common.pojo;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * 接受FormData对象
 */
public class RequestFile implements Serializable{

    private MultipartFile uploadFile;

    public MultipartFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}
