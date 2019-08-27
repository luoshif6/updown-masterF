package com.updown.controller;

import com.updown.common.exceptions.ExceptionEnum;
import com.updown.common.exceptions.UpException;
import com.updown.common.pojo.UpdownResult;
import com.updown.common.utils.CookieUtils;
import com.updown.pojo.File;
import com.updown.pojo.User;
import com.updown.service.TbFileService;
import com.updown.sso.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/*
Tb_File表操作
 */
@Controller
@RequestMapping("table")
public class TbFileController {

    @Value("${UP_TOKEN_KEY}")
    private String UP_TOKEN_KEY;

    //  注入tbFileService
    @Autowired
    private TbFileService tbFileService;

    //注入UserLoginService获取用户信息
    @Autowired
    private UserLoginService userLoginService;

    //  文件上传
    @RequestMapping(value = "insertfile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UpdownResult> insertTbFile(@RequestBody File file, HttpServletRequest request) {
//      如果file为空，抛出异常
        if (file == null) {
            throw new UpException(ExceptionEnum.FILE_INSERT_ERROR);
        }
//      获取token
        String token = CookieUtils.getCookieValue(request, UP_TOKEN_KEY);
//      通过sso的服务获取用户信息
        UpdownResult result = this.userLoginService.findUserByToken(token);
        User user = (User) result.getData();
        if (user != null) {
            //      将用户类型以及用户id存入file对象
            file.setUser_type(user.getUser_type());
            file.setUser_id(user.getUser_id());
        }
        this.tbFileService.insertTbFile(file);
        return ResponseEntity.ok(UpdownResult.ok(file));


    }

    /**
     * 根据file_id删除文件
     *
     * @param file_id
     * @retur
     */
    @RequestMapping(value = "deletefile", method = RequestMethod.GET)
    public ResponseEntity<UpdownResult> deleteUserById(@RequestParam("file_id") Long file_id) {
        UpdownResult result = this.tbFileService.deleteTbFileById(file_id);
        if (result.getStatus() == 200) {
            //删除成功
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }


}
