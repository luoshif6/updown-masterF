package com.updown.controller;


import com.updown.common.pojo.UpdownResult;
import com.updown.pojo.File;
import com.updown.service.SelectFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("select")
public class SelectFileController {
    /**
     * 查询所有的文件
     * @param user_id
     * @return
     */

    @Autowired
    private SelectFileService selectFileService;

    @RequestMapping(value = "allfile",method = RequestMethod.GET)
    public  ResponseEntity<UpdownResult> selectAllFile(){
        UpdownResult result = this.selectFileService.selectAllFile();
            if (result.getStatus() == 200) {
                //查询成功
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }




    /**
     * 通过User_id查询用户下的文件
     * @param user_id
     * @return
     */
    @RequestMapping(value = "userfile",method = RequestMethod.GET)
    public ResponseEntity<UpdownResult> selectFileByUserId(@RequestParam("user_id")Long user_id){
        List<File> files = this.selectFileService.selectFileByUserId(user_id);
        if (CollectionUtils.isEmpty(files)){
            //文件列表为空
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404,"未找到文件",null));
        }
        return ResponseEntity.ok(UpdownResult.ok(files));
    }

    /**
     * 通过Task_id查询用户下的文件
     * @param task_id
     * @return
     */
    @RequestMapping(value = "taskfile",method = RequestMethod.GET)
    public ResponseEntity<UpdownResult> selectFileByTaskId(@RequestParam("task_id")Long task_id){
        List<File> files = this.selectFileService.selectFileByTaskId(task_id);
        if (CollectionUtils.isEmpty(files)){
            //文件列表为空
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404,"未找到文件",null));
        }
        return ResponseEntity.ok(UpdownResult.ok(files));
    }


}
