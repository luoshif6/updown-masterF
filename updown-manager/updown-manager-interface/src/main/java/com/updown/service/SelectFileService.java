package com.updown.service;

import com.updown.common.pojo.UpdownResult;
import com.updown.pojo.File;

import java.util.List;


public interface SelectFileService {
    /**
     * 查询所有文件
     * @return
     */
     UpdownResult selectAllFile();
    /**
     * 通过User_id查询用户下的文件
     * @param user_id
     * @return
     */
     List<File> selectFileByUserId(Long user_id);

    /**
     * 通过Task_id查询用户下的文件
     * @param task_id
     * @return
     */
    List<File> selectFileByTaskId(Long task_id);
    /**
     * 通过文件id查询文件
     * @param file_id
     * @return
     */
    File selectFileByFileId(Long file_id);
}


