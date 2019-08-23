package com.updown.controller;

import com.updown.common.pojo.UpdownResult;
import com.updown.pojo.File;
import com.updown.service.TbFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TbFileController {

    @Autowired
    private TbFileService tbFileService;

    @RequestMapping(value = "/file/insertTbFile",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UpdownResult> insertTbFile(File file) {
        if (file==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404, "无表单信息", null));
        }
        try {
            this.tbFileService.insertTbFile(file);
            return ResponseEntity.ok(UpdownResult.ok());
        }catch (Exception exception){
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404, "无表单信息", null));

        }


    }

}
