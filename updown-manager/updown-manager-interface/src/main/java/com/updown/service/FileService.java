package com.updown.service;


import com.updown.common.pojo.UpdownResult;

public interface FileService {

    //文件上传
     UpdownResult createFile(byte[] uploadFile, String extName);
    //文件下载   根据文件在服务器中的地址，文件要保存的名字，以及文件保存的路径来下载
     UpdownResult getFile(String filePath, String fileName,String fileUrl);
    //文件删除
     UpdownResult deleteFile(String filePath);
}
