package com.updown.service.impl;

import com.updown.common.exceptions.ExceptionEnum;
import com.updown.common.exceptions.UpException;
import com.updown.common.pojo.UpdownResult;
import com.updown.mapper.FileMapper;
import com.updown.pojo.File;
import com.updown.service.TbFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TbFileServiceImpl implements TbFileService {

    @Autowired
    private FileMapper fileMapper;

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
        file.setTask_id(null);
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
                return UpdownResult.build(404, "删除错误");
            }
            int i = this.fileMapper.deleteByPrimaryKey(file_id);
            if (i <= 0) {
                return UpdownResult.build(404, "删除错误");
            }
            return UpdownResult.ok();
        }




}
