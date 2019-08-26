package com.updown.service.impl;

import com.updown.common.pojo.UpdownResult;
import com.updown.common.utils.FastDFSClient;
import com.updown.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
    //获取服务器IP地址
    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;
    //获取配置client文件地址
    private String conf = "classpath:properties/client.conf";

    //文件上传方法
    @Override
    public UpdownResult createFile(byte[] uploadFile, String extName) {

        FastDFSClient fastDFSClient = null;


        try {

            fastDFSClient = new FastDFSClient(conf);
            String url = fastDFSClient.uploadFile(uploadFile, extName);
            System.out.println(url);
            //url = IMAGE_SERVER_URL + url;
            return UpdownResult.ok(url);

        } catch (Exception e) {
            e.printStackTrace();
            return UpdownResult.build(404,"文件上传失败");
        }

    }
    //文件下载
    @Override
    public UpdownResult getFile(String filePath, String fileName,String fileUrl) {
        try {
            //接收文件路径
            FastDFSClient fastDFSClient = new FastDFSClient(conf);
            fastDFSClient.downloadFile(filePath, fileName,fileUrl);
            return UpdownResult.ok(UpdownResult.ok());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return UpdownResult.build(404,"文件下载失败");
        }
    }

    @Override
    public UpdownResult deleteFile(String filePath) {
        try {
            //接收文件路径
            //取扩展名
            FastDFSClient fastDFSClient = new FastDFSClient(conf);
            fastDFSClient.deleteFile(filePath);
            return UpdownResult.ok(UpdownResult.ok());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return UpdownResult.build(404,"文件删除失败");

        }
    }
}