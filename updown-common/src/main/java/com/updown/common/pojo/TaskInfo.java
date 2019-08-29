package com.updown.common.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther: 闫昊
 * @date: 2019/8/29
 */
public class TaskInfo implements Serializable {

    private Long task_id;  //任务id

    private String user_name;  //发布者名字

    private String task_name;  //任务名称

    private Date task_create_time;  //任务发布时间

    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
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

    public Date getTask_create_time() {
        return task_create_time;
    }

    public void setTask_create_time(Date task_create_time) {
        this.task_create_time = task_create_time;
    }

}
