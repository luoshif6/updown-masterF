package com.updown.service.impl;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.updown.common.pojo.UpdownResult;
import com.updown.common.utils.FastDFSClient;
import com.updown.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    //获取服务器IP地址
    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;
    //获取配置client文件地址
    private String conf = "classpath:properties/client.conf";

    /**
     * 文件上传
     *
     * @param
     */
    @Override
    public UpdownResult createFile(byte[] uploadFile, String extName) {

        FastDFSClient fastDFSClient = null;
        try {

            fastDFSClient = new FastDFSClient(conf);
            String url = fastDFSClient.uploadFile(uploadFile, extName);
            //url = IMAGE_SERVER_URL + url;
            return UpdownResult.ok(url);

        } catch (Exception e) {
            e.printStackTrace();
            return UpdownResult.build(404, "文件上传失败");
        }

    }

    /**
     * 文件下载
     *
     * @param filePath，fileName， fileUrl//代表本地下载位置
     */
    @Override
    public UpdownResult getFile(String filePath, String fileName, String fileUrl) {
        File file = new File("C:/updown/data");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            //接收文件路径
            FastDFSClient fastDFSClient = new FastDFSClient(conf);
            fastDFSClient.downloadFile(filePath, fileName, fileUrl);
//            回收资源，防止文件被占用
            System.gc();
            return UpdownResult.ok(UpdownResult.ok());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return UpdownResult.build(404, "文件下载失败");
        }
    }

    /**
     * 文件删除
     *
     * @param filePath
     */
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
            return UpdownResult.build(404, "文件删除失败");

        }
    }

    /**
     * 文件预览
     * 流程：下载到本地 -> 处理成pdf(处理后会一起删除处理文件和pdf文件) -> 上传到fastdfs(服务器文件在controller删除)
     *
     * @param filePath
     */
    @Override
    public UpdownResult filePreview(String filePath, String type) {
        try {
//        创建文件夹
            File file = new File("C:/updown/PreviewCache");
            if (!file.exists()) {
                file.mkdirs();
            }
//        下载文件到本地
            getFile(filePath, "pdf处理文件", "C:/updown/PreviewCache/");
//        获取处理文件文件
            File inPutFile = new File("C:/updown/PreviewCache/pdf处理文件" + "." + type);
//        创建pdf转换对象
            Document document = new Document();
            int a=10/0;
//        加载转换文件
            document.loadFromFile(String.valueOf(inPutFile));
//        设置uuid防止重复
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        转换成pdf文件
            document.saveToFile("C:/updown/PreviewCache/" + uuid + ".pdf", FileFormat.PDF);
//        关闭转换
            document.close();
//        第一次删除下载的临时文件
            deleteFile(inPutFile);
//        返回转换后的文件路径
            File uploadFile = new File("C:/updown/PreviewCache/" + uuid + ".pdf");
//        转换成字节
            byte[] bytes = file2Byte(uploadFile);
//            上传
            UpdownResult result = createFile(bytes, "PDF");
            deleteFile(uploadFile);
            String url = result.getData().toString();
//       第二次删除文件，防止删除失败
            deleteFile(inPutFile);
            deleteFile(uploadFile);
            return UpdownResult.ok(url);
        } catch (Exception e) {
//            如果处理失败返回原路径
            return UpdownResult.build(403,"文件预览失败,将下载文件");
        }
    }

    /*   *//**
     * 清空预览文件缓存
     *//*
    @Override
    public void deletePDFCache() {
        File file = new File("C:/updown/PreviewCache/");
//        如果没有该文件夹创建一个
        if (!file.exists()) {
            file.mkdirs();
        }
//        读取文件挨个删除
        File[] files = file.listFiles();
        for (File deleteFile : files) {
            deleteFile.delete();
        }
    }*/

    /**
     * 删除文件方法，文件预览内使用（filePreview）
     *
     * @param file
     */
    private void deleteFile(File file) {
        boolean result = file.delete();
        if (!result) {
            System.gc();    //回收资源
            file.delete();
        }
    }

    /**
     * file转字节文件
     *
     * @param tradeFile
     * @return
     */
    private byte[] file2Byte(File tradeFile) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}