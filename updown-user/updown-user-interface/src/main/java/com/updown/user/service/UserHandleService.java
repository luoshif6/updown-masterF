package com.updown.user.service;

import com.updown.common.pojo.UpdownResult;
import com.updown.pojo.User;

/**
 * 用户操作
 */
public interface UserHandleService {

    /**
     * 修改密码
     * @param user_id
     * @param user_password
     * @return
     */
    UpdownResult updateUserPassword(Long user_id, String user_password);

    /**
     * 根据用户id查询用户
     * @param user_id
     * @return
     */
    User findUserByUserId(Long user_id);

}
