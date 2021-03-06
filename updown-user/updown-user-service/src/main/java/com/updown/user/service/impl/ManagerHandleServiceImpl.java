package com.updown.user.service.impl;

import com.updown.common.exceptions.ExceptionEnum;
import com.updown.common.exceptions.UpException;
import com.updown.common.pojo.TaskInfo;
import com.updown.common.pojo.UpdownResult;
import com.updown.common.pojo.UserInfo;
import com.updown.mapper.TaskMapper;
import com.updown.mapper.UserMapper;
import com.updown.pojo.Task;
import com.updown.pojo.User;
import com.updown.user.service.ManagerHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerHandleServiceImpl implements ManagerHandleService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public UpdownResult deleteUserById(Long user_id) {
        if (user_id == null) {
            return UpdownResult.build(404, "删除错误");
        }
        int i = this.userMapper.deleteByPrimaryKey(user_id);
        if (i <= 0) {
            return UpdownResult.build(404, "删除错误");
        }
        return UpdownResult.ok();
    }

    @Override
    public UpdownResult selectAllUser() {
        List<User> users = this.userMapper.selectAll();
        List<UserInfo> userInfos = new ArrayList<>();
        for (User user: users){
            //将user处理成userinfo,剔除不需要的信息
            UserInfo userInfo = new UserInfo();
            //设置每个字段的信息
            userInfo.setUser_id(user.getUser_id());
            userInfo.setUser_name(user.getUser_name());
            userInfo.setUser_number(user.getUser_number());
            userInfo.setUser_type(user.getUser_type());
            userInfo.setUser_create_time(user.getUser_create_time());
            //添加到集合中
            userInfos.add(userInfo);
        }
        return UpdownResult.ok(userInfos);
    }

    @Override
    public UpdownResult getUserType(Long user_id) {
        User user = this.userMapper.selectByPrimaryKey(user_id);
        if (user == null) {
            return UpdownResult.build(404, "请求错误");
        }
        return UpdownResult.ok(user.getUser_type());
    }

    /**
     * 管理员添加任务
     *
     * @return
     */
    @Override
    public void insertTask(Task task) {
        int count = taskMapper.insert(task);
        if (count != 1) {
            throw new UpException(ExceptionEnum.USER_DELETE_EXCEPTION);
        }
    }

    /**
     * 查询所有任务
     *
     * @return
     */
    @Override
    public List selectAllTask() {
        List<Task> tasks = taskMapper.selectAll();
        List<TaskInfo> taskInfos = new ArrayList<>();
        for (Task task : tasks) {
            TaskInfo taskInfo = new TaskInfo();
            String user_name = userMapper.selectByPrimaryKey(task.getUser_id()).getUser_name();
            taskInfo.setTask_id(task.getTask_id());
            taskInfo.setTask_name(task.getTask_name());
            taskInfo.setTask_create_time(task.getTask_create_time());
            taskInfo.setUser_name(user_name);
            taskInfos.add(taskInfo);
        }

        if (taskInfos == null) {
            throw new UpException(ExceptionEnum.TASK_SELECT_FAIL);
        }
        return taskInfos;
    }

    /**
     * 根据task_id查询task
     */
    @Override
    public Task selectTaskByTaskId(Long task_id) {
        if (task_id == null) {
            throw new UpException(ExceptionEnum.TASK_ID_NULL);
        }
        Task task = taskMapper.selectByPrimaryKey(task_id);
        if (task == null) {
            throw new UpException(ExceptionEnum.TASK_SELECT_FAIL);
        }
        return task;

    }

    /**
     * 根据task_id删除task
     */
    @Override
    public void deleteTaskByTaskId(Long task_id) {
        if (task_id == null) {
            throw new UpException(ExceptionEnum.TASK_ID_NULL);
        }
        taskMapper.deleteByPrimaryKey(task_id);
    }

}
