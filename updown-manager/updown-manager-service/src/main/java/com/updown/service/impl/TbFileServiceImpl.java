package com.updown.service.impl;

import com.updown.common.exceptions.ExceptionEnum;
import com.updown.common.exceptions.UpException;
import com.updown.common.pojo.UpdownResult;
import com.updown.mapper.FileMapper;
import com.updown.pojo.File;
import com.updown.service.FileService;
import com.updown.service.SelectFileService;
import com.updown.service.TbFileService;
import javafx.beans.DefaultProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TbFileServiceImpl implements TbFileService {

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private SelectFileService selectFileService;
    @Autowired
    private FileService fileService;
    /**
     * 用户添加文件
     *
     * @return
     */
    @Override
    public UpdownResult insertTbFile(File file) {
        //补全file属性
        file.setFile_id(null);
        file.setUser_id(file.getUser_id());
        file.setFile_name(file.getFile_name());
        file.setFile_create_time(new Date());
        file.setFile_url(file.getFile_url());
        file.setUser_type(file.getUser_type());
        file.setTask_id(file.getTask_id());
        //进行插入操作
        int count = fileMapper.insert(file);
        //如果执行结果条数不为1，则抛出异常
        if (count != 1) {
            throw new UpException(ExceptionEnum.USER_DELETE_EXCEPTION);
        }
        return UpdownResult.ok();
    }
    /**
     * 用户删除文件
     *
     * @return
     */
    @Override
    public UpdownResult deleteTbFileById(Long file_id) {
            if (file_id == null) {
                return UpdownResult.build(404, "删除错误0");
            }

            try {
                 //根据文件id，将服务器内的文件也一并删除
                this.fileService.deleteFile(this.selectFileService.selectFileByFileId(file_id).getFile_url());
                //删除表里的数据
                int i = this.fileMapper.delete(selectFileService.selectFileByFileId(file_id));

                 if (i <= 0) {
                                return UpdownResult.build(404, "删除错误1");
                            }
                return UpdownResult.ok();
            }catch (Exception e){
                e.printStackTrace();
                return UpdownResult.build(404, "删除错误2");
            }




        }




}
