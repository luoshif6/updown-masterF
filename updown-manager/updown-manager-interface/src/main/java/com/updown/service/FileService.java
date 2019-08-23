package com.updown.service;


import com.updown.common.pojo.UpdownResult;

public interface FileService {

    //文件上传
     UpdownResult createFile(byte[] uploadFile, String extName);
    //文件下载
     UpdownResult getFile(String filePath, String fileName);
    //文件删除
     UpdownResult deleteFile(String filePath);
}
