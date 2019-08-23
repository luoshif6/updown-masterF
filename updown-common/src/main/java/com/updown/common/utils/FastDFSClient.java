package com.updown.common.utils;


import org.apache.commons.io.IOUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.FileOutputStream;

public class FastDFSClient {

    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient1 storageClient = null;

    public FastDFSClient(String conf) throws Exception {
        if (conf.contains("classpath:")) {
            conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
        }
        //3.加载配置文件
        ClientGlobal.init(conf);
        //4.创建一个TrackerClient对象
        trackerClient = new TrackerClient();
        //5.使用TrackerClient对象获得trackerserver对象
        trackerServer = trackerClient.getConnection();
        //6.创建一个StorageServer的引用null就可以
        storageServer = null;
        //7.创建一个StorageClient对象。trackerserver、StorageServer两个参数
        storageClient = new StorageClient1(trackerServer, storageServer);
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     * @param fileName 文件全路径
     * @param extName 文件扩展名，不包含（.）
     * @param metas 文件扩展信息
     * @return
     * @throws Exception
     */






    public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
        String result = storageClient.upload_file1(fileName, extName, metas);
        return result;
    }

    public String uploadFile(String fileName) throws Exception {
        return uploadFile(fileName, null, null);
    }

    public String uploadFile(String fileName, String extName) throws Exception {
        return uploadFile(fileName, extName, null);
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     * @param fileContent 文件的内容，字节数组
     * @param extName 文件扩展名
     * @param metas 文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {

        String result = storageClient.upload_file1(fileContent, extName, metas);
        return result;
    }

    public String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null, null);
    }

    public String uploadFile(byte[] fileContent, String extName) throws Exception {
        return uploadFile(fileContent, extName, null);
    }



    /**
     * 下载文件方法
     * <p>Title: downloadFile</p>
     * <p>Description: </p>
     * @param fileName 文件全路径
     * @param
     * @param
     * @return
     * @throws Exception
     */
    public void downloadFile(String filePath,String fileName) throws Exception {

        String extName = filePath.substring(filePath.lastIndexOf(".")+1);
        // 根据文件标识下载文件
        byte[] by = storageClient.download_file1(filePath);
        // 将数据写入输出流
        IOUtils.write(by, new FileOutputStream("D:/xiazai/"+fileName+"."+extName));
    }
    /**
     * 删除文件方法
     * <p>Title: downloadFile</p>
     * <p>Description: </p>
     * @param fileName 文件全路径
     * @param extName 文件扩展名，不包含（.）
     * @param
     * @return
     * @throws Exception
     */
    public void deleteFile(String filePath) throws Exception {

        // 根据文件标识删除文件，返回0则删除成功
        int i = storageClient.delete_file1(filePath);
        if (i == 0) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }





}
