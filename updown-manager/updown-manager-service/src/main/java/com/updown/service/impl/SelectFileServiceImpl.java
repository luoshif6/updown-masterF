package com.updown.service.impl;

import com.updown.common.exceptions.ExceptionEnum;
import com.updown.common.exceptions.UpException;
import com.updown.mapper.FileMapper;
import com.updown.pojo.File;
import com.updown.service.SelectFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectFileServiceImpl implements SelectFileService {

    @Autowired
    private FileMapper fileMapper;


    //遍历所有文件
    @Override
    public List<File> selectAllFile() {
        List<File> files = fileMapper.selectAll();
        return files;
    }

    //根据用户id查文件
    @Override
    public List<File> selectFileByUserId(Long user_id) {


        File f = new File();
        f.setUser_id(user_id);
        List<File> files = fileMapper.select(f);
        return files;
    }
    //根据任务id查文件
    @Override
    public List<File> selectFileByTaskId(Long task_id) {
        File f = new File();
        f.setTask_id(task_id);
        List<File> files = fileMapper.select(f);
        return files;
    }
    //根据文件id查文件
    @Override
    public File selectFileByFileId(Long file_id) {

        return fileMapper.selectByPrimaryKey(file_id);


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

}
