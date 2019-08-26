package com.updown.common.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件信息实体类
 */
public class Fileinfo implements Serializable {


    private Long file_id;  //文件id

    private String file_name;   //文件名称

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date file_create_time;   //文件创建时间

    private Boolean user_type;  //用户类型  true:管理员  false:学生

    private Long user_id;  //用户id上传者id

    private Long task_id;   //作业任务的id

    public Long getFile_id() {
        return file_id;
    }

    public void setFile_id(Long file_id) {
        this.file_id = file_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }



    public Date getFile_create_time() {
        return file_create_time;
    }

    public void setFile_create_time(Date file_create_time) {
        this.file_create_time = file_create_time;
    }

    public Boolean getUser_type() {
        return user_type;
    }

    public void setUser_type(Boolean user_type) {
        this.user_type = user_type;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }
}
