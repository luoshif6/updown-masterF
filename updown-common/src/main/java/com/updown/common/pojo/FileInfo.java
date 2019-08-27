package com.updown.common.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 文件信息实体类
 */
public class FileInfo implements Serializable {




    private String file_name;   //文件名称
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date file_create_time;   //文件创建时间

    private Boolean user_type;  //用户类型  true:管理员  false:学生

    private String user_name;  //上传者的name

    private String task_name;   //作业任务的name





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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }
}
