package com.updown.service;

import com.updown.pojo.File;

import java.util.List;


public interface SelectFileService {
    /**
     * 查询所有文件
     * @param null
     * @return
     */
     List<File> selectAllFile();
    /**
     * 通过User_id查询用户下的文件
     * @param user_id
     * @return
     */
     List<File> selectFileByUserId(Long user_id);

}


