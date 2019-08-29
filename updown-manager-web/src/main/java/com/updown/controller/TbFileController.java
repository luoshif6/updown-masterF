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

/**
 * tb_file 表操作
 *
 * @param
 * @retur
 */
@Controller
@RequestMapping("table")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TbFileController {

    @Value("${UP_TOKEN_KEY}")
    private String UP_TOKEN_KEY;

    //  注入tbFileService
    @Autowired
    private TbFileService tbFileService;

    //注入UserLoginService获取用户信息
    @Autowired
    private UserLoginService userLoginService;

    /**
     * 上传文件
     *
     * @param
     * @retur
     */
    @RequestMapping(value = "insertfile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UpdownResult> insertTbFile(@RequestParam("file_name") String file_name,
                                                     @RequestParam("file_url") String file_url,
                                                     @RequestParam("task_id") Long task_id,
                                                     HttpServletRequest request) {
//      如果file为空，抛出异常xx
        if (file_name == null || file_url==null) {
            throw new UpException(ExceptionEnum.FILE_INSERT_ERROR);
        }

//      获取token
        String token = CookieUtils.getCookieValue(request, UP_TOKEN_KEY);
//      通过sso的服务获取用户信息
        UpdownResult result = this.userLoginService.findUserByToken(token);
        User user = (User) result.getData();
        File f = new File();
        f.setFile_name(file_name);
        f.setFile_url(file_url);
        f.setTask_id(task_id);
        //因暂时无法获得token信息，方便测试，所以加上下面两行
        f.setUser_type(true);
        f.setUser_id(Long.valueOf(9));
        if (user != null) {
            //      将用户类型以及用户id存入file对象
            f.setUser_type(user.getUser_type());
            f.setUser_id(user.getUser_id());
        }
        this.tbFileService.insertTbFile(f);
        return ResponseEntity.ok(UpdownResult.ok());


    }

    /**
     * 根据file_id删除文件
     *
     * @param file_id
     * @retur
     */
    @RequestMapping(value = "deletefile", method = RequestMethod.DELETE)
    public ResponseEntity<UpdownResult> deleteUserById(@RequestParam("file_id") Long file_id) {
        UpdownResult result = this.tbFileService.deleteTbFileById(file_id);
        if (result.getStatus() == 200) {
            //删除成功
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }


}
