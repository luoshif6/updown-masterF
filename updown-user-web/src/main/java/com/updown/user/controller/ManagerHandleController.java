package com.updown.user.controller;

import com.updown.common.exceptions.ExceptionEnum;
import com.updown.common.exceptions.UpException;
import com.updown.common.pojo.UpdownResult;
import com.updown.common.utils.CookieUtils;
import com.updown.pojo.Task;
import com.updown.pojo.User;
import com.updown.sso.service.UserLoginService;
import com.updown.sso.service.UserRegisterService;
import com.updown.user.service.ManagerHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 管理员相关操作
 */
@Controller
@RequestMapping("manager")
public class ManagerHandleController {

    @Autowired
    private ManagerHandleService managerHandleService;

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserLoginService userLoginService;

    @Value("${UP_TOKEN_KEY}")
    private String UP_TOKEN_KEY;

    /**
     * 用户删除
     *
     * @param user_id
     * @retur
     */
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public ResponseEntity<UpdownResult> deleteUserById(@RequestParam("user_id") Long user_id) {
        UpdownResult result = this.managerHandleService.deleteUserById(user_id);
        if (result.getStatus() == 200) {
            //删除成功
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @RequestMapping(value = "query", method = RequestMethod.GET)
    public ResponseEntity<UpdownResult> selectAllUser() {
        UpdownResult result = this.managerHandleService.selectAllUser();
        if (result.getStatus() == 200) {
            //查询成功
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    /**
     * 查询用户类型
     *
     * @param user_id
     * @return
     */
    @RequestMapping(value = "getUserType", method = RequestMethod.GET)
    public ResponseEntity<UpdownResult> getUserType(Long user_id) {
        UpdownResult result = this.managerHandleService.getUserType(user_id);
        if (result.getStatus() == 200) {
            //查询成功
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    /**
     * 添加管理员
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "insertManager", method = RequestMethod.POST)
    public ResponseEntity<UpdownResult> insertManager(User user) {
        //2.添加管理员
        //2.1首先判断数据是否可用
        Boolean username_bol = this.userRegisterService.selectCheckUser(user.getUser_name(), 1);
        Boolean number_bol = this.userRegisterService.selectCheckUser(user.getUser_number(), 2);
        if (username_bol && number_bol) {
            //2.2注册管理员
            user.setUser_type(false);  //类型设置为管理员类型
            this.userRegisterService.createUser(user);
        }
        return ResponseEntity.ok(UpdownResult.ok());
    }

    /**
     * 管理员添加任务
     */
    @RequestMapping(value = "insertTask", method = RequestMethod.POST)
    public ResponseEntity<Void> insertTask(@RequestBody Task task, HttpServletRequest request) {
        if (task == null) {
            throw new UpException(ExceptionEnum.USER_DATA_NULL);
        }
//        获取token
        String token = CookieUtils.getCookieValue(request, UP_TOKEN_KEY);
//        通过sso的服务获取用户信息
        UpdownResult result = userLoginService.findUserByToken(token);
        User user = (User) result.getData();
//        存入task
        task.setUser_id(user.getUser_id());
        managerHandleService.insertTask(task);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 查询所有任务
     */
    @RequestMapping(value = "selectAll", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> selectAllTask() {
        return ResponseEntity.ok(managerHandleService.selectAllTask());
    }

}
