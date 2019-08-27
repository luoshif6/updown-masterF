package com.updown.sso.controller;

import com.updown.common.pojo.UpdownResult;
import com.updown.sso.service.UserExitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户安全退出
 */
@Controller
@RequestMapping("user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserExitController {

    @Autowired
    private UserExitService userExitService;

    /**
     * 用户安全退出
     * @param token
     * @return
     */
    @RequestMapping(value = "logout/{token}",method = RequestMethod.GET)
    public ResponseEntity<UpdownResult> deleteUserStatusByToken(@PathVariable("token") String token){
        UpdownResult updownResult = this.userExitService.deleteUserStatusByToken(token);
        if (updownResult.getStatus() == 200){
            return ResponseEntity.ok(updownResult);
        }
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updownResult);
    }
}
