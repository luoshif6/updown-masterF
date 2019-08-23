package com.updown.controller;

import com.updown.common.pojo.UpdownResult;
import com.updown.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


//文件管理
@Controller
public class FileController {

    @Autowired
    private FileService fileService;


    //private String IMAGE_SERVER_URL="http://192.168.25.133/";
    //String conf="D:/DEMO/code/shiyanshimaster-end/updown-masterF/updown-manager/updown-manager-service/src/main/resources/properties/client.conf";
	//文件上传
	@RequestMapping(value = "/file/upload",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<UpdownResult> createfile(@RequestParam("file")MultipartFile uploadFile) {
        try {

            if (uploadFile == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(400,"无文件"));
            }
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            UpdownResult result = this.fileService.createFile(uploadFile.getBytes(),extName);
            String url = result.getData().toString();
            //响应上传图片的url
            if (url.equals("error")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404, "文件上传失败", null));
            } else {
                return ResponseEntity.ok(UpdownResult.ok(url));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404,"文件上传失败1",null));
        }
    }
		
		
	
		

	//文件下载
	@RequestMapping("/file/download")
	@ResponseBody
	public ResponseEntity<UpdownResult> fileDownload(@RequestParam("filePath")String filePath, @RequestParam("fileName") String fileName) {
		if (filePath==null || fileName==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404, "文件下载失败", null));

        }

	    this.fileService.getFile(filePath,fileName);
        return ResponseEntity.ok(UpdownResult.ok());

	}


	//文件删除
	@RequestMapping("/file/delete")
	@ResponseBody
	public ResponseEntity<UpdownResult> fileDelete(@RequestParam("filePath")String filePath) {
        if (filePath==null ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404, "文件删除失败", null));

        }
	    this.fileService.deleteFile(filePath);
        return ResponseEntity.ok(UpdownResult.ok());
}




}
