package com.updown.service.impl;

import com.updown.common.exceptions.ExceptionEnum;
import com.updown.common.exceptions.UpException;
import com.updown.common.pojo.FileInfo;
import com.updown.common.pojo.UpdownResult;
import com.updown.mapper.FileMapper;
import com.updown.pojo.File;
import com.updown.service.SelectFileService;
import com.updown.user.service.ManagerHandleService;
import com.updown.user.service.UserHandleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SelectFileServiceImpl implements SelectFileService {

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private UserHandleService userHandleService;
    @Autowired
    private ManagerHandleService managerHandleService;


    //遍历所有文件
    @Override
    public UpdownResult selectAllFile() {
        List<File> files = this.fileMapper.selectAll();
        List<FileInfo> fileInfos = new ArrayList<>();
        for (File file: files){
            //将file处理成fileinfo,剔除不需要的信息
            FileInfo fileInfo = new FileInfo();
            String type = StringUtils.substringAfterLast(file.getFile_url(), ".");
            //设置每个字段的信息
            fileInfo.setFile_id(file.getFile_id());
            fileInfo.setFile_name(file.getFile_name());
            fileInfo.setFile_create_time(file.getFile_create_time());
            fileInfo.setTask_name(this.managerHandleService.selectTaskByTaskId(file.getTask_id()).getTask_name());
            fileInfo.setUser_name(this.userHandleService.findUserByUserId(file.getUser_id()).getUser_name());
            fileInfo.setFile_create_time(file.getFile_create_time());
            fileInfo.setUser_type(file.getUser_type());
            fileInfo.setFile_type(type);
            //添加到集合中
            fileInfos.add(fileInfo);
        }
        return UpdownResult.ok(fileInfos);
    }

    //根据用户id查文件
    @Override
    public UpdownResult selectFileByUserId(Long user_id) {

        File f = new File();
        f.setUser_id(user_id);
        List<File> files = fileMapper.select(f);

        List<FileInfo> fileInfos = new ArrayList<>();
        for(File file: files){
            //将file处理成fileinfo,剔除不需要的信息
            FileInfo fileInfo = new FileInfo();
            String type = StringUtils.substringAfterLast(file.getFile_url(), ".");
            //设置每个字段的信息
            fileInfo.setFile_id(file.getFile_id());
            fileInfo.setFile_name(file.getFile_name());
            fileInfo.setFile_create_time(file.getFile_create_time());
            fileInfo.setTask_name(this.managerHandleService.selectTaskByTaskId(file.getTask_id()).getTask_name());
            fileInfo.setUser_name(this.userHandleService.findUserByUserId(file.getUser_id()).getUser_name());
            fileInfo.setFile_create_time(file.getFile_create_time());
            fileInfo.setUser_type(file.getUser_type());
            fileInfo.setFile_type(type);
            //添加到集合中
            fileInfos.add(fileInfo);
        }
        return UpdownResult.ok(fileInfos);

    }

    //根据任务id查文件
    @Override
    public UpdownResult selectFileByTaskId(Long task_id) {
        File f = new File();
        f.setTask_id(task_id);
        List<File> files = fileMapper.select(f);

        List<FileInfo> fileInfos = new ArrayList<>();
        for(File file: files){
            //将file处理成fileinfo,剔除不需要的信息
            FileInfo fileInfo = new FileInfo();
            String type = StringUtils.substringAfterLast(file.getFile_url(), ".");
            //设置每个字段的信息
            fileInfo.setFile_id(file.getFile_id());
            fileInfo.setFile_name(file.getFile_name());
            fileInfo.setFile_create_time(file.getFile_create_time());
            fileInfo.setTask_name(this.managerHandleService.selectTaskByTaskId(file.getTask_id()).getTask_name());
            fileInfo.setUser_name(this.userHandleService.findUserByUserId(file.getUser_id()).getUser_name());
            fileInfo.setFile_create_time(file.getFile_create_time());
            fileInfo.setUser_type(file.getUser_type());
            fileInfo.setFile_type(type);
            //添加到集合中
            fileInfos.add(fileInfo);
        }

        return UpdownResult.ok(fileInfos);
    }

    /**
     * 根据file_id查询file
     *
     * @param file_id
     * @return
     */
    @Override
    public File selectFileByFileId(Long file_id) {
        if (file_id == null) {
            throw new UpException(ExceptionEnum.FILE_ID_NULL);
        }
        File select = fileMapper.selectByPrimaryKey(file_id);
        if (select == null) {
            throw new UpException(ExceptionEnum.FILE_FOUND_FAIL);
        }
        return select;
    }

    /**
     * 根据url查询file
     *
     * @param file_url
     * @return
     */
    @Override
    public File selectFileByFileUrl(String file_url) {
        File file = new File();
        System.out.println(file_url);
        file.setFile_url(file_url);
        File one = fileMapper.selectOne(file);
        System.out.println(one);
        return one;
    }

}
