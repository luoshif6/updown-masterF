package com.updown.user.controller;

import com.updown.common.pojo.UpdownResult;
import com.updown.common.pojo.UserBo;
import com.updown.user.service.UserHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 普通用户的相关操作
 */
@Controller
@RequestMapping("user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserHandleConterlloer {

    @Autowired
    private UserHandleService userHandleService;

    /**
     * 密码修改
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseEntity<UpdownResult> updateUserPassword(@RequestBody UserBo user) {
        UpdownResult result = this.userHandleService.updateUserPassword(user);
        if (result.getStatus() == 200) {
            //修改成功
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
